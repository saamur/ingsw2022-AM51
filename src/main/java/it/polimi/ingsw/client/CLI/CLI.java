package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.modeldata.*;
import it.polimi.ingsw.constants.CliConstants;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.gamemessages.*;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;


public class CLI implements View, Runnable {

    private String nickname;
    private AvailableGamesMessage availableGamesMessage;
    private boolean gameChosen;
    private boolean gameOver;

    private GameData gameData;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public CLI(){
        System.out.println("Welcome to the CLI!");
        Scanner stdIn = new Scanner(System.in);
        ServerHandler serverHandler = null;

        do{
            System.out.println("Insert IP:");
            String address = stdIn.nextLine();
            System.out.println("Insert port:");
            int port = stdIn.nextInt();
            try {
                serverHandler = new ServerHandler(address, port, this);
            }catch (IOException e){
                System.out.println("The server could not be reached.\nCheck if the parameters are correct or if the server is running\n");
            }
        }while(serverHandler == null);

        Thread serverHandlerThread = new Thread(serverHandler);
        this.addPropertyChangeListener(serverHandler);
        serverHandlerThread.start();

        displayEverything();
    }

    @Override
    public void run() {

        Scanner stdIn = new Scanner(System.in);

        while (!gameOver) {
            Message message;
            if ((message = commandParser(stdIn.nextLine())) != null) {
                pcs.firePropertyChange("message", null, message);
            }
        }

    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        System.out.println("Welcome " + nickname + "!");
        if (availableGamesMessage != null)
            displayEverything();
    }

    @Override
    public void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage) {
        this.availableGamesMessage = availableGamesMessage;
        if (nickname != null)
            displayEverything();
    }

    @Override
    public void playerAddedToGame(String message) {
        System.out.println(message);
        gameChosen = true;
        displayEverything();
    }

    @Override
    public void setGameData (GameData gameData) {
        this.gameData = gameData;
        gameChosen = true;
        displayEverything();
    }

    @Override
    public void updateGameData (UpdateMessage updateMessage) {
        if (gameData != null) {
            synchronized (gameData) {
                updateMessage.updateGameData(gameData);
            }
            displayEverything();
        }
    }

    @Override
    public void addPropertyChangeListener (PropertyChangeListener propertyChangeListener) {
        pcs.addPropertyChangeListener(propertyChangeListener);
    }

    @Override
    public void handleGenericMessage(String message) {
        System.out.println("handleGenericMessage: " + message);
        //todo
    }

    @Override
    public void handleErrorMessage(String message) {
        System.out.println("handleErrorMessage: " + message);
        //todo
    }

    @Override
    public void handleGameOver(List<String> winnersNickname) {
        gameData.setWinnersNicknames(winnersNickname);
        gameOver = true;
        displayEverything();
    }

    @Override
    public void handlePlayerDisconnected(String playerDisconnectedNickname) {
        System.out.println(playerDisconnectedNickname + " has disconnected");
        System.out.println("The game will close");
        gameOver = true;
    }

    public Message commandParser (String line) {

        Message message = null;

        if (gameData == null) {
            if (nickname == null) {
                if ("".equals(line))
                    System.out.println("I don't really know how to pronunce it. Try with another one: ");
                else if (line.contains(" "))
                    System.out.println("This nickname is not valid, you can't use spaces. Try with another one: ");
                else
                    message = new NicknameMessage(line);
            }
            else if (!gameChosen) {
                String[] words = line.split(" ");
                if (words.length > 0) {
                    if (CliConstants.JOINGAME_COMMAND.equalsIgnoreCase(words[0]) && words.length == 2) {
                        try {
                            message = new AddPlayerMessage(Integer.parseInt(words[1]));
                        } catch (IllegalArgumentException e) {}
                    }
                    else if (CliConstants.RESTOREGAME_COMMAND.equalsIgnoreCase(words[0]) && words.length == 2) {
                        int index;
                        try {
                            index = Integer.parseInt(words[1]);
                            if (index < availableGamesMessage.savedGameData().size())
                                message = new RestoreGameMessage(availableGamesMessage.savedGameData().get(index));
                        } catch (NumberFormatException e) {}
                    }
                    else if (CliConstants.CREATENEWGAME_COMMAND.equalsIgnoreCase(words[0]) && words.length == 3) {
                        try {
                            message = new NewGameMessage(Integer.parseInt(words[1]), Boolean.parseBoolean(words[2]));
                        } catch (IllegalArgumentException e) {}
                    }
                }

                if (message == null)
                    System.out.println("This command is not correct");

            }
            else {
                System.out.println("The game is not started yet");
            }
        }
        else {
            synchronized (gameData) {
                if (gameData.getCurrPlayer().equals(nickname)) {
                    if (gameData.getGameState() == GameState.PLANNING) {
                        try {
                            message = new ChosenCardMessage(Card.valueOf(line.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("This is not a valid card. Retry: ");
                        }
                    }
                    else if (gameData.getGameState() == GameState.ACTION) {
                        String[] words = line.split(" ");
                        if (gameData.getActiveCharacterCard() != null && !gameData.isActiveCharacterPunctualEffectApplied()) {
                            //todo tutta la cosa dei personaggi
                        }
                        else if (words.length == 2 && CliConstants.ACTIVATECHARACTER_COMMAND.equalsIgnoreCase(words[0])) {
                            try {
                                message = new ActivateCharacterCardMessage(CharacterID.valueOf(words[1].toUpperCase()));
                            } catch (IllegalArgumentException e) {
                                System.out.println("This character does not exist");
                            }
                        }
                        else if (gameData.getTurnState() == TurnState.STUDENT_MOVING) {
                            if (words.length == 2 && CliConstants.CHAMBER_COMMAND.equalsIgnoreCase(words[0])) {
                                try {
                                    message = new MoveStudentToChamberMessage(Clan.valueOf(words[1].toUpperCase()));
                                } catch (IllegalArgumentException e) {
                                    System.out.println("This is not a valid clan");
                                }
                            }
                            else if (words.length == 3 && CliConstants.ISLAND_COMMAND.equalsIgnoreCase(words[0])) {
                                try {
                                    message = new MoveStudentToIslandMessage(Clan.valueOf(words[2].toUpperCase()), Integer.parseInt(words[1]));
                                } catch (Exception e) {
                                    System.out.println("The parameters are not correct");
                                }
                            }
                            else {
                                System.out.println("This command is not correct");
                            }
                        }
                        else if (gameData.getTurnState() == TurnState.MOTHER_MOVING) {
                            try {
                                message = new MoveMotherNatureMessage(Integer.parseInt(line));
                            } catch (NumberFormatException e) {
                                System.out.println("This is not a valid number");
                            }
                        }
                        else if (gameData.getTurnState() == TurnState.CLOUD_CHOOSING) {
                            try {
                                message = new ChosenCloudMessage(Integer.parseInt(line));
                            } catch (NumberFormatException e) {
                                System.out.println("This is not a valid number");
                            }
                        }
                        else {
                            if (CliConstants.ENDTURN_COMMAND.equalsIgnoreCase(line))
                                message = new EndTurnMessage();
                            else
                                System.out.println("This command is not correct");
                        }
                    }
                }
                else {
                    System.out.println("It is not your turn");
                }
            }
        }

        return message;

    }

    @Override
    public void displayEverything() {

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        if (nickname == null) {
            System.out.println("Choose a nickname: ");
        }
        else if (!gameChosen) {
            ModelDisplay.displayAvailableGames(availableGamesMessage);
            System.out.println("Possible commands:");
            System.out.println("\t" + CliConstants.CREATENEWGAME_COMMAND + " <number of players> <true for expert mode, false otherwise>");
            System.out.println("\t" + CliConstants.JOINGAME_COMMAND + " <game id>");
            System.out.println("\t" + CliConstants.RESTOREGAME_COMMAND + " <game id>");
            System.out.println();
        }
        else if (gameData == null) {
            System.out.println("wait for the game to start...");
        }
        else {
            synchronized (gameData) {
                if (gameData.getGameState() == GameState.GAME_OVER) {
                    System.out.println("THE GAME IS OVER");
                    System.out.print("Winners: ");
                    for (String s : gameData.getWinnersNicknames())
                        System.out.println(s + " ");
                    System.out.println();
                } else {

                    ModelDisplay.displayModel(gameData, nickname);

                    if (nickname.equals(gameData.getCurrPlayer())) {
                        if (gameData.getGameState() == GameState.PLANNING) {
                            System.out.println("Your available cards are: ");
                            for (PlayerData p : gameData.getPlayerData())
                                if (p.getNickname().equals(nickname))
                                    ModelDisplay.displayDeck(p);
                            System.out.println("Chose one: ");
                        } else if (gameData.getGameState() == GameState.ACTION) {
                            if (gameData.getActiveCharacterCard() != null && !gameData.isActiveCharacterPunctualEffectApplied()) {
                                //todo tutta la cosa dei personaggi
                            } else {
                                if (gameData.getTurnState() == TurnState.STUDENT_MOVING) {
                                    System.out.println("Possible commands:");
                                    System.out.println("\t" + CliConstants.CHAMBER_COMMAND + " <clan>");
                                    System.out.println("\t" + CliConstants.ISLAND_COMMAND + " <island index> <clan>");
                                } else if (gameData.getTurnState() == TurnState.MOTHER_MOVING) {
                                    System.out.println("Possible commands:");
                                    System.out.println("\t" + "<island index>");
                                } else if (gameData.getTurnState() == TurnState.CLOUD_CHOOSING) {
                                    System.out.println("Possible commands:");
                                    System.out.println("\t" + "<cloud index>");
                                } else {
                                    System.out.println("Possible commands:");
                                    System.out.println("\t" + CliConstants.ENDTURN_COMMAND);
                                }
                                if (gameData.isExpertModeEnabled() && gameData.getActiveCharacterCard() == null)
                                    System.out.println("\t" + CliConstants.ACTIVATECHARACTER_COMMAND + " <character name>");
                            }
                        }
                    }

                }
            }
        }

    }

}
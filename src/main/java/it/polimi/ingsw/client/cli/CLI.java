package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.constants.ConnectionConstants;
import it.polimi.ingsw.constants.cliconstants.CliCommandConstants;
import it.polimi.ingsw.constants.cliconstants.CliGraphicConstants;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import it.polimi.ingsw.model.GameState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The CLI class contains the necessary methods needed to play to Eriantys with the Command Line Interface
 *
 */
public class CLI implements View, Runnable {

    private String nickname;
    private AvailableGamesMessage availableGamesMessage;
    private boolean gameChosen;
    private volatile boolean gameOver;

    private GameData gameData;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Constructs a Cli instance asking the user the IP address and the port of the server
     */
    public CLI(){

        System.out.println("Welcome to the CLI!");
        Scanner stdIn = new Scanner(System.in);
        ServerHandler serverHandler = null;

        do{
            System.out.println("Insert IP, press enter for default (" + ConnectionConstants.DEFAULT_IP + "):");
            String address = stdIn.nextLine();
            if ("".equals(address))
                address = ConnectionConstants.DEFAULT_IP;

            System.out.println("Insert server port, press enter for default (" + ConnectionConstants.DEFAULT_PORT + "):");
            String line = stdIn.nextLine();
            try {
                int port = "".equals(line) ? ConnectionConstants.DEFAULT_PORT : Integer.parseInt(line);
                serverHandler = new ServerHandler(address, port, this);
            }  catch (IllegalArgumentException e) {
                System.out.println("This is not a number");
            } catch (IOException e){
                System.out.println("The server could not be reached.\nCheck if the parameters are correct or if the server is running\n");
            }
        } while (serverHandler == null);

        this.addPropertyChangeListener(serverHandler);
        new Thread(serverHandler).start();

        displayEverything();

    }

    @Override
    public void run() {

        Scanner stdIn = new Scanner(System.in);

        while (!gameOver) {
            Message message;
            String line;
            try {
                line = stdIn.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }
            if(gameOver)
                break;
            synchronized (this) {
                message = CommandParser.parseCommand(line, gameData, nickname, gameChosen, availableGamesMessage);
            }
            if (message != null)
                pcs.firePropertyChange("message", null, message);
        }

    }

    @Override
    public synchronized void setNickname(String nickname) {
        this.nickname = nickname;
        System.out.println("Welcome " + nickname + "!");
        if (availableGamesMessage != null)
            displayEverything();
    }

    @Override
    public synchronized void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage) {
        this.availableGamesMessage = availableGamesMessage;
        if (nickname != null)
            displayEverything();
    }

    @Override
    public synchronized void playerAddedToGame(String message) {
        System.out.println(message);
        gameChosen = true;
        displayEverything();
    }

    @Override
    public synchronized void setGameData (GameData gameData) {
        this.gameData = gameData;
        gameChosen = true;
        displayEverything();
    }

    @Override
    public synchronized void updateGameData (UpdateMessage updateMessage) {
        if (gameData != null) {
            updateMessage.updateGameData(gameData);
            displayEverything();
        }
    }

    @Override
    public void handleGenericMessage(String message) {
        if(!gameOver)
            System.out.println(message);
    }

    @Override
    public void handleErrorMessage(String message) {
        System.out.println(CliGraphicConstants.ANSI_RED + message + CliGraphicConstants.ANSI_RESET);
    }

    @Override
    public synchronized void handleGameOver(List<String> winnersNickname) {
        gameData.setWinnersNicknames(winnersNickname);
        gameOver = true;
        displayEverything();
    }

    @Override
    public synchronized void handlePlayerDisconnected(String playerDisconnectedNickname) {
        System.out.println(playerDisconnectedNickname + " has disconnected");
        if (gameData != null && !gameOver)
            System.out.println("The game has been saved");
        gameOver = true;
    }

    @Override
    public void handleServerDisconnected() {
        System.out.println("The server has disconnected");
        if (gameData != null && !gameOver)
            System.out.println("but the game has been saved");
    }

    /**
     * The method displayEverything shows, depending on the phase of the game in which you are, the relative graphics of the game
     */

    private synchronized void displayEverything() {
        System.out.println("\n");

        if (nickname == null) {
            System.out.println("\n");
            System.out.println(CliGraphicConstants.ERIANTYS);
            System.out.println("\n");
            System.out.println("Choose a nickname: ");
        } else if (!gameChosen) {
            ModelDisplay.displayAvailableGames(availableGamesMessage);
            System.out.println("Possible commands:");
            System.out.println("\t" + CliCommandConstants.CREATENEWGAME_COMMAND + " <number of players> <true for expert mode, false otherwise>");
            System.out.println("\t" + CliCommandConstants.JOINGAME_COMMAND + " <game id>");
            System.out.println("\t" + CliCommandConstants.RESTOREGAME_COMMAND + " <game id>");
            System.out.println();
        } else if (gameData == null) {
            System.out.println("wait for the game to start...");
        } else if (gameData.getGameState() == GameState.GAME_OVER) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("THE GAME IS OVER\n");
            for(String winner : gameData.getWinnersNicknames()){
                if(nickname.equals(winner)){
                    System.out.println(CliGraphicConstants.WINNER);
                    System.out.println("\n\n");
                }
            }

            System.out.println("Winner" + (gameData.getWinnersNicknames().size() == 1 ? "" : "s"));
            for (String s : gameData.getWinnersNicknames())
                System.out.println(s + " ");
            System.out.println();
        } else {

            ModelDisplay.displayModel(gameData, nickname);

            if (nickname.equals(gameData.getCurrPlayer())) {
                if (gameData.getGameState() == GameState.PLANNING) {
                    for(PlayerData player : gameData.getPlayerData()){
                        if(player.getCurrCard() != null){
                            System.out.println(player.getNickname() + " chose " + player.getCurrCard().name());
                        }
                    }
                    System.out.println("Your available cards are: ");
                    for (PlayerData p : gameData.getPlayerData())
                        if (p.getNickname().equals(nickname))
                            ModelDisplay.displayDeck(p);

                    System.out.println("Chose one: ");
                } else if (gameData.getGameState() == GameState.ACTION) {
                    System.out.println("Possible commands:");
                    if (gameData.getActiveCharacterCard() != null && !gameData.isActiveCharacterPunctualEffectApplied()) {
                        System.out.println("\t" + CliCommandConstants.getCharacterEffectCommand(gameData.getActiveCharacterCard()));
                    } else {
                        switch (gameData.getTurnState()) {
                            case STUDENT_MOVING -> {
                                System.out.println("\t" + CliCommandConstants.CHAMBER_COMMAND + " <clan>");
                                System.out.println("\t" + CliCommandConstants.ISLAND_COMMAND + " <island index> <clan>");
                            }
                            case MOTHER_MOVING -> {
                                System.out.println("\t" + "<island index>");
                                for(PlayerData p : gameData.getPlayerData()){
                                    if(p.getNickname().equals(nickname))
                                        System.out.println(("\t" + "[max steps: " + p.getCurrCard().getMaxStepsMotherNature() + "]"));
                                }
                            }

                            case CLOUD_CHOOSING -> System.out.println("\t" + "<cloud index>");
                            case END_TURN -> System.out.println("\t" + CliCommandConstants.ENDTURN_COMMAND);
                        }
                        if (gameData.isExpertModeEnabled() && gameData.getActiveCharacterCard() == null) {
                            System.out.println("\t" + CliCommandConstants.ACTIVATECHARACTER_COMMAND + " <character name>");
                        }
                    }
                }
            }

        }

        System.out.println();

    }

    @Override
    public void addPropertyChangeListener (PropertyChangeListener propertyChangeListener) {
        pcs.addPropertyChangeListener(propertyChangeListener);
    }

}
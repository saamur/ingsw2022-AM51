package it.polimi.ingsw;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.modeldata.*;
import it.polimi.ingsw.constants.CliConstants;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.gamemessages.*;
import it.polimi.ingsw.messages.updatemessages.UpdateGamePhase;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import org.fusesource.jansi.AnsiConsole;


public class CLI implements View, Runnable {

    private String nickname;
    private AvailableGamesMessage availableGamesMessage;
    private boolean waitingForServer;
    private boolean gameChosen;
    private boolean gameOver;

    private GameData gameData;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public CLI() {
        System.out.println("Welcome to the CLI!");
        displayEverything();
    }

    @Override
    public synchronized void run() {

        Scanner stdIn = new Scanner(System.in);

        while (!gameOver) {
            try {
                while (waitingForServer)
                    wait();
                if (gameOver)
                    break;

                Message message = null;

                if (nickname == null) {
                    String name = stdIn.nextLine();
                    if ("".equals(name))
                        System.out.println("I don't really know how to pronunce it. Try with another one: ");
                    else if (name.contains(" "))
                        System.out.println("This nickname is not valid, you can't use spaces. Try with another one: ");
                    else
                        message = new NicknameMessage(name);
                }
                else if (!gameChosen) {
                    String[] words;
                    do { words = stdIn.nextLine().split(" "); } while (words.length == 0);
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

                    if (message == null)
                        System.out.println("This command is not correct");

                }
                else if (gameData != null && gameData.getCurrPlayer().equals(nickname)) {
                    if (gameData.getGameState() == GameState.PLANNING) {
                        String card = stdIn.nextLine();
                        try {
                            message = new ChosenCardMessage(Card.valueOf(card.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("This is not a valid card. Retry: ");
                        }
                    }
                    else if (gameData.getGameState() == GameState.ACTION) {
                        String line = stdIn.nextLine();
                        String[] words = line.split(" ");
                        if (gameData.getActiveCharacterCard() != null && !gameData.isActiveCharacterEffectApplied()) {
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
                    waitingForServer = true;
                }

                if (message != null) {
                    pcs.firePropertyChange("message", null, message);
                    waitingForServer = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public synchronized void setNickname(String nickname) {
        this.nickname = nickname;
        System.out.println("Welcome " + nickname + "!");
        if (availableGamesMessage != null) {
            displayEverything();
            waitingForServer = false;
            notifyAll();
        }
    }

    @Override
    public synchronized void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage) {
        this.availableGamesMessage = availableGamesMessage;
        if (nickname != null) {
            displayEverything();
            waitingForServer = false;
            notifyAll();
        }
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
        waitingForServer = false;
        notifyAll();
    }

    @Override
    public synchronized void updateGameData (UpdateMessage updateMessage) {
        if (gameData != null) {
            updateMessage.updateGameData(gameData);
            displayEverything();
            if (updateMessage instanceof UpdateGamePhase) {
                waitingForServer = false;
                notifyAll();
            }
        }
    }

    @Override
    public void addPropertyChangeListener (PropertyChangeListener propertyChangeListener) {
        pcs.addPropertyChangeListener(propertyChangeListener);
    }

    @Override
    public synchronized void handleGenericMessage(String message) {
        System.out.println("handleGenericMessage: " + message);
        //todo
    }

    @Override
    public synchronized void handleErrorMessage(String message) {
        System.out.println("handleErrorMessage: " + message);
        //todo
    }

    @Override
    public void handleGameOver(List<String> winnersNickname) {
        gameData.setWinnersNicknames(winnersNickname);
        displayEverything();
        gameOver = true;
        notifyAll();
    }

    @Override
    public synchronized void handlePlayerDisconnected(String playerDisconnectedNickname) {
        System.out.println(playerDisconnectedNickname + " has disconnected");
        System.out.println("The game will close");
        gameOver = true;
        notifyAll();
    }

    public synchronized Message commandParser (String line) {

        Message message = null;

        String[] words = line.split(" ");
        if (words.length == 0)
            return null;

        switch (words[0].toLowerCase()) {
            case "message":
                if (words.length != 2)
                    return null;
                message = new NicknameMessage(words[1]);
                break;
            case "joingame":
                if (words.length != 2)
                    return null;
                try {
                    message = new AddPlayerMessage(Integer.parseInt(words[1]));
                } catch (IllegalArgumentException e) {
                    return null;
                }
                break;
            case "restoregame":
                if (availableGamesMessage == null || words.length != 2)
                    return null;
                int index;
                try {
                    index = Integer.parseInt(words[1]);
                }
                catch (NumberFormatException e) {
                    return null;
                }
                if (index >= availableGamesMessage.savedGameData().size())
                    return null;
                message = new RestoreGameMessage(availableGamesMessage.savedGameData().get(index));
                break;
            case "createnewgame":
                if (words.length != 3)
                    return null;
                try {
                    message = new NewGameMessage(Integer.parseInt(words[1]), Boolean.parseBoolean(words[2]));
                } catch (IllegalArgumentException e) {
                    return null;
                }
                break;
            case "chosencard":
                if (words.length != 2)
                    return null;
                try {
                    message = new ChosenCardMessage(Card.valueOf(words[1].toUpperCase()));
                } catch (IllegalArgumentException e) {
                    return null;
                }
                break;
            case "movestudenttochamber":
                if (words.length != 2)
                    return null;
                try {
                    message = new MoveStudentToChamberMessage(Clan.valueOf(words[1].toUpperCase()));
                } catch (IllegalArgumentException e) {
                    return null;
                }
                break;
            case "movestudenttoisland":
                if (words.length != 3)
                    return null;
                try {
                    message = new MoveStudentToIslandMessage(Clan.valueOf(words[1].toUpperCase()), Integer.parseInt(words[2]));
                } catch (Exception e) {
                    return null;
                }
                break;
            case "movemothernature":
                if (words.length != 2)
                    return null;
                try {
                    message = new MoveMotherNatureMessage(Integer.parseInt(words[1]));
                } catch (NumberFormatException e) {
                    return null;
                }
                break;
            case "chosencloud":
                if (words.length != 2)
                    return null;
                try {
                    message = new ChosenCloudMessage(Integer.parseInt(words[1]));
                } catch (NumberFormatException e) {
                    return null;
                }
                break;
            case "endturn":
                if (words.length != 1)
                    return null;
                message = new EndTurnMessage();
                break;
            case "activatecharactercard":
                if (words.length != 2)
                    return null;
                try {
                    message = new ActivateCharacterCardMessage(CharacterID.valueOf(words[1].toUpperCase()));
                } catch (IllegalArgumentException e) {
                    return  null;
                }
                break;
            case "applycharactercardeffect":
                if (words.length == 2) {
                    try {
                        message = new ApplyCharacterCardEffectMessage1(Integer.parseInt(words[1]));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
                else if (words.length == 12) {
                    try {
                        Map<Clan, Integer> students1 = new EnumMap<>(Clan.class);
                        Map<Clan, Integer> students2 = new EnumMap<>(Clan.class);
                        for (int i = 0; i < Clan.values().length; i++)
                            students1.put(Clan.values()[i], Integer.parseInt(words[2 + i]));
                        for (int i = 0; i < Clan.values().length; i++)
                            students2.put(Clan.values()[i], Integer.parseInt(words[7 + i]));
                        message = new ApplyCharacterCardEffectMessage2(Integer.parseInt(words[1]), students1, students2);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
                break;
            case "setclancharactercard":
                if (words.length != 2)
                    return null;
                try {
                    message = new SetClanCharacterMessage(Clan.valueOf(words[1].toUpperCase()));
                } catch (IllegalArgumentException e) {
                    return  null;
                }
                break;

        }

        return message;

    }

    public void displayHall(HallData hallData) {
        int i;

        System.out.println("HALL");

        System.out.print("| ");

        for (Clan c : Clan.values()) {
            System.out.print(CliConstants.getColorStudent(c) + c);
            System.out.print(CliConstants.ANSI_RESET + " | ");
        }
        System.out.println();
        for (Clan clan : Clan.values()) {
            System.out.print("| ");
            for (i = 0; i < clan.toString().length() / 2; i++) {
                System.out.print(" ");
            }
            if (hallData.students().get(clan) != null)
                System.out.print(hallData.students().get(clan));
            else
                System.out.print("0");
            for (int k = i; k < clan.toString().length(); k++)
                System.out.print(" ");

        }

        System.out.println("|");
        System.out.println("\n" + CliConstants.ANSI_RESET);
    }

    public void displayChamber(ChamberData chamberData){
        System.out.println("CHAMBER");


            for (Clan c : Clan.values()) {
                System.out.print(CliConstants.getColorStudent(c) + c);
                for (int l = 0; l < CliConstants.MAX_LENGHT_STUDENTS - c.toString().length(); l++) {
                    System.out.print(" ");
                }
                System.out.print("  ");
                for (int i = 0; i < chamberData.students().get(c); i++) {
                    System.out.print(CliConstants.getColorStudent(c) + CliConstants.STUDENTS_PRESENT + " ");
                }
                for (int k = 0; k < GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER - chamberData.students().get(c); k++) {
                    System.out.print(CliConstants.getColorStudent(c) + CliConstants.STUDENTS_NOT_PRESENT + " ");
                }
                if(chamberData.professors().get(c))
                    System.out.print(CliConstants.getColorStudent(c) + CliConstants.PROFESSOR_SYMBOL + CliConstants.ANSI_RESET);
                else
                    System.out.print(CliConstants.getColorStudent(c) + CliConstants.NO_PROFESSOR_SYMBOL + CliConstants.ANSI_RESET);
                System.out.println("\n");
            }
            System.out.println("\n" + CliConstants.ANSI_RESET);

    }


    public void displayIslands() {
        List<IslandData> islandsData = gameData.getIslandManager().getIslands();
        System.out.println(CliConstants.ANSI_RESET + "ISLANDS");
        for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS + 1; j++) {
            System.out.print(" ");
        }
        System.out.print("| ");
        if (islandsData.size() <= CliConstants.MAX_VISUAL) {
            for (int i = 0; i < islandsData.size(); i++) {
                if (gameData.getIslandManager().getMotherNaturePosition() != i)
                    System.out.print("island " + (i));
                else
                    System.out.print(CliConstants.ANSI_PURPLE + "island " + (i));
                System.out.print(CliConstants.ANSI_RESET + " | ");
            }
            System.out.println("\n");
            for(Clan clan : Clan.values()) {
                System.out.print(CliConstants.getColorStudent(clan) + clan + CliConstants.ANSI_RESET);
                System.out.print(" ");
                for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS - clan.toString().length() + 1; j++) {
                    System.out.print(" ");
                }

                System.out.print("| ");
                for (IslandData islandsDatum : islandsData) {
                    for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2; j++) {
                        System.out.print(" ");
                    }

                    System.out.print(islandsDatum.students().get(clan));
                    for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2 - 1; j++) { //todo aggiustare spazi nel caso gli studenti di un clan in un isola siano più di 10 (problemi di allineamento)
                        System.out.print(" ");
                    }
                    System.out.print(" | ");
                }
                System.out.print("\n");
            }
            System.out.print("TOWER    |");
            for(int i = 0; i<islandsData.size(); i++){
                System.out.print("   ");
                createTower2(i);
                System.out.print("   ");

            }
            System.out.println("\n");
            System.out.print("n° isl   |");
            for (IslandData islandsDatum : islandsData) {
                System.out.print("    ");
                System.out.print(islandsDatum.numberOfIslands());
                System.out.print("     | ");
            }

        }
        else {
            for (int i = 0; i < CliConstants.MAX_VISUAL; i++) {
                if (gameData.getIslandManager().getMotherNaturePosition() != i)
                    System.out.print("island " + (i));
                else
                    System.out.print(CliConstants.ANSI_PURPLE + "island " + (i));
                System.out.print(CliConstants.ANSI_RESET + "  | ");
            }

            System.out.println("\n");

            for(Clan clan : Clan.values()) {
                System.out.print(CliConstants.getColorStudent(clan) + clan);
                for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS - clan.toString().length() + 1; j++) {
                    System.out.print(" ");
                }

                System.out.print(CliConstants.ANSI_RESET + "| ");
                for (int i = 0; i < CliConstants.MAX_VISUAL; i++) {
                    numStudentsForIsland(clan, i);
                }
                System.out.println("\n");
            }

            System.out.print("TOWER    |");
            for(int i = 0; i<CliConstants.MAX_VISUAL; i++){
                createTower(i);

            }
            System.out.println("\n");
            System.out.print("n° isl   | ");
            for(int i = 0; i<CliConstants.MAX_VISUAL; i++){
                System.out.print("    ");
                System.out.print(islandsData.get(i).numberOfIslands());
                System.out.print("     | ");
            }

            System.out.println("\n");
            for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS + 1; j++) {
                System.out.print(" ");
            }
            System.out.print(CliConstants.ANSI_RESET + "| ");

            for (int i = CliConstants.MAX_VISUAL; i < islandsData.size(); i++) {

                if (gameData.getIslandManager().getMotherNaturePosition() != i)
                    System.out.print("island " + (i));
                else
                    System.out.print(CliConstants.ANSI_PURPLE + "island " + (i));

                if(i < 9) {
                    System.out.print(CliConstants.ANSI_RESET + "  | ");
                }
                else
                    System.out.print(CliConstants.ANSI_RESET + " | ");
            }
            System.out.print("\n");

                for(Clan clan : Clan.values()) {
                    System.out.print(CliConstants.getColorStudent(clan) + clan);
                    for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS - clan.toString().length() + 1; j++) {
                        System.out.print(" ");
                    }

                    System.out.print(CliConstants.ANSI_RESET + "| ");
                    for (int k = CliConstants.MAX_VISUAL; k < islandsData.size(); k++) {
                        numStudentsForIsland(clan, k);
                    }
                    System.out.println("\n");
                }

            System.out.print("TOWER    |");
            for(int i = CliConstants.MAX_VISUAL; i<islandsData.size(); i++){
                createTower(i);

            }

            System.out.println("\n");
            System.out.print("n° isl   | ");
            for(int i = 6; i<islandsData.size(); i++){
                System.out.print("    ");
                System.out.print(islandsData.get(i).numberOfIslands());
                if(i<10)
                    System.out.print("     | ");
                else System.out.print("     | ");
            }

                }
        System.out.println("\n");
            }


    private void createTower2(int i) {
        if(gameData.getIslandManager().getIslands().get(i).towerColor()==null){
            System.out.print("     ");
        }
        else{
            System.out.print(gameData.getIslandManager().getIslands().get(i).towerColor().toString());
            if(gameData.getIslandManager().getIslands().get(i).towerColor().equals(TowerColor.GRAY)){
                System.out.print(" ");
            }
        }
    }

    private void numStudentsForIsland(Clan clan, int k) {
        for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2; j++) {
            System.out.print(" ");
        }

        System.out.print(gameData.getIslandManager().getIslands().get(k).students().get(clan));
        for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2 - 1; j++) {
            System.out.print(" ");
        }
        System.out.print("  | ");
    }


    private void createTower(int i) {
        System.out.print("    ");
        createTower2(i);
        System.out.print("  |");
    }

    public void displayClouds(){
        for(int j = 0; j < gameData.getCloudManager().clouds().length; j++) {
            int i;
            if(gameData.getCloudManager().clouds()[j].picked())
                System.out.println(CliConstants.ANSI_RED + "CLOUD " + j + " (already chosen)" + CliConstants.ANSI_RESET);
            else
                System.out.println("CLOUD "+ j);
            System.out.print("| ");
            for (Clan c : Clan.values()) {
                System.out.print(CliConstants.getColorStudent(c) + c);
                System.out.print(CliConstants.ANSI_RESET + " | ");
            }
            System.out.println("\n");
            for (Clan clan : Clan.values()) {
                System.out.print("| ");
                for (i = 0; i < clan.toString().length() / 2; i++) {
                    System.out.print(" ");
                }
                if (gameData.getCloudManager().clouds()[j].students().get(clan) != null)
                    System.out.print(gameData.getCloudManager().clouds()[j].students().get(clan));
                else
                    System.out.print("0");
                for (int k = i; k < clan.toString().length(); k++)
                    System.out.print(" ");
            }
            System.out.println("|");
            System.out.println("\n" + CliConstants.ANSI_RESET);
        }
    }

    public void displayTower(PlayerData playerData){
        System.out.print("\n TOWERS: ");


            if(playerData.getColorOfTowers().equals(TowerColor.WHITE)) {
                System.out.println(TowerColor.WHITE.name());
                for (int i = 0; i < playerData.getNumberOfTowers(); i++) {
                    System.out.print(" " + CliConstants.TOWER_SYMBOL);
                }
            }
            else if(playerData.getColorOfTowers().equals(TowerColor.BLACK)){
                System.out.println(TowerColor.BLACK.name());
                for(int i = 0; i < playerData.getNumberOfTowers(); i++){
                    System.out.print(" " + CliConstants.TOWER_SYMBOL);//todo trovare un modo per rappresentare le torri nere differenziandole dalle bianche
                }
            }
            else if(playerData.getColorOfTowers().equals(TowerColor.GRAY)){
                System.out.println(TowerColor.GRAY.name());
                for(int i = 0; i < playerData.getNumberOfTowers(); i++){
                    System.out.print(CliConstants.ANSI_GRAY + " " + CliConstants.TOWER_SYMBOL);
                }
            }
            System.out.println(CliConstants.ANSI_RESET + "\n");

    }


    public void displayActiveCharacter(){

        System.out.println("\n");
        System.out.println("ACTIVE CHARACTER CARDS");
        System.out.print("        |");
        for(int i = 0; i < CliConstants.NUM_ACTIVE_CHARACTER_CARDS; i++){
            System.out.print("  ");
            if(gameData.getCharacterCardData()[i].characterID() == gameData.getActiveCharacterCard())
                System.out.print(CliConstants.ANSI_RED + gameData.getCharacterCardData()[i].characterID().toString() + CliConstants.ANSI_RESET);
            else
                System.out.print(gameData.getCharacterCardData()[i].characterID().toString());
            System.out.print("  |");
        }

        System.out.println("\n");
        System.out.print("Cost:   |");
        for(int i = 0; i < CliConstants.NUM_ACTIVE_CHARACTER_CARDS; i++){
            System.out.print("  ");
            if(gameData.getCharacterCardData()[i].characterID().toString().length()%2!= 0) {
                createAvailableCard(i);
                System.out.print("  |");
            }
            else{
                createAvailableCard(i);
                System.out.print(" |");
            }
            }
        }

    private void createAvailableCard(int i) {
        for (int j = 0; j < gameData.getCharacterCardData()[i].characterID().toString().length() / 2; j++) {
            System.out.print(" ");
        }
        System.out.print(gameData.getCharacterCardData()[i].cost());
        for (int j = 0; j < gameData.getCharacterCardData()[i].characterID().toString().length() / 2; j++) {
            System.out.print(" ");
        }
    }

    public void updateDeck(PlayerData playerData){

        for(int i = 0; i < playerData.getAvailableCards().size(); i++){
            System.out.println(playerData.getAvailableCards().get(i));
            System.out.println("Priority: " + playerData.getAvailableCards().get(i).getPriority());
            System.out.println("Max Steps: " + playerData.getAvailableCards().get(i).getMaxStepsMotherNature());
            System.out.println("\n\n");
        }

    }

    @Override
    public void displayEverything() {

        if (nickname == null) {
            System.out.println("Choose a nickname: ");
        }
        else if (!gameChosen) {
            System.out.println(availableGamesMessage);
            System.out.println("Possible commands:");
            System.out.println("\t" + CliConstants.CREATENEWGAME_COMMAND + " <number of players> <true for expert mode, false otherwise>");
            System.out.println("\t" + CliConstants.JOINGAME_COMMAND + " <game id>");
            System.out.println("\t" + CliConstants.RESTOREGAME_COMMAND + " <game id>");
            System.out.println();
        }
        else if (gameData == null) {
            System.out.println("wait for the game to start...");
        }
        else if (gameData.getGameState() == GameState.GAME_OVER) {
            System.out.println("THE GAME IS OVER");
            System.out.print("Winners: ");
            for (String s : gameData.getWinnersNicknames())
                System.out.println(s + " ");
            System.out.println();
        }
        else {

            displayModel();

            if (nickname.equals(gameData.getCurrPlayer())) {
                if (gameData.getGameState() == GameState.PLANNING) {
                    System.out.println("Your available cards are: ");
                    for (PlayerData p : gameData.getPlayerData())
                        if (p.getNickname().equals(nickname))
                            updateDeck(p);
                    System.out.println("Chose one: ");
                }
                else if (gameData.getGameState() == GameState.ACTION) {
                    if (gameData.getActiveCharacterCard() != null && !gameData.isActiveCharacterEffectApplied()) {
                        //todo tutta la cosa dei personaggi
                    }
                    else {
                        if (gameData.getTurnState() == TurnState.STUDENT_MOVING) {
                            System.out.println("Possible commands:");
                            System.out.println("\t" + CliConstants.CHAMBER_COMMAND + " <clan>");
                            System.out.println("\t" + CliConstants.ISLAND_COMMAND + " <island index> <clan>");
                        }
                        else if (gameData.getTurnState() == TurnState.MOTHER_MOVING) {
                            System.out.println("Possible commands:");
                            System.out.println("\t" + "<island index>");
                        }
                        else if (gameData.getTurnState() == TurnState.CLOUD_CHOOSING) {
                            System.out.println("Possible commands:");
                            System.out.println("\t" + "<cloud index>");
                        }
                        else {
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

    public void displayModel() {
        AnsiConsole.systemInstall();

        //System.out.flush();
        for(PlayerData p : gameData.getPlayerData()){
            if(p.getNickname().equals(nickname)) {
                System.out.println(p.getNickname() + "'s School" + " (you) ");
            }
            else
                System.out.println(p.getNickname() + "'s School");
            displayHall(p.getHallData());
            displayChamber(p.getChamberData());
            displayTower(p);
        }
        System.out.println("\n\n\n");

        displayIslands();

        System.out.println("\n\n\n");

        displayClouds();


        System.out.println("\n\n\n");
        if(gameData.isExpertModeEnabled()) {
            displayActiveCharacter();
            System.out.println("\n\n\n");
        }

        System.out.println("Game phase: " + gameData.getGameState().name().toLowerCase());
        if (gameData.getGameState() == GameState.ACTION)
            System.out.println("Turn state: " + gameData.getTurnState().name().toLowerCase());

        if (nickname.equals(gameData.getCurrPlayer()))
            System.out.println("It's your turn");
        else
            System.out.println(gameData.getCurrPlayer() + " is playing");

    }

}




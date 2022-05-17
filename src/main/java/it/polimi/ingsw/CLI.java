package it.polimi.ingsw;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.modeldata.*;
import it.polimi.ingsw.constants.CliConstants;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.gamemessages.*;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.fusesource.jansi.AnsiConsole;


public class CLI implements View, Runnable {

    private String nickname;
    private AvailableGamesMessage availableGamesMessage;

    private GameData gameData;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public CLI() {
        System.out.println("Welcome to the CLI!");
        System.out.println("First thing, choose a nickname: ");
    }

    @Override
    public void run() {

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            //displayModel();
            //todo print di tutto il model
            Message message;
            try {
                message = commandParser(stdIn.readLine());
                if (message != null) {
                    pcs.firePropertyChange("message", null, message);
                }
                else
                    System.out.println("this command is not correct");
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        System.out.println("Welcome " + nickname + "!");
    }

    @Override
    public void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage) {
        this.availableGamesMessage = availableGamesMessage;
        System.out.println(availableGamesMessage);
    }

    @Override
    public void setGameData (GameData gameData) {
        this.gameData = gameData;
        displayModel();
    }

    @Override
    public void updateGameData (UpdateMessage updateMessage) {
        if (gameData != null)
            updateMessage.updateGameData(gameData);
        displayModel();
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
        System.out.println("Game over");
        System.out.println("Winners: " + winnersNickname);
    }

    @Override
    public void handlePlayerDisconnected(String playerDisconnectedNickname) {
        System.out.println(playerDisconnectedNickname + " has disconnected");
        System.out.println("The game will close");
    }

    public synchronized Message commandParser (String line) {

        Message message = null;

        String[] words = line.split(" ");
        if (words.length == 0)
            return null;

        switch (words[0].toLowerCase()) {
            case "nickname":
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
                System.out.print(CliConstants.getColorStudent(clan) + clan);
                System.out.print(" ");
                for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS - clan.toString().length() + 1; j++) {
                    System.out.print(" ");
                }

                System.out.print("| ");
                for (IslandData islandsDatum : islandsData) {
                    for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2; j++) {
                        System.out.println(" ");
                    }

                    System.out.print(islandsDatum.students().get(clan));
                    for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2 - 1; j++) { //todo aggiustare spazi nel caso gli studenti di un clan in un isola siano più di 10 (problemi di allineamento)
                        System.out.print(" ");
                    }
                    System.out.println(" | ");
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

            for (int i = 6; i < islandsData.size(); i++) {

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
                    for (int k = 0; k < 6; k++) {
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

    @Override
    public void displayModel() {
        AnsiConsole.systemInstall();

        if (gameData == null)
            return;

        int numberOfPlayers = gameData.getPlayerData().length;
        System.out.flush();
        for(int i = 0; i < numberOfPlayers; i++){
            if(gameData.getPlayerData()[i].getNickname().equals(nickname)) {
                System.out.println(gameData.getPlayerData()[i].getNickname() + "'s School" + " (you) ");
            }
            else
                System.out.println(gameData.getPlayerData()[i].getNickname() + "'s School");
            displayHall(gameData.getPlayerData()[i].getHallData());
            displayChamber(gameData.getPlayerData()[i].getChamberData());
            displayTower(gameData.getPlayerData()[i]);
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




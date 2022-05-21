package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.modeldata.*;
import it.polimi.ingsw.constants.cliconstants.CliGraphicConstants;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.TowerColor;
import org.fusesource.jansi.AnsiConsole;

public class ModelDisplay {

    public static void displayAvailableGames (AvailableGamesMessage availableGamesMessage) {
        System.out.println("NEW AVAILABLE GAMES:");
        if(availableGamesMessage.openingNewGameDataList().size() > 0)
            System.out.println("   ID   | num of Players |  expert mode |");
        else
            System.out.println("no game has been created ");
        for(int i = 0; i < availableGamesMessage.openingNewGameDataList().size(); i++) {
            System.out.print("   ");
            System.out.print(availableGamesMessage.openingNewGameDataList().get(i).id());
            if(availableGamesMessage.openingNewGameDataList().get(i).id() < 10){
                System.out.print(" ");
            }
            System.out.print("   |");
            System.out.print("       " + availableGamesMessage.openingNewGameDataList().get(i).numOfPlayers() + "        |");
            System.out.print("     " + availableGamesMessage.openingNewGameDataList().get(i).expertMode());
            if(availableGamesMessage.openingNewGameDataList().get(i).expertMode())
                System.out.print(" ");
            System.out.println("    |");
        }
        System.out.println("\n\n");

        System.out.println("RESTORED GAMES:");
        if(availableGamesMessage.openingRestoredGameDataList().size() > 0){
            System.out.println("   ID   | num of Players |  expert mode |");
        }
        else
            System.out.println("no opening restored games");
        for(int i = 0; i < availableGamesMessage.openingRestoredGameDataList().size(); i++){
            System.out.print("   ");
            System.out.print(availableGamesMessage.openingRestoredGameDataList().get(i).id());
            if(availableGamesMessage.openingRestoredGameDataList().get(i).id() < 10){
                System.out.print(" ");
            }
            System.out.print("   |");
            System.out.print("       " + availableGamesMessage.openingRestoredGameDataList().get(i).numOfPlayers() + "        |");
            System.out.print("     " + availableGamesMessage.openingRestoredGameDataList().get(i).expertMode());
            if(availableGamesMessage.openingRestoredGameDataList().get(i).expertMode())
                System.out.print(" ");
            System.out.println("    |");
        }
        System.out.println("\n\n");

        System.out.println("SAVED GAMES: ");
        if(availableGamesMessage.savedGameData().size() > 0){
            System.out.println("   ID   | num of Players |  expert mode |");
        }
        else
            System.out.println("no saved games");

        for(int i = 0; i < availableGamesMessage.savedGameData().size(); i++){
            System.out.print("   ");
            System.out.print(i);
            if(i < 10){
                System.out.print(" ");
            }
            System.out.print("   |");
            System.out.print("       " + availableGamesMessage.savedGameData().get(i).numOfPlayers() + "        |");
            System.out.print("     " + availableGamesMessage.savedGameData().get(i).expertMode());
            if(availableGamesMessage.savedGameData().get(i).expertMode())
                System.out.print(" ");
            System.out.println("    |");
        }

        System.out.println("\n\n");





    }

    public static void displayModel (GameData gameData, String nickname) {
        AnsiConsole.systemInstall();

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        System.out.println(gameData);

        //System.out.flush();
        for(PlayerData p : gameData.getPlayerData()){
            if(p.getNickname().equals(nickname)) {
                System.out.println(p.getNickname() + "'s School" + " (you) ");
            }
            else
                System.out.println(p.getNickname() + "'s School");
            displayHall(p.getHallData());
            displayChamber(p.getChamberData());
            displayTowersPlayer(p);
            if(gameData.isExpertModeEnabled())
                displayCoins(p);
        }
        System.out.println("\n\n\n");

        displayIslands(gameData.getIslandManager());

        System.out.println("\n\n\n");

        displayClouds(gameData.getCloudManager());


        System.out.println("\n\n\n");
        if(gameData.isExpertModeEnabled()) {
            displayAvailableCharacters(gameData.getCharacterCardData(), gameData.getActiveCharacterCard());
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

    private static void displayHall (HallData hallData) {
        int i;

        System.out.println("HALL");

        System.out.print("| ");

        for (Clan c : Clan.values()) {
            System.out.print(CliGraphicConstants.getColorStudent(c) + c);
            System.out.print(CliGraphicConstants.ANSI_RESET + " | ");
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
        System.out.println("\n" + CliGraphicConstants.ANSI_RESET);
    }

    private static void displayChamber (ChamberData chamberData){
        System.out.println("CHAMBER");


        for (Clan c : Clan.values()) {
            System.out.print(CliGraphicConstants.getColorStudent(c) + c);
            for (int l = 0; l < CliGraphicConstants.MAX_LENGHT_STUDENTS - c.toString().length(); l++) {
                System.out.print(" ");
            }
            System.out.print("  ");
            for (int i = 0; i < chamberData.students().get(c); i++) {
                System.out.print(CliGraphicConstants.getColorStudent(c) + CliGraphicConstants.STUDENTS_PRESENT + " ");
            }
            for (int k = 0; k < GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER - chamberData.students().get(c); k++) {
                System.out.print(CliGraphicConstants.getColorStudent(c) + CliGraphicConstants.STUDENTS_NOT_PRESENT + " ");
            }
            if(chamberData.professors().get(c))
                System.out.print(CliGraphicConstants.getColorStudent(c) + CliGraphicConstants.PROFESSOR_SYMBOL + CliGraphicConstants.ANSI_RESET);
            else
                System.out.print(CliGraphicConstants.getColorStudent(c) + CliGraphicConstants.NO_PROFESSOR_SYMBOL + CliGraphicConstants.ANSI_RESET);
            System.out.println("\n");
        }
        System.out.println("\n" + CliGraphicConstants.ANSI_RESET);

    }

    private static void displayIslands (IslandManagerData islandManagerData) {
        System.out.println(CliGraphicConstants.ANSI_RESET + "ISLANDS");
        System.out.println("\n");
        updateIsland(islandManagerData, 0, Math.min(islandManagerData.getIslands().size(), CliGraphicConstants.MAX_VISUAL));

        System.out.println("\n");
        if(islandManagerData.getIslands().size() > 6) {
            updateIsland(islandManagerData, 6, islandManagerData.getIslands().size());
        }

        System.out.println("\n");
    }



    private static void updateIsland(IslandManagerData islandManagerData, int init, int end) {
        for (int j = 0; j < CliGraphicConstants.MAX_LENGHT_STUDENTS + 1; j++)
            System.out.print(" ");
        System.out.print("|");
        for(int i = init; i < end; i++){
            if(islandManagerData.getMotherNaturePosition() == i){
                System.out.print(CliGraphicConstants.ANSI_PURPLE + " island " + i + " " + CliGraphicConstants.ANSI_RESET);
            }
            else
                System.out.print(" island " + i + " ");
            if(i < 10){
                System.out.print(" ");
            }
            System.out.print("|");
        }
        System.out.println("\n");
        for(Clan c : Clan.values()){
            System.out.print(c.toString());
            for(int k = 0; k < (CliGraphicConstants.MAX_LENGHT_STUDENTS + 1) - c.toString().length(); k++){
                System.out.print(" ");
            }
            for(int i = init; i < end; i++) {
                System.out.print("|     ");
                System.out.print(islandManagerData.getIslands().get(i).students().get(c));
                System.out.print("     ");
            }
            System.out.println("|\n");
        }


        System.out.print("TOWER    |");
        for(int i = init; i < end; i++) {
            System.out.print("   ");
            if (islandManagerData.getIslands().get(i).towerColor() != null) {
                System.out.print(islandManagerData.getIslands().get(i).towerColor().toString());
                if (islandManagerData.getIslands().get(i).towerColor() == TowerColor.GRAY) {
                    System.out.print(" ");
                }
            }
            else
                    System.out.print("     ");


            System.out.print("   |");

        }


        System.out.println("\n");
        System.out.print("n° isl   |");
        for(int i = init; i < end; i++){
            System.out.print("     ");
            System.out.print(islandManagerData.getIslands().get(i).numberOfIslands());
            System.out.print("     |");
        }
        System.out.println("\n");



    }





    private static void displayTowersPlayer (PlayerData playerData){
        System.out.print("\n TOWERS: ");


        if(playerData.getColorOfTowers().equals(TowerColor.WHITE)) {
            System.out.println(TowerColor.WHITE.name());
            for (int i = 0; i < playerData.getNumberOfTowers(); i++) {
                System.out.print(" " + CliGraphicConstants.TOWER_SYMBOL);
            }
        }
        else if(playerData.getColorOfTowers().equals(TowerColor.BLACK)){
            System.out.println(TowerColor.BLACK.name());
            for(int i = 0; i < playerData.getNumberOfTowers(); i++){
                System.out.print(" " + CliGraphicConstants.TOWER_SYMBOL);//todo trovare un modo per rappresentare le torri nere differenziandole dalle bianche
            }
        }
        else if(playerData.getColorOfTowers().equals(TowerColor.GRAY)){
            System.out.println(TowerColor.GRAY.name());
            for(int i = 0; i < playerData.getNumberOfTowers(); i++){
                System.out.print(CliGraphicConstants.ANSI_GRAY + " " + CliGraphicConstants.TOWER_SYMBOL);
            }
        }
        System.out.println(CliGraphicConstants.ANSI_RESET + "\n");

    }



    private static void displayClouds (CloudManagerData cloudManagerData){
        for(int j = 0; j < cloudManagerData.clouds().length; j++) {
            int i;
            if(cloudManagerData.clouds()[j].picked())
                System.out.println(CliGraphicConstants.ANSI_RED + "CLOUD " + j + " (already chosen)" + CliGraphicConstants.ANSI_RESET);
            else
                System.out.println("CLOUD "+ j);
            System.out.print("| ");
            for (Clan c : Clan.values()) {
                System.out.print(CliGraphicConstants.getColorStudent(c) + c);
                System.out.print(CliGraphicConstants.ANSI_RESET + " | ");
            }
            System.out.println("\n");
            for (Clan clan : Clan.values()) {
                System.out.print("| ");
                for (i = 0; i < clan.toString().length() / 2; i++) {
                    System.out.print(" ");
                }
                if (cloudManagerData.clouds()[j].students().get(clan) != null)
                    System.out.print(cloudManagerData.clouds()[j].students().get(clan));
                else
                    System.out.print("0");
                for (int k = i; k < clan.toString().length(); k++)
                    System.out.print(" ");
            }
            System.out.println("|");
            System.out.println("\n" + CliGraphicConstants.ANSI_RESET);
        }
    }

    private static void displayAvailableCharacters (CharacterCardData[] characterCardData, CharacterID activeCharacter){

        System.out.println("\n");
        System.out.println("ACTIVE CHARACTER CARDS");
        System.out.print("        |");
        for(int i = 0; i < GameConstants.NUM_AVAILABLE_CHARACTER_CARDS; i++){
            System.out.print("  ");
            if(characterCardData[i].characterID() == activeCharacter)
                System.out.print(CliGraphicConstants.ANSI_RED + characterCardData[i].characterID().toString() + CliGraphicConstants.ANSI_RESET);
            else
                System.out.print(characterCardData[i].characterID().toString());
            System.out.print("  |");
        }

        System.out.println("\n");
        System.out.print("Cost:   |");
        for(int i = 0; i < GameConstants.NUM_AVAILABLE_CHARACTER_CARDS; i++){
            System.out.print("  ");
            if(characterCardData[i].characterID().toString().length()%2!= 0) {
                displayCharacterCard(characterCardData[i]);
                System.out.print("  |");
            }
            else{
                displayCharacterCard(characterCardData[i]);
                System.out.print(" |");
            }
        }
    }

    private static void displayCharacterCard (CharacterCardData characterCardData) {
        for (int j = 0; j < characterCardData.characterID().toString().length() / 2; j++) {
            System.out.print(" ");
        }
        System.out.print(characterCardData.cost());
        for (int j = 0; j < characterCardData.characterID().toString().length() / 2; j++) {
            System.out.print(" ");
        }
    }

    public static void displayDeck (PlayerData playerData) {

        updateDeck(0, playerData, Math.min(playerData.getAvailableCards().size(), CliGraphicConstants.MAX_VISUAL_DECK));


        if (playerData.getAvailableCards().size() > 5) {
            updateDeck(CliGraphicConstants.MAX_VISUAL_DECK, playerData, playerData.getAvailableCards().size());
        }
    }

    private static void updateDeck(int init, PlayerData playerData, int d){
        System.out.print("          | ");
        for(int i = init; i < d; i++) {
            System.out.print(playerData.getAvailableCards().get(i).toString());
            for (int j = 0; j < CliGraphicConstants.MAX_NAME_CARD_LENGTH - playerData.getAvailableCards().get(i).toString().length(); j++) {
                System.out.print(" ");
            }
            System.out.print(" | ");
        }
        System.out.print("\nPriority  |");
        for(int i = init; i < d; i++) {
            System.out.print("    ");
            System.out.print(playerData.getAvailableCards().get(i).getPriority());
            if (playerData.getAvailableCards().get(i).getPriority() == CliGraphicConstants.DOUBLE_DIGITS)
                System.out.print("    |");
            else
                System.out.print("     |");
        }

        System.out.print("\nMax Steps |");
        for(int i = init; i < d; i++) {
            System.out.print("    ");
            System.out.print(playerData.getAvailableCards().get(i).getMaxStepsMotherNature());
            System.out.print("     |");
        }
        System.out.println("\n\n");

    }


    private static void displayCoins(PlayerData playerData){
        if(playerData.getChamberData().coins()==0){
            System.out.println(playerData.getNickname() + " doesn't have any coins");
        }
        System.out.println("Number of Coins: " + playerData.getChamberData().coins());

        System.out.println("\n\n");
    }



}

package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.modeldata.*;
import it.polimi.ingsw.constants.cliconstants.CliGraphicConstants;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.controller.OpeningNewGameData;
import it.polimi.ingsw.controller.OpeningRestoredGameData;
import it.polimi.ingsw.controller.SavedGameData;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.TowerColor;
import org.fusesource.jansi.AnsiConsole;

import java.util.Arrays;

public class ModelDisplay {

    /**
     * the method displayAvailableGames shows the list of the new available games, the restored games and the saved games that the player can resume
     * @param availableGamesMessage
     */

    public static void displayAvailableGames (AvailableGamesMessage availableGamesMessage) {
        System.out.println("NEW AVAILABLE GAMES:");
        if(availableGamesMessage.openingNewGameDataList().size() > 0)
            System.out.println("   ID   | num of Players |  expert mode | nicknames ");
        else
            System.out.println("no game has been created ");
        for(OpeningNewGameData openingNewGameData : availableGamesMessage.openingNewGameDataList()) {
            System.out.print("   ");
            System.out.print(openingNewGameData.id());
            if(openingNewGameData.id() < 10){
                System.out.print(" ");
            }
            System.out.print("   |");
            System.out.print("       " + openingNewGameData.numOfPlayers() + "        |");
            System.out.print("     " + openingNewGameData.expertMode());
            if(openingNewGameData.expertMode())
                System.out.print(" ");
            System.out.print("    | ");
            for (String nickname : openingNewGameData.nicknames())
                System.out.print(nickname + " ");

            System.out.println();
        }
        System.out.println("\n\n");

        System.out.println("RESTORED GAMES:");
        if(availableGamesMessage.openingRestoredGameDataList().size() > 0){
            System.out.println("   ID   | num of Players |  expert mode | missing nicknames");
        }
        else
            System.out.println("no opening restored games");
        for(OpeningRestoredGameData a : availableGamesMessage.openingRestoredGameDataList()){
            System.out.print("   ");
            System.out.print(a.id());
            if(a.id() < 10){
                System.out.print(" ");
            }
            System.out.print("   |");
            System.out.print("       " + a.numOfPlayers() + "        |");
            System.out.print("     " + a.expertMode());
            if(a.expertMode())
                System.out.print(" ");
            System.out.print("    |");
            for(String nickname : a.missingNicknames()){
                System.out.print(nickname + " ");
            }
            System.out.println();
        }

        System.out.println("\n\n");

        System.out.println("SAVED GAMES: ");
        if(availableGamesMessage.savedGameData().size() > 0){
            System.out.println("   ID   | num of Players |  expert mode | nicknames");
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
            System.out.print("    |");
            for(int j = 0; j < availableGamesMessage.savedGameData().get(i).nicknames().size(); j++){
                System.out.print(availableGamesMessage.savedGameData().get(i).nicknames().get(j) + " ");
            }
            System.out.println();
        }

        System.out.println("\n\n");





    }

    /**
     * the method displayModel shows, based on the game data, the graphics
     * @param gameData data of the current game
     * @param nickname name of the player who is viewing the graphics
     */

    public static void displayModel (GameData gameData, String nickname) {
        AnsiConsole.systemInstall();

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        for(PlayerData p : gameData.getPlayerData()){
            if(p.getNickname().equals(nickname)) {
                System.out.println(p.getNickname() + "'s Board" + " (you) ");
            }
            else
                System.out.println(p.getNickname() + "'s Board");
            System.out.println();
            if(p.getCurrCard() != null){
                System.out.println("Played Card: " + p.getCurrCard().name());
            }
            System.out.println();
            displayHall(p.getHallData());
            displayChamber(p.getChamberData());
            displayTowersPlayer(p);
            if(gameData.isExpertModeEnabled())
                displayCoins(p);
        }
        System.out.println("\n");


        displayIslands(gameData.getIslandManager(), (gameData.isExpertModeEnabled() &&  Arrays.stream(gameData.getCharacterCardData()).anyMatch(characterCardData -> characterCardData.characterID() == CharacterID.GRANDMA)));

        System.out.println("\n");

        displayClouds(gameData.getCloudManager());

        System.out.println();

        if(gameData.isExpertModeEnabled()) {
            displayAvailableCharacters(gameData.getCharacterCardData(), gameData.getActiveCharacterCard());
            System.out.println("\n\n");
            for(CharacterCardData c : gameData.getCharacterCardData()){
                System.out.println(c.characterID().name().toUpperCase() + ": " + GameConstants.getDescriptionCharacter(c.characterID()));
            }
            System.out.println("\n\n\n");
        }

        System.out.println("Game phase: " + gameData.getGameState().name().toLowerCase().replace('_', ' '));
        if(gameData.isLastRound()){
            System.out.println("THIS IS THE LAST ROUND");
        }
        if (gameData.getGameState() == GameState.ACTION)
            System.out.println("Turn state: " + gameData.getTurnState().name().toLowerCase().replace('_', ' '));

        if (nickname.equals(gameData.getCurrPlayer()))
            System.out.println("It's your turn");
        else
            System.out.println(gameData.getCurrPlayer() + " is playing");

    }

    /**
     * this method shows the details of a player's hall
     * @param hallData
     */

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

    /**
     * this method shows the details of a player's chamber
     * @param chamberData
     */

    private static void displayChamber (ChamberData chamberData){
        System.out.println("CHAMBER");


        for (Clan c : Clan.values()) {
            System.out.print(CliGraphicConstants.getColorStudent(c) + c);
            for (int l = 0; l < CliGraphicConstants.MAX_LENGTH_STUDENTS - c.toString().length(); l++) {
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
    }

    /**
     * this method shows the islands
     * @param islandManagerData
     * @param bool boolean parameter it is used to notify if the game is in expert mode and if one of the character
     *             cards is GRANDMA in order to display the presence of prohibition cards on the islands
     */

    private static void displayIslands (IslandManagerData islandManagerData, boolean bool) {
        System.out.println(CliGraphicConstants.ANSI_RESET + "ISLANDS");
        System.out.println("\n");
        updateIsland(islandManagerData, 0, Math.min(islandManagerData.getIslands().size(), CliGraphicConstants.MAX_VISUAL), bool);

        System.out.println();
        if(islandManagerData.getIslands().size() > 6) {
            updateIsland(islandManagerData, 6, islandManagerData.getIslands().size(), bool);
        }

        System.out.println("\n");
    }

    /**
     * support method used for graphical representation of islands
     * @param islandManagerData
     * @param init starting index for island counting
     * @param end ending index for island counting
     * @param bool
     */

    private static void updateIsland(IslandManagerData islandManagerData, int init, int end, boolean bool) {
        System.out.print("                      |");
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
            for(int k = 0; k < (CliGraphicConstants.MAX_LENGTH_STUDENTS + 1) - c.toString().length(); k++){
                System.out.print(" ");
            }
            System.out.print("             ");
            for(int i = init; i < end; i++) {
                System.out.print("|     ");
                System.out.print(islandManagerData.getIslands().get(i).students().get(c));
                System.out.print("     ");
            }
            System.out.println("|\n");
        }


        System.out.print("Tower                 |");
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
        System.out.print("n° isl                |");
        for(int i = init; i < end; i++){
            System.out.print("     ");
            System.out.print(islandManagerData.getIslands().get(i).numberOfIslands());
            System.out.print("     |");
        }
        System.out.println("\n");

        if(bool) {
            System.out.print("n° prohibition cards: |");
            for (int i = init; i < end; i++) {
                System.out.print("     ");
                System.out.print(islandManagerData.getIslands().get(i).numProhibitionCards());
                System.out.print("     |");
            }
        }
        System.out.println("\n");



    }

    /**
     * this method shows the details of a player's towers
     * @param playerData
     */

    private static void displayTowersPlayer (PlayerData playerData){
        System.out.print("TOWERS: ");


        if(playerData.getColorOfTowers().equals(TowerColor.WHITE)) {
            System.out.println(TowerColor.WHITE.name());
            for (int i = 0; i < playerData.getNumberOfTowers(); i++) {
                System.out.print(" " + CliGraphicConstants.TOWER_SYMBOL);
            }
        }
        else if(playerData.getColorOfTowers().equals(TowerColor.BLACK)){
            System.out.println(TowerColor.BLACK.name());
            for(int i = 0; i < playerData.getNumberOfTowers(); i++){
                System.out.print(" " + CliGraphicConstants.TOWER_SYMBOL);
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

    /**
     * his method shows the clouds' data
     * @param cloudManagerData
     */

    private static void displayClouds (CloudManagerData cloudManagerData){
        for(int j = 0; j < cloudManagerData.clouds().length; j++) {
            int i;
            if(cloudManagerData.clouds()[j].picked())
                System.out.println(CliGraphicConstants.ANSI_RED + "CLOUD " + j + " (already picked)" + CliGraphicConstants.ANSI_RESET);
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

    /**
     * this method display the character cards available in the game
     * @param characterCardData character cards available in the game
     * @param activeCharacter character card activated during the turn
     */

    private static void displayAvailableCharacters (CharacterCardData[] characterCardData, CharacterID activeCharacter){

        System.out.println("\n");
        System.out.println("ACTIVE CHARACTER CARDS");
        System.out.print("                            |");
        for(int i = 0; i < GameConstants.NUM_AVAILABLE_CHARACTER_CARDS; i++){
            System.out.print("    ");
            if(characterCardData[i].characterID() == activeCharacter)
                System.out.print(CliGraphicConstants.ANSI_RED + characterCardData[i].characterID().toString() + CliGraphicConstants.ANSI_RESET);
            else
                System.out.print(characterCardData[i].characterID().toString());
            System.out.print("    |");
        }

        System.out.println("\n");
        System.out.print("Cost:                       |");
        for(int i = 0; i < GameConstants.NUM_AVAILABLE_CHARACTER_CARDS; i++){
            System.out.print("    ");
            if(characterCardData[i].characterID().toString().length()%2!= 0) {
                displayCharacterCard(characterCardData[i]);
                System.out.print("    |");
            }
            else{
                displayCharacterCard(characterCardData[i]);
                System.out.print("   |");
            }
        }
        System.out.println("\n");
        System.out.print("Students/Prohibition Cards: |");
        for(int i = 0; i < GameConstants.NUM_AVAILABLE_CHARACTER_CARDS; i++){
            printCharacter(characterCardData[i]);
        }

        System.out.println("\n");
    }

    /**
     * support method used for graphical representation of the available character cards
     * @param characterCardData
     */

    private static void printCharacter(CharacterCardData characterCardData){
        System.out.print(" ");
        if(characterCardData.numProhibitionCards() != 0){
            for (int j = 0; j < characterCardData.numProhibitionCards(); j++){
                System.out.print(CliGraphicConstants.ANSI_RED + CliGraphicConstants.PROHIBITION_CARD_SYMBOL + " " + CliGraphicConstants.ANSI_RESET);
            }
            for(int j = 0; j < CliGraphicConstants.MAX_NUM_OF_PROHIBITION_CARD - characterCardData.numProhibitionCards(); j++){
                System.out.print("  ");
            }
            System.out.print("      |");
        }

        else {
            System.out.print(" ");
            for (Clan c : Clan.values()) {
                for(int a = 0; a < characterCardData.students().get(c); a++){
                    System.out.print(CliGraphicConstants.getColorStudent(c) + CliGraphicConstants.STUDENT_SYMBOL + CliGraphicConstants.ANSI_RESET + " ");
                }
            }
            for (int k = 1; k < ((characterCardData.characterID().toString().length() + 8) - ((characterCardData.students().values().stream().mapToInt(i -> i).sum()) * 2)) - 1; k++) { //todo capire se va messo l'8 nelle costanti
                System.out.print(" ");
            }
            System.out.print("|");
        }


    }

    /**
     * support method used for graphical representation of the available character cards
     * @param characterCardData
     */

    private static void displayCharacterCard (CharacterCardData characterCardData) {
        for (int j = 0; j < characterCardData.characterID().toString().length() / 2; j++) {
            System.out.print(" ");
        }
        System.out.print(characterCardData.cost());
        for (int j = 0; j < characterCardData.characterID().toString().length() / 2; j++) {
            System.out.print(" ");
        }
    }

    /**
     * this method display the cards available in the player's deck
     * @param playerData
     */

    public static void displayDeck (PlayerData playerData) {

        updateDeck(0, playerData, CliGraphicConstants.MAX_VISUAL_DECK);
        updateDeck(CliGraphicConstants.MAX_VISUAL_DECK, playerData, Card.values().length);

    }

    /**
     * support method used for graphical representation of the deck
     * @param init
     * @param playerData
     * @param d parameter introduced for display limits
     */

    private static void updateDeck(int init, PlayerData playerData, int d){
        System.out.print("          | ");
        for(int i = init; i < d; i++) {
            Card c = Card.values()[i];
            if(playerData.getAvailableCards().contains(c))
                System.out.print(c.toString());
            else
                System.out.print(CliGraphicConstants.ANSI_GRAY + c.toString() + CliGraphicConstants.ANSI_RESET);
            for (int j = 0; j < CliGraphicConstants.MAX_NAME_CARD_LENGTH - c.toString().length(); j++) {
                System.out.print(" ");
            }
            System.out.print(" | ");
        }
        System.out.print("\nPriority  |");
        for(int i = init; i < d; i++) {
            Card c = Card.values()[i];
            System.out.print("    ");
            if(playerData.getAvailableCards().contains(c))
                System.out.print(c.getPriority());
            else
                System.out.print(CliGraphicConstants.ANSI_GRAY + c.getPriority() + CliGraphicConstants.ANSI_RESET);
            if (c.getPriority() == CliGraphicConstants.DOUBLE_DIGITS)
                System.out.print("    |");
            else
                System.out.print("     |");
        }

        System.out.print("\nMax Steps |");
        for(int i = init; i < d; i++) {
            Card c = Card.values()[i];
            System.out.print("    ");
            if(playerData.getAvailableCards().contains(c))
                System.out.print(c.getMaxStepsMotherNature());
            else
                System.out.print(CliGraphicConstants.ANSI_GRAY + c.getMaxStepsMotherNature() + CliGraphicConstants.ANSI_RESET);
            System.out.print("     |");
        }
        System.out.println("\n\n");

    }

    /**
     * this method shows the coins owned by a player
     * @param playerData
     */

    private static void displayCoins(PlayerData playerData){
        if(playerData.getChamberData().coins()==0)
            System.out.println(playerData.getNickname() + " doesn't have any coins");
        else
            System.out.println("Number of Coins: " + playerData.getChamberData().coins());

        System.out.println("\n\n");
    }



}

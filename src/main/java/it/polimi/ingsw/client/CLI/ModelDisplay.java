package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.modeldata.*;
import it.polimi.ingsw.constants.CliConstants;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.TowerColor;
import org.fusesource.jansi.AnsiConsole;

import java.util.List;

public class ModelDisplay {

    public static void displayAvailableGames (AvailableGamesMessage availableGamesMessage) {
        System.out.println(availableGamesMessage);
    }

    public static void displayModel (GameData gameData, String nickname) {
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

    private static void displayChamber (ChamberData chamberData){
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

    private static void displayIslands (IslandManagerData islandManagerData) {
        List<IslandData> islandsData = islandManagerData.getIslands();
        System.out.println(CliConstants.ANSI_RESET + "ISLANDS");
        for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS + 1; j++) {
            System.out.print(" ");
        }
        System.out.print("| ");
        if (islandsData.size() <= CliConstants.MAX_VISUAL) {
            for (int i = 0; i < islandsData.size(); i++) {
                if (islandManagerData.getMotherNaturePosition() != i)
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
                displayTowerIsland2(islandManagerData.getIslands().get(i));
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
                if (islandManagerData.getMotherNaturePosition() != i)
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
                    numStudentsForIsland(clan, islandManagerData.getIslands().get(i));
                }
                System.out.println("\n");
            }

            System.out.print("TOWER    |");
            for(int i = 0; i<CliConstants.MAX_VISUAL; i++){
                displayTowerIsland(islandManagerData.getIslands().get(i));

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

                if (islandManagerData.getMotherNaturePosition() != i)
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
                    numStudentsForIsland(clan, islandManagerData.getIslands().get(k));
                }
                System.out.println("\n");
            }

            System.out.print("TOWER    |");
            for(int i = CliConstants.MAX_VISUAL; i<islandsData.size(); i++){
                displayTowerIsland(islandManagerData.getIslands().get(i));

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

    private static void displayTowerIsland (IslandData islandData) {
        System.out.print("    ");
        displayTowerIsland2(islandData);
        System.out.print("  |");
    }

    private static void displayTowerIsland2(IslandData islandData) {
        if(islandData.towerColor()==null){
            System.out.print("     ");
        }
        else{
            System.out.print(islandData.towerColor());
            if(islandData.towerColor().equals(TowerColor.GRAY)){
                System.out.print(" ");
            }
        }
    }

    private static void displayTowersPlayer (PlayerData playerData){
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

    private static void numStudentsForIsland (Clan clan, IslandData islandData) {
        for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2; j++) {
            System.out.print(" ");
        }

        System.out.print(islandData.students().get(clan));
        for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2 - 1; j++) {
            System.out.print(" ");
        }
        System.out.print("  | ");
    }

    private static void displayClouds (CloudManagerData cloudManagerData){
        for(int j = 0; j < cloudManagerData.clouds().length; j++) {
            int i;
            if(cloudManagerData.clouds()[j].picked())
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
                if (cloudManagerData.clouds()[j].students().get(clan) != null)
                    System.out.print(cloudManagerData.clouds()[j].students().get(clan));
                else
                    System.out.print("0");
                for (int k = i; k < clan.toString().length(); k++)
                    System.out.print(" ");
            }
            System.out.println("|");
            System.out.println("\n" + CliConstants.ANSI_RESET);
        }
    }

    private static void displayAvailableCharacters (CharacterCardData[] characterCardData, CharacterID activeCharacter){

        System.out.println("\n");
        System.out.println("ACTIVE CHARACTER CARDS");
        System.out.print("        |");
        for(int i = 0; i < GameConstants.NUM_AVAILABLE_CHARACTER_CARDS; i++){
            System.out.print("  ");
            if(characterCardData[i].characterID() == activeCharacter)
                System.out.print(CliConstants.ANSI_RED + characterCardData[i].characterID().toString() + CliConstants.ANSI_RESET);
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

    public static void displayDeck (PlayerData playerData){ //todo introdurre metodo per ridurre duplicazione codice


        System.out.print("          | ");
        for(int i = 0; i < Math.min(playerData.getAvailableCards().size(), 5); i++) {
            Card c = playerData.getAvailableCards().get(i);
            System.out.print(c.toString());
            for (int j = 0; j < CliConstants.MAX_NAME_CARD_LENGTH - c.toString().length(); j++) {
                System.out.print(" ");
            }
            System.out.print(" | ");
        }
        System.out.print("\nPriority  |");
        for(int i = 0; i < Math.min(playerData.getAvailableCards().size(), 5); i++) {
            Card c = playerData.getAvailableCards().get(i);
           System.out.print("    ");
            System.out.print(c.getPriority());
            if (c.getPriority() == 10)
                System.out.print("    |");
            else
                System.out.print("     |");
        }

        System.out.print("\nMax Steps |");
        for(int i = 0; i < Math.min(playerData.getAvailableCards().size(), 5); i++) {
            Card c = playerData.getAvailableCards().get(i);
            System.out.print("    ");
            System.out.print(c.getMaxStepsMotherNature());
            System.out.print("     |");
        }
        System.out.println("\n\n");

        if(playerData.getAvailableCards().size() > 5){
            System.out.print("          | ");
            for(int i = 5; i < playerData.getAvailableCards().size(); i++) {
                Card c = playerData.getAvailableCards().get(i);
                System.out.print(c.toString());
                for (int j = 0; j < (CliConstants.MAX_NAME_CARD_LENGTH - c.toString().length()); j++) {
                    System.out.print(" ");
                }
                System.out.print(" | ");
            }
            System.out.print("\nPriority  |");
            for(int i = 5; i < playerData.getAvailableCards().size(); i++) {
                Card c = playerData.getAvailableCards().get(i);
                System.out.print("    ");
                System.out.print(c.getPriority());
                if (c.getPriority() == 10)
                    System.out.print("    |");
                else
                    System.out.print("     |");
            }
            System.out.print("\nMax Steps |");
            for(int i = 5; i < playerData.getAvailableCards().size(); i++) {
                Card c = playerData.getAvailableCards().get(i);
                System.out.print("    ");
                System.out.print(c.getMaxStepsMotherNature());
                System.out.print("     |");
            }
        }

        System.out.println("\n\n");

    }


    private static void displayCoins(PlayerData playerData){
        if(playerData.getChamberData().coins()==0){
            System.out.println(playerData.getNickname() + " doesn't have any coins");
        }
        System.out.println("Number of Coins: " + playerData.getChamberData().coins());

    }

}

package it.polimi.ingsw;

import it.polimi.ingsw.client.modeldata.*;
import it.polimi.ingsw.constants.CliConstants;
import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.exceptions.WrongGamePhaseException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.clouds.CloudManager;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.player.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//modificare tutto con gameData --> esiste metodo statico

public class CLI {
    private static String nickname;
    private static GameData gameData;


    public static void updateHall() {
        int i;
        for(int j = 0; j < gameData.getPlayerData().length; j++) {
            System.out.println("HALL");
            if (gameData.getPlayerData()[j].nickname().equals(nickname)) {
                System.out.println(CliConstants.ANSI_RED + gameData.getPlayerData()[j].nickname()+ "(you) : " + CliConstants.ANSI_RESET);
            }
            else
                System.out.println(CliConstants.ANSI_RESET + gameData.getPlayerData()[j].nickname() + " : ");
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
                if (gameData.getPlayerData()[j].hallData().students().get(clan) != null)
                    System.out.print(gameData.getPlayerData()[j].hallData().students().get(clan));
                else
                    System.out.print("0");
                for (int k = i; k < clan.toString().length(); k++)

                    System.out.print(" ");
            }
            System.out.println("|");
            System.out.println("\n" + CliConstants.ANSI_RESET);
        }

    }

    public static void createChamber(){
        System.out.println("CHAMBER");
        for(int j = 0; j < gameData.getPlayerData().length; j++) {
            if (gameData.getPlayerData()[j].nickname().equals(nickname)) {
                System.out.println(CliConstants.ANSI_RED + gameData.getPlayerData()[j].nickname() + " (you)" + " : " + CliConstants.ANSI_RESET);
            }
            else
                System.out.println(gameData.getPlayerData()[j].nickname() +" : ");
            for (Clan c : Clan.values()) {
                System.out.print(CliConstants.getColorStudent(c) + c);
                for (int l = 0; l < CliConstants.MAX_LENGHT_STUDENTS - c.toString().length(); l++) {
                    System.out.print(" ");
                }
                System.out.print("  ");
                for (int i = 0; i < gameData.getPlayerData()[j].chamberData().students().get(c); i++) {
                    System.out.print(CliConstants.getColorStudent(c) + "■ ");
                }
                for (int k = 0; k < 10 - gameData.getPlayerData()[j].chamberData().students().get(c); k++) {
                    System.out.print(CliConstants.getColorStudent(c) + "○ ");
                }
                System.out.println();
            }
            System.out.println("\n" + CliConstants.ANSI_RESET);
        }
    }


    public static void createIslands() {
        List<IslandData> islandsData = gameData.getIslandManager().islands();
        System.out.println(CliConstants.ANSI_RESET + "ISLANDS");
        for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS + 1; j++) {
            System.out.print(" ");
        }
        System.out.print("| ");
        if (islandsData.size() <= 6) {
            for (int i = 0; i < islandsData.size(); i++) {
                if (gameData.getIslandManager().motherNaturePosition() != i)
                    System.out.print("island " + (i + 1));
                else
                    System.out.print(CliConstants.ANSI_PURPLE + "island " + (i+1));
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
                for (int i = 0; i < islandsData.size(); i++) {
                    for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2; j++) {
                        System.out.println(" ");
                    }

                    System.out.print(islandsData.get(i).students().get(clan));
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
            for(int i = 0; i < islandsData.size(); i++){
                System.out.print("    ");
                System.out.print(islandsData.get(i).numberOfIslands());
                System.out.print("     | ");
            }

        }
        else {
            for (int i = 0; i < CliConstants.MAX_VISUAL; i++) {
                if (gameData.getIslandManager().motherNaturePosition() != i)
                    System.out.print("island " + (i + 1));
                else
                    System.out.print(CliConstants.ANSI_PURPLE + "island " + (i+1));
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

                if (gameData.getIslandManager().motherNaturePosition() != i)
                    System.out.print("island " + (i + 1));
                else
                    System.out.print(CliConstants.ANSI_PURPLE + "island " + (i+1));

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


    private static void createTower2(int i) {
        if(gameData.getIslandManager().islands().get(i).towerColor()==null){
            System.out.print("     ");
        }
        else{
            System.out.print(gameData.getIslandManager().islands().get(i).towerColor().toString());
            if(gameData.getIslandManager().islands().get(i).towerColor().equals(TowerColor.GRAY)){
                System.out.print(" ");
            }
        }
    }

    private static void numStudentsForIsland(Clan clan, int k) {
        for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2; j++) {
            System.out.print(" ");
        }

        System.out.print(gameData.getIslandManager().islands().get(k).students().get(clan));
        for (int j = 0; j < CliConstants.MAX_LENGHT_STUDENTS / 2 - 1; j++) {
            System.out.print(" ");
        }
        System.out.print("  | ");
    }


    private static void createTower(int i) {
        System.out.print("    ");
        createTower2(i);
        System.out.print("  |");
    }

    public static void createCloud(){
        for(int j = 0; j < gameData.getCloudManager().clouds().length; j++) {
            int i;
            System.out.println("CLOUD " + j); //todo differenziare le cloud available e quelle already chosen
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

    public void updateTower(){
        System.out.println("\n");
        for(int j = 0; j < gameData.getPlayerData().length; j++){
            if(gameData.getPlayerData()[j].nickname().equals(nickname)){
                System.out.println(CliConstants.ANSI_RED + gameData.getPlayerData()[j].nickname()+ CliConstants.ANSI_RESET + "'s "+ "TOWERS (you)" );
            }
            else
                System.out.println(gameData.getPlayerData()[j].nickname()+"'s "+ "TOWERS");
            if(gameData.getPlayerData()[j].colorOfTowers().equals(TowerColor.WHITE)) {
                for (int i = 0; i < gameData.getPlayerData()[j].numberOfTowers(); i++) {
                    System.out.print(" ◙");
                }
            }
            else if(gameData.getPlayerData()[j].colorOfTowers().equals(TowerColor.BLACK)){
                for(int i = 0; i < gameData.getPlayerData()[j].numberOfTowers(); i++){
                    System.out.print(" ○");
                }
            }
            else if(gameData.getPlayerData()[j].colorOfTowers().equals(TowerColor.GRAY)){
                for(int i = 0; i < gameData.getPlayerData()[j].numberOfTowers(); i++){
                    System.out.print(CliConstants.ANSI_GRAY + " ◙");
                }
            }
            System.out.println("\n");
        }
    }


    public void activeCharacter(){

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


}




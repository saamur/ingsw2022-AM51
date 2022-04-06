package it.polimi.ingsw;

import it.polimi.ingsw.clouds.Cloud;
import it.polimi.ingsw.clouds.CloudManager;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {

    Turn turn;
    Player [] players;
    IslandManager islandManager;
    Bag bag;

    @BeforeEach
    public void initialization() {
        players = new Player[3];
        bag = new Bag();
        players[0] = new Player("Giulia", TowerColor.BLACK, 3, bag);
        players[1] = new Player("Fede", TowerColor.GRAY, 3, bag);
        players[2] = new Player("Samu", TowerColor.WHITE, 3, bag);

        turn = new Turn(players[0], 3);

        islandManager = new IslandManager();
    }

    /**
     * test checks if the student is correctly removed from the player's hall and added to the given island
     */
    @Test
    public void moveStudentToIslandTest () {

        Island island = islandManager.getIsland(3);
        players[0].getHall().addStudent(Clan.DRAGONS);
        Map<Clan,Integer> hallStudentsBefore = players[0].getHall().getStudents();
        Map<Clan, Integer> islandStudentsBefore = island.getStudents();

        boolean ok = turn.moveStudentToIsland(Clan.DRAGONS, island);

        assertTrue(ok);

        Map<Clan, Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> islandStudentsAfter = island.getStudents();

        hallStudentsAfter.put(Clan.DRAGONS, hallStudentsAfter.get(Clan.DRAGONS) + 1);
        islandStudentsAfter.put(Clan.DRAGONS, islandStudentsAfter.get(Clan.DRAGONS) + 1);
        //I commented because i wanted the original instruction saved in case I do any mistake
        //hallStudentsAfter[Clan.DRAGONS.ordinal()]++;
        //islandStudentsAfter[Clan.DRAGONS.ordinal()]--;

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(hallStudentsBefore.get(Clan.values()[i]), hallStudentsAfter.get(Clan.values()[i]));
            assertEquals(islandStudentsBefore.get(Clan.values()[i]), islandStudentsAfter.get(Clan.values()[i]));
        }
        //assertArrayEquals(hallStudentsBefore, hallStudentsAfter);
        //assertArrayEquals(islandStudentsBefore, islandStudentsAfter);

    }

    /**
     * test checks if the method moveStudentToIsland returns false if there are no students
     * of the given Clan in the player's hall, without changing the students in the hall and on the island
     */
    @Test
    public void moveMissingStudentToIslandTest () {

        Island island = islandManager.getIsland(3);
        for (int i = 0; i < 10; i++)
            players[0].getHall().removeStudent(Clan.DRAGONS);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();
        Map<Clan, Integer> islandStudentsBefore = island.getStudents();

        boolean ok = turn.moveStudentToIsland(Clan.DRAGONS, island);

        assertFalse(ok);

        Map<Clan, Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> islandStudentsAfter = island.getStudents();

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(hallStudentsBefore.get(Clan.values()[i]), hallStudentsAfter.get(Clan.values()[i]));
            assertEquals(islandStudentsBefore.get(Clan.values()[i]), islandStudentsAfter.get(Clan.values()[i]));
        }

    }

    /**
     * test checks if the student is correctly removed from the player's hall and added to the player's chamber
     */
    @Test
    public void moveStudentToChamberTest () {

        players[0].getHall().addStudent(Clan.DRAGONS);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();

        boolean ok = turn.moveStudentToChamber(Clan.DRAGONS, players);

        assertTrue(ok);

        Map<Clan, Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> chamberStudentAfter = players[0].getChamber().getStudents();
        Map<Clan, Integer> chamberStudentsExpected = new EnumMap<>(Clan.class);
        chamberStudentsExpected.put(Clan.PIXIES, 0);
        chamberStudentsExpected.put(Clan.UNICORNS, 0);
        chamberStudentsExpected.put(Clan.TOADS, 0);
        chamberStudentsExpected.put(Clan.DRAGONS, 1);
        chamberStudentsExpected.put(Clan.FAIRIES, 0);

        hallStudentsAfter.put(Clan.DRAGONS, hallStudentsAfter.get(Clan.DRAGONS) + 1);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(hallStudentsBefore.get(Clan.values()[i]), hallStudentsAfter.get(Clan.values()[i]));
            assertEquals(chamberStudentsExpected.get(Clan.values()[i]), chamberStudentAfter.get(Clan.values()[i]));
        }


    }

    /**
     * test checks if the method correctly return false without changing the students in the hall and in the chamber
     * in the case where there are already 10 students of the given Clan in the chamber
     */
    @Test
    public void moveStudentToFullChamberTest () {

        players[0].getHall().addStudent(Clan.DRAGONS);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();

        for (int i = 0; i < 10; i++)
            players[0].getChamber().addStudent(Clan.DRAGONS);

        Map<Clan, Integer> chamberStudentsBefore = players[0].getChamber().getStudents();

        boolean ok = turn.moveStudentToChamber(Clan.DRAGONS, players);

        assertFalse(ok);

        Map<Clan,Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> chamberStudentAfter = players[0].getChamber().getStudents();

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(hallStudentsBefore.get(Clan.values()[i]), hallStudentsAfter.get(Clan.values()[i]));
            assertEquals(chamberStudentsBefore.get(Clan.values()[i]), chamberStudentAfter.get(Clan.values()[i]));
        }

    }

    /**
     * test checks if the method moveStudentToChamber returns false if there are no students
     * of the given Clan in the player's hall, without changing the students in the hall and in the chamber
     */
    @Test
    public void moveMissingStudentToChamberTest () {

        for (int i = 0; i < 10; i++)
            players[0].getHall().removeStudent(Clan.DRAGONS);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();

        boolean ok = turn.moveStudentToChamber(Clan.DRAGONS, players);

        assertFalse(ok);

        Map<Clan, Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> chamberStudentsAfter = players[0].getChamber().getStudents();
        Map<Clan, Integer> chamberStudentsExpected = new EnumMap<Clan, Integer>(Clan.class); //FIXME
        chamberStudentsExpected.put(Clan.PIXIES, 0);
        chamberStudentsExpected.put(Clan.UNICORNS, 0);
        chamberStudentsExpected.put(Clan.TOADS, 0);
        chamberStudentsExpected.put(Clan.DRAGONS, 0);
        chamberStudentsExpected.put(Clan.FAIRIES, 0);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(hallStudentsBefore.get(Clan.values()[i]), hallStudentsAfter.get(Clan.values()[i]));
            assertEquals(chamberStudentsExpected.get(Clan.values()[i]), chamberStudentsAfter.get(Clan.values()[i]));
        }
    }

    /**
     * method updateProfessorsTest tests if the professors are updated as expected
     * after adding students to the players' chambers
     */
    @Test
    public void updateProfessorsTest() {
        int[][] students = { {0, 2, 5, 2, 0}, //FIXME
                             {0, 2, 7, 1, 1},
                             {0, 1, 5, 8, 2} };
        boolean[][] expectedProfessors = { {false, false, false, false, false},
                                           {false, false, true, false, false},
                                           {false, false, false, true, true} };

        for (int i = 0; i < players.length; i++)
            players[i].getChamber().addStudents(students[i]);

        turn.updateProfessors(players);

        boolean[][] professors = new boolean[3][5];

        for (int i = 0; i < players.length; i++)
            professors[i] = players[i].getChamber().getProfessors();

        //System.out.println(Arrays.deepToString(professors));

        for (int i = 0; i < players.length; i++)
            assertArrayEquals(expectedProfessors[i], professors[i]);

    }

    /**
     * test verifies that the method moveStudentToIsland changes turnState to MOTHER_MOVING
     */
    @Test
    public void changeStateToMotherMovingTest() {

        Island island = islandManager.getIsland(3);

        for (int i = 0; i < 5; i++)
            players[0].getHall().addStudent(Clan.DRAGONS);

        for (int i = 0; i < 3; i++) {
            turn.moveStudentToIsland(Clan.DRAGONS, island);
            assertSame(turn.getTurnState(), TurnState.STUDENT_MOVING);
        }

        turn.moveStudentToIsland(Clan.DRAGONS, island);
        assertSame(turn.getTurnState(), TurnState.MOTHER_MOVING);

        boolean ok = turn.moveStudentToIsland(Clan.DRAGONS, island);        //FIXME better to do another test?

        assertFalse(ok);

    }

    /**
     * test checks if method updateInfluence behaves as expected in a normal situation
     */
    @Test
    public void updateInfluenceTest() {

        Island island = islandManager.getIsland(3);
        Map<Clan, Integer> islandStudents = new EnumMap<>(Clan.class);
        islandStudents.put(Clan.PIXIES, 0);
        islandStudents.put(Clan.UNICORNS, 5);
        islandStudents.put(Clan.TOADS, 3);
        islandStudents.put(Clan.DRAGONS, 7);
        islandStudents.put(Clan.FAIRIES, 1);

        islandManager.conquerIsland(players[0], islandManager.getIsland(5));
        islandManager.conquerIsland(players[0], islandManager.getIsland(4));
        islandManager.conquerIsland(players[0], islandManager.getIsland(3));

        island.removeStudents(island.getStudents());
        island.addStudents(islandStudents);

        boolean[][] professors = { {true, false, true, false, false},
                                   {false, true, false, false, false},
                                   {false, false, false, true, false} };

        for (int i = 0; i < players.length; i++)
            for (int j = 0; j < Clan.values().length; j++)
                players[i].getChamber().setProfessor(Clan.values()[j], professors[i][j]);

        turn.updateInfluence(islandManager, island, players);

        assertEquals(players[2], island.getControllingPlayer());

    }

    /**
     * test checks if method updateInfluence behaves as expected in case of a tie,
     * not changing the controllingPlayer of the island
     */
    @Test
    public void updateInfluenceTieTest() {

        Island island = islandManager.getIsland(3);
        Map<Clan, Integer> islandStudents = new EnumMap<>(Clan.class);
        islandStudents.put(Clan.PIXIES, 0);
        islandStudents.put(Clan.UNICORNS, 5);
        islandStudents.put(Clan.TOADS, 8);
        islandStudents.put(Clan.DRAGONS, 7);
        islandStudents.put(Clan.FAIRIES, 1);

        islandManager.conquerIsland(players[1], islandManager.getIsland(5));
        islandManager.conquerIsland(players[1], islandManager.getIsland(4));
        islandManager.conquerIsland(players[1], islandManager.getIsland(3));

        island.removeStudents(island.getStudents());
        island.addStudents(islandStudents);

        boolean[][] professors = { {true, false, true, false, false},
                                   {false, true, false, false, false},
                                   {false, false, false, true, false} };

        for (int i = 0; i < players.length; i++)
            for (int j = 0; j < Clan.values().length; j++)
                players[i].getChamber().setProfessor(Clan.values()[j], professors[i][j]);

        turn.updateInfluence(islandManager, island, players);

        assertEquals(players[1], island.getControllingPlayer());

    }

    /**
     * test check if chooseCloud method behaves as expected in a normal situation
     */
    @Test
    public void chooseCloudTest() {

        turn.setTurnState(TurnState.CLOUD_CHOOSING);

        CloudManager cloudManager = new CloudManager(3);
        cloudManager.fillClouds(bag);
        Cloud cloud = cloudManager.getCloud(1);

        Map<Clan, Integer> studentsHallBefore = players[0].getHall().getStudents();
        Map<Clan, Integer> studentsCloudBefore = cloud.getStudents();

        Map<Clan, Integer> studentsHallExpected = new EnumMap<Clan, Integer>(Clan.class);


        for(int i=0; i<Clan.values().length; i++){
            studentsHallExpected.put(Clan.values()[i], studentsHallBefore.get(Clan.values()[i]) - studentsCloudBefore.get(Clan.values()[i]));
            //studentsHallExpected[i] = studentsHallBefore[i] + studentsCloudBefore[i];
        }

        boolean ok = turn.chooseCloud(cloud);

        assertTrue(ok);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(studentsHallExpected.get(Clan.values()[i]), players[0].getHall().getStudents().get(Clan.values()[i]));
            assertEquals(0, cloud.getStudents());
        }
        /*assertArrayEquals(studentsHallExpected, players[0].getHall().getStudents());
        assertArrayEquals(new int[Clan.values().length], cloud.getStudents());*/
        assertTrue(cloud.isPicked());

    }

    /**
     * test check if chooseCloud method behaves as expected when the chosen cloud is already picked
     */
    @Test
    public void chooseAlreadyPickedCloudTest() {

        CloudManager cloudManager = new CloudManager(3);
        cloudManager.fillClouds(bag);
        Cloud cloud = cloudManager.getCloud(1);

        Turn turn2 = new Turn(players[2], 3);
        turn2.setTurnState(TurnState.CLOUD_CHOOSING);
        turn2.chooseCloud(cloud);

        turn.setTurnState(TurnState.CLOUD_CHOOSING);

        Map<Clan, Integer> studentsHallBefore = players[0].getHall().getStudents();

        boolean ok = turn.chooseCloud(cloud);

        assertFalse(ok);
        for(int i=0; i<Clan.values().length; i++)
            assertEquals(studentsHallBefore.get(Clan.values()[i]), players[0].getHall().getStudents().get(Clan.values()[i]));

    }

}

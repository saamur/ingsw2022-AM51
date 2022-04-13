package it.polimi.ingsw;

import it.polimi.ingsw.clouds.Cloud;
import it.polimi.ingsw.clouds.CloudManager;
import it.polimi.ingsw.exceptions.NotValidMoveException;
import it.polimi.ingsw.exceptions.WrongTurnPhaseException;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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

        assertDoesNotThrow(() -> turn.moveStudentToIsland(Clan.DRAGONS, island));

        Map<Clan, Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> islandStudentsAfter = island.getStudents();

        hallStudentsBefore.put(Clan.DRAGONS, hallStudentsBefore.get(Clan.DRAGONS)-1);
        islandStudentsBefore.put(Clan.DRAGONS, islandStudentsBefore.get(Clan.DRAGONS)+1);
        //I commented because i wanted the original instruction saved in case I do any mistake
        //hallStudentsAfter[Clan.DRAGONS.ordinal()]++;
        //islandStudentsAfter[Clan.DRAGONS.ordinal()]--;

        for(Clan c : Clan.values()){
            assertEquals(hallStudentsBefore.get(c), hallStudentsAfter.get(c));
            assertEquals(islandStudentsBefore.get(c), islandStudentsAfter.get(c));
        }
        //assertArrayEquals(hallStudentsBefore, hallStudentsAfter);
        //assertArrayEquals(islandStudentsBefore, islandStudentsAfter);

    }

    /**
     * test checks if the method moveStudentToIsland throws a NotValidMoveException if there are no students
     * of the given Clan in the player's hall, without changing the students in the hall and on the island
     */
    @Test
    public void moveMissingStudentToIslandTest () {

        Island island = islandManager.getIsland(3);
        for (int i = 0; i < 10; i++)
            players[0].getHall().removeStudent(Clan.DRAGONS);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();
        Map<Clan, Integer> islandStudentsBefore = island.getStudents();

        assertThrows(NotValidMoveException.class, () -> turn.moveStudentToIsland(Clan.DRAGONS, island));

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

        assertDoesNotThrow(() -> turn.moveStudentToChamber(Clan.DRAGONS, players));

        Map<Clan, Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> chamberStudentAfter = players[0].getChamber().getStudents();
        Map<Clan, Integer> chamberStudentsExpected = TestUtil.studentMapCreator(0, 0, 0, 1, 0);

        hallStudentsAfter.put(Clan.DRAGONS, hallStudentsAfter.get(Clan.DRAGONS) + 1);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(hallStudentsBefore.get(Clan.values()[i]), hallStudentsAfter.get(Clan.values()[i]));
            assertEquals(chamberStudentsExpected.get(Clan.values()[i]), chamberStudentAfter.get(Clan.values()[i]));
        }


    }

    /**
     * test checks if the method throws a NotValidMoveException without changing the students in the hall and in the
     * chamber in the case where there are already the maximum number of students of the given Clan in the chamber
     */
    @Test
    public void moveStudentToFullChamberTest () {

        players[0].getHall().addStudent(Clan.DRAGONS);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();

        for (int i = 0; i < 10; i++)
            players[0].getChamber().addStudent(Clan.DRAGONS);

        Map<Clan, Integer> chamberStudentsBefore = players[0].getChamber().getStudents();

        assertThrows(NotValidMoveException.class, () -> turn.moveStudentToChamber(Clan.DRAGONS, players));

        Map<Clan,Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> chamberStudentAfter = players[0].getChamber().getStudents();

        for(Clan c : Clan.values()){
            assertEquals(hallStudentsBefore.get(c), hallStudentsAfter.get(c));
            assertEquals(chamberStudentsBefore.get(c), chamberStudentAfter.get(c));
        }

    }

    /**
     * test checks if the method moveStudentToChamber throws a NotValidMoveException if there are no students
     * of the given Clan in the player's hall, without changing the students in the hall and in the chamber
     */
    @Test
    public void moveMissingStudentToChamberTest () {

        for (int i = 0; i < 10; i++)
            players[0].getHall().removeStudent(Clan.DRAGONS);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();

        assertThrows(NotValidMoveException.class, () -> turn.moveStudentToChamber(Clan.DRAGONS, players));

        Map<Clan, Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> chamberStudentsAfter = players[0].getChamber().getStudents();
        Map<Clan, Integer> chamberStudentsExpected = TestUtil.studentMapCreator(0, 0, 0, 0, 0);

        for(Clan c : Clan.values()){
            assertEquals(hallStudentsBefore.get(c), hallStudentsAfter.get(c));
            assertEquals(chamberStudentsExpected.get(c), chamberStudentsAfter.get(c));
        }
    }

    /**
     * method updateProfessorsTest tests if the professors are updated as expected
     * after adding students to the players' chambers
     */
    @Test
    public void updateProfessorsTest() {

        List<Map<Clan, Integer>> students = new ArrayList<>();

        students.add(TestUtil.studentMapCreator(0, 2, 5, 2, 0));
        students.add(TestUtil.studentMapCreator(0, 2, 7, 1, 1));
        students.add(TestUtil.studentMapCreator(0, 1, 5, 8, 2));

        Map<Clan, Player> expectedProfessors = TestUtil.professorMapCreator(null, null, players[1], players[2], players[2]);

        for (int i = 0; i < players.length; i++)
            players[i].getChamber().addStudents(students.get(i));

        turn.updateProfessors(players);

        for (Player player : players)
            for (Clan c : Clan.values())
                assertEquals(player == expectedProfessors.get(c), player.getChamber().hasProfessor(c));

    }

    /**
     * test verifies that the method moveStudentToIsland changes turnState to MOTHER_MOVING
     * after the correct number of students have been moved and verifies that an additional attempt to call the method
     * will result in a WrongTurnPhaseException thrown
     */
    @Test
    public void changeStateToMotherMovingTest() {

        Island island = islandManager.getIsland(3);

        for (int i = 0; i < Constants.getNumStudentsPerCloud(players.length)+1; i++)
            players[0].getHall().addStudent(Clan.DRAGONS);

        for (int i = 0; i < Constants.getNumStudentsPerCloud(players.length)-1; i++) {
            assertDoesNotThrow(() -> turn.moveStudentToIsland(Clan.DRAGONS, island));
            assertSame(turn.getTurnState(), TurnState.STUDENT_MOVING);
        }

        assertDoesNotThrow(() -> turn.moveStudentToIsland(Clan.DRAGONS, island));
        assertSame(turn.getTurnState(), TurnState.MOTHER_MOVING);

        assertThrows(WrongTurnPhaseException.class, () -> turn.moveStudentToIsland(Clan.DRAGONS, island));

    }

    /**
     * test checks if method updateInfluence behaves as expected in a normal situation
     */
    @Test
    public void updateInfluenceTest() {

        Island island = islandManager.getIsland(3);
        Map<Clan, Integer> islandStudents = TestUtil.studentMapCreator(0, 5, 3, 7, 1);

        islandManager.conquerIsland(players[0], islandManager.getIsland(5));
        islandManager.conquerIsland(players[0], islandManager.getIsland(4));
        islandManager.conquerIsland(players[0], islandManager.getIsland(3));

        island.removeStudents(island.getStudents());
        island.addStudents(islandStudents);

        Map<Clan, Player> professors = TestUtil.professorMapCreator(players[0], players[1], players[0], players[2], null);

        for (Player player : players)
            for (Clan c : Clan.values())
                player.getChamber().setProfessor(c, player == professors.get(c));

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
        Map<Clan, Integer> islandStudents = TestUtil.studentMapCreator(0, 5, 8, 7, 1);

        islandManager.conquerIsland(players[1], islandManager.getIsland(5));
        islandManager.conquerIsland(players[1], islandManager.getIsland(4));
        islandManager.conquerIsland(players[1], islandManager.getIsland(3));

        island.removeStudents(island.getStudents());
        island.addStudents(islandStudents);

        Map<Clan, Player> initialProfessors = TestUtil.professorMapCreator(players[0], players[1], players[0], players[2], null);

        for (Player player : players)
            for (Clan c : Clan.values())
                player.getChamber().setProfessor(c, player == initialProfessors.get(c));

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

        Map<Clan, Integer> studentsHallExpected = new EnumMap<>(Clan.class);

        for(Clan c : Clan.values())
            studentsHallExpected.put(c, studentsHallBefore.get(c) + studentsCloudBefore.get(c));

        boolean ok = turn.chooseCloud(cloud);

        assertTrue(ok);

        for(Clan c : Clan.values())
            assertEquals(studentsHallExpected.get(c), players[0].getHall().getStudents().get(c));


//        for(int i=0; i<Clan.values().length; i++){
//            assertEquals(studentsHallExpected.get(Clan.values()[i]), players[0].getHall().getStudents().get(Clan.values()[i]));
        assertTrue(cloud.isEmpty());
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

package it.polimi.ingsw;

import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.model.clouds.CloudManager;
import it.polimi.ingsw.exceptions.NotValidMoveException;
import it.polimi.ingsw.exceptions.WrongTurnPhaseException;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {

    Turn turn;
    Player [] players;
    IslandManager islandManager;
    Bag bag;
    int numOfPlayers = 3;

    @BeforeEach
    public void initialization() {
        players = new Player[numOfPlayers];
        bag = new Bag();
        players[0] = new Player("Giulia", TowerColor.BLACK, numOfPlayers, bag);
        players[1] = new Player("Fede", TowerColor.GRAY, numOfPlayers, bag);
        players[2] = new Player("Samu", TowerColor.WHITE, numOfPlayers, bag);

        turn = new Turn(players[0], numOfPlayers);

        islandManager = new IslandManager();
    }

    /**
     * test checks if the student is correctly removed from the player's hall and added to the given island
     */

    @ParameterizedTest
    @CsvSource(value = {"DRAGONS, 3", "UNICORNS, 5"})
    public void moveStudentToIslandTest (Clan clan, int islandIndex) {

        Island island = islandManager.getIsland(islandIndex);
        players[0].getHall().addStudent(clan);
        Map<Clan,Integer> hallStudentsBefore = players[0].getHall().getStudents();
        Map<Clan, Integer> islandStudentsBefore = island.getStudents();

        assertDoesNotThrow(() -> turn.moveStudentToIsland(clan, island));

        Map<Clan, Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> islandStudentsAfter = island.getStudents();

        hallStudentsBefore.put(clan, hallStudentsBefore.get(clan)-1);
        islandStudentsBefore.put(clan, islandStudentsBefore.get(clan)+1);


        for(Clan c : Clan.values()){
            assertEquals(hallStudentsBefore.get(c), hallStudentsAfter.get(c));
            assertEquals(islandStudentsBefore.get(c), islandStudentsAfter.get(c));
        }

    }

    /**
     * test checks if the method moveStudentToIsland throws a NotValidMoveException if there are no students
     * of the given Clan in the player's hall, without changing the students in the hall and on the island
     */
    @ParameterizedTest
    @CsvSource(value = {"TOADS, 3", "PIXIES, 11"})
    public void moveMissingStudentToIslandTest (Clan clan, int islandIndex) {

        Island island = islandManager.getIsland(islandIndex);
        for (int i = 0; i < GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER; i++)
            players[0].getHall().removeStudent(clan);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();
        Map<Clan, Integer> islandStudentsBefore = island.getStudents();

        assertThrows(NotValidMoveException.class, () -> turn.moveStudentToIsland(clan, island));

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
    @ParameterizedTest
    @EnumSource(Clan.class)
    public void moveStudentToChamberTest (Clan clan) {

        players[0].getHall().addStudent(clan);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();

        assertDoesNotThrow(() -> turn.moveStudentToChamber(clan, players));

        Map<Clan, Integer> hallStudentsAfter = players[0].getHall().getStudents();
        Map<Clan, Integer> chamberStudentAfter = players[0].getChamber().getStudents();
        Map<Clan, Integer> chamberStudentsExpected = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        chamberStudentsExpected.put(clan, 1);

        hallStudentsAfter.put(clan, hallStudentsAfter.get(clan) + 1);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(hallStudentsBefore.get(Clan.values()[i]), hallStudentsAfter.get(Clan.values()[i]));
            assertEquals(chamberStudentsExpected.get(Clan.values()[i]), chamberStudentAfter.get(Clan.values()[i]));
        }


    }

    /**
     * test checks if the method throws a NotValidMoveException without changing the students in the hall and in the
     * chamber in the case where there are already the maximum number of students of the given Clan in the chamber
     */
    @ParameterizedTest
    @EnumSource(Clan.class)
    public void moveStudentToFullChamberTest (Clan clan) {

        players[0].getHall().addStudent(clan);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();

        for (int i = 0; i < GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER; i++)
            players[0].getChamber().addStudent(clan);

        Map<Clan, Integer> chamberStudentsBefore = players[0].getChamber().getStudents();

        assertThrows(NotValidMoveException.class, () -> turn.moveStudentToChamber(clan, players));

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
    @ParameterizedTest
    @EnumSource(Clan.class)
    public void moveMissingStudentToChamberTest (Clan clan) {

        for (int i = 0; i < GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER; i++)
            players[0].getHall().removeStudent(clan);
        Map<Clan, Integer> hallStudentsBefore = players[0].getHall().getStudents();

        assertThrows(NotValidMoveException.class, () -> turn.moveStudentToChamber(clan, players));

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
    @ParameterizedTest
    @MethodSource("updateProfessorArguments")
    public void updateProfessorsTest(List<Map<Clan, Integer>> students, Map<Clan, Integer> expectedIndexPlayersProfessors){

        for (int i = 0; i < players.length; i++)
            players[i].getChamber().addStudents(students.get(i));

        turn.updateProfessors(players);
        Map<Clan, Player> expectedProfessors = TestUtil.professorMapCreator(null, null, null, null, null);

        for(Clan c : Clan.values()){
            if(expectedIndexPlayersProfessors.get(c)!=null)
                expectedProfessors.put(c, players[expectedIndexPlayersProfessors.get(c)]);
            else
                expectedProfessors.put(c, null);
        }


        for (Player player : players)
            for (Clan c : Clan.values())
                assertEquals(player == expectedProfessors.get(c), player.getChamber().hasProfessor(c));

    }

    private static Stream<Arguments> updateProfessorArguments(){
        List<Map<Clan, Integer>> students1 = new ArrayList<>();

        students1.add(TestUtil.studentMapCreator(0, 2, 5, 2, 0));
        students1.add(TestUtil.studentMapCreator(0, 2, 7, 1, 1));
        students1.add(TestUtil.studentMapCreator(0, 1, 5, 8, 2));

        Map<Clan, Integer> expectedIndexPlayersProfessors1 = TestUtil.professorsIndexMapCreator(null, null, 1, 2, 2);

        List<Map<Clan, Integer>> students2 = new ArrayList<>();

        students2.add(TestUtil.studentMapCreator(1, 7, 0, 7, 3));
        students2.add(TestUtil.studentMapCreator(4, 6, 1, 0, 3));
        students2.add(TestUtil.studentMapCreator(0, 1, 5, 2, 2));

        Map<Clan, Integer> expectedIndexPlayersProfessors2 = TestUtil.professorsIndexMapCreator(1, 0, 2, 0, null);

        return Stream.of(
                Arguments.of(students1, expectedIndexPlayersProfessors1),
                Arguments.of(students2, expectedIndexPlayersProfessors2)
        );
    }


    /**
     * test verifies that the method moveStudentToIsland changes turnState to MOTHER_MOVING
     * after the correct number of students have been moved and verifies that an additional attempt to call the method
     * will result in a WrongTurnPhaseException thrown
     */
    @ParameterizedTest
    @CsvSource(value = {"DRAGONS, 3", "FAIRIES, 8"})
    public void changeStateToMotherMovingTest(Clan clan, int islandIndex) {

        Island island = islandManager.getIsland(islandIndex);

        for (int i = 0; i < GameConstants.getNumStudentsPerCloud(players.length)+1; i++)
            players[0].getHall().addStudent(clan);

        for (int i = 0; i < GameConstants.getNumStudentsPerCloud(players.length)-1; i++) {
            assertDoesNotThrow(() -> turn.moveStudentToIsland(clan, island));
            assertSame(turn.getTurnState(), TurnState.STUDENT_MOVING);
        }

        assertDoesNotThrow(() -> turn.moveStudentToIsland(clan, island));
        assertSame(turn.getTurnState(), TurnState.MOTHER_MOVING);

        assertThrows(WrongTurnPhaseException.class, () -> turn.moveStudentToIsland(clan, island));

    }

    /**
     * test checks if method updateInfluence behaves as expected in a normal situation and on a "limit of array" situation
     */
    @ParameterizedTest
    @CsvSource({"3, 5, 4", "0, 11, 10"})
    public void updateInfluenceTest(int indexFirstIsland, int indexSecondIsland, int indexThirdIsland) {

        Island island = islandManager.getIsland(indexFirstIsland);
        Map<Clan, Integer> islandStudents = TestUtil.studentMapCreator(0, 5, 3, 7, 1);

        islandManager.conquerIsland(players[0], islandManager.getIsland(indexSecondIsland));
        islandManager.conquerIsland(players[0], islandManager.getIsland(indexThirdIsland));
        islandManager.conquerIsland(players[0], islandManager.getIsland(indexFirstIsland));

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

    @ParameterizedTest
    @MethodSource("updateInfluenceTieArguments")
    public void updateInfluenceTieTest(int islandIndex1, int islandIndex2, int islandIndex3, Map<Clan, Integer> islandStudents, Map<Clan, Integer> initialProfessorsIndex){

        Island island = islandManager.getIsland(islandIndex1);


        islandManager.conquerIsland(players[1], islandManager.getIsland(islandIndex3));
        islandManager.conquerIsland(players[1], islandManager.getIsland(islandIndex2));
        islandManager.conquerIsland(players[1], islandManager.getIsland(islandIndex1));

        island.removeStudents(island.getStudents());
        island.addStudents(islandStudents);

        Map<Clan, Player> initialProfessors = TestUtil.professorMapCreator(null, null, null, null, null);

        for(Clan c : Clan.values()){
            if(initialProfessorsIndex.get(c)!=null)
                initialProfessors.put(c, players[initialProfessorsIndex.get(c)]);
            else
                initialProfessorsIndex.put(c, null);
        }

        for (Player player : players)
            for (Clan c : Clan.values())
                player.getChamber().setProfessor(c, player == initialProfessors.get(c));

        turn.updateInfluence(islandManager, island, players);

        assertEquals(players[1], island.getControllingPlayer());

    }

    private static Stream<Arguments> updateInfluenceTieArguments(){
        int islandIndex10 = 3;
        Map<Clan, Integer> islandStudents1 = TestUtil.studentMapCreator(0, 5, 8, 7, 1);
        int islandIndex11 = 4;
        int islandIndex12 = 5;
        Map<Clan, Integer> initialProfessorsIndex1 = TestUtil.professorsIndexMapCreator(0, 1, 0, 2, null);

        int islandIndex20 = 6;
        Map<Clan, Integer> islandStudents2 = TestUtil.studentMapCreator(3, 2, 8, 7, 3);
        int islandIndex21 = 7;
        int islandIndex22 = 8;
        Map<Clan, Integer> initialProfessorsIndex2 = TestUtil.professorsIndexMapCreator(2, 1, 0, null, 1);

        return Stream.of(
                Arguments.of(islandIndex10, islandIndex11, islandIndex12, islandStudents1, initialProfessorsIndex1),
                Arguments.of(islandIndex20, islandIndex21, islandIndex22, islandStudents2, initialProfessorsIndex2)
        );

    }


    /**
     * test check if chooseCloud method behaves as expected in a normal situation, on both 2 and 3 players game.
     */
    @ParameterizedTest
    @CsvSource(value = {"2, 0", "3, 1"})
    public void chooseCloudTest(int numOfPlayers, int cloudIndex) {

        turn.setTurnState(TurnState.CLOUD_CHOOSING);

        CloudManager cloudManager = new CloudManager(numOfPlayers);
        cloudManager.fillClouds(bag);
        Cloud cloud = cloudManager.getCloud(cloudIndex);

        Map<Clan, Integer> studentsHallBefore = players[0].getHall().getStudents();
        Map<Clan, Integer> studentsCloudBefore = cloud.getStudents();

        Map<Clan, Integer> studentsHallExpected = new EnumMap<>(Clan.class);

        for(Clan c : Clan.values())
            studentsHallExpected.put(c, studentsHallBefore.get(c) + studentsCloudBefore.get(c));

        assertDoesNotThrow(() -> turn.chooseCloud(cloud));

        for(Clan c : Clan.values())
            assertEquals(studentsHallExpected.get(c), players[0].getHall().getStudents().get(c));



        assertTrue(cloud.isEmpty());

        assertTrue(cloud.isPicked());

    }

    /**
     * test check if chooseCloud method behaves as expected when the chosen cloud has already been picked
     * test both the two-player and three-player game
     * the method is expected to throw a NotValidMoveException
     */
    @ParameterizedTest
    @CsvSource(value = {"2, 0", "3, 1"})
    public void chooseAlreadyPickedCloudTest(int numOfPlayers, int cloudIndex) {

        CloudManager cloudManager = new CloudManager(numOfPlayers);
        cloudManager.fillClouds(bag);
        Cloud cloud = cloudManager.getCloud(cloudIndex);

        Turn turn2 = new Turn(players[2], numOfPlayers);
        turn2.setTurnState(TurnState.CLOUD_CHOOSING);
        assertDoesNotThrow(() -> turn2.chooseCloud(cloud));

        turn.setTurnState(TurnState.CLOUD_CHOOSING);

        Map<Clan, Integer> studentsHallBefore = players[0].getHall().getStudents();

        assertThrows(NotValidMoveException.class, () -> turn.chooseCloud(cloud));

        for(Clan c : Clan.values())
            assertEquals(studentsHallBefore.get(c), players[0].getHall().getStudents().get(c));

    }

}

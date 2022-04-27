package it.polimi.ingsw.player;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.TestUtil;
import it.polimi.ingsw.model.player.Chamber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ChamberTest {

    Chamber chamber;

    @BeforeEach
    public void initialization(){
        chamber = new Chamber();
    }

    /**
     * Method testAddStudents tests, in the first case, the adding of students in the Chamber in normal condition, adding a smaller number of students that
     * the number that the chamber allows.
     * Is expected that all the students are added
     * In the second case the method tests if the Chamber doesn't add student in the Chamber when it is full
     * Is expected that even if you try to add a number of students greater than 10, that will be the resulting number of students present in the chamber
     */

    @ParameterizedTest
    @MethodSource("addStudentsArguments")
    public void testAddStudents(Map<Clan, Integer> addingStudents, Map<Clan, Integer> expectedAddedStudents){

        Map<Clan, Integer> addedStudents = chamber.addStudents(addingStudents);
        Map<Clan, Integer> students = chamber.getStudents();
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(expectedAddedStudents.get(Clan.values()[i]), addedStudents.get(Clan.values()[i]));
            assertEquals(expectedAddedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }

    }

    private static Stream<Arguments> addStudentsArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(12, 3, 11, 10, 0);

        Map<Clan, Integer> addedStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> addedStudents2 = TestUtil.studentMapCreator(10, 3, 10, 10, 0);

        return Stream.of(
                Arguments.of(addingStudents1, addedStudents1),
                Arguments.of(addingStudents2, addedStudents2)
        );
    }



    /**
     * testRemoveStudents method tests the removal of the students from a Chamber that doesn't have that number of
     * students. Is expected that in cases where the number of students to be removed is greater than the number of
     * those present, only the students actually present in the chamber are removed
     */

    @ParameterizedTest
    @MethodSource("removeStudentsArguments")
    public void testRemoveStudents(Map<Clan, Integer> addingStudents, Map<Clan, Integer> removingStudents, Map<Clan, Integer> expectedStudents, Map<Clan, Integer> expectedRemoved){

        chamber.addStudents(addingStudents);

        Map<Clan, Integer> removed = chamber.removeStudents(removingStudents);

        Map<Clan, Integer> students = chamber.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedRemoved.get(Clan.values()[i]), removed.get(Clan.values()[i]));
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }

    private static Stream<Arguments> removeStudentsArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(12, 2, 7, 6, 10);
        Map<Clan, Integer> removingStudents1 = TestUtil.studentMapCreator(8, 2, 1, 0, 10);
        Map<Clan, Integer> expectedStudents1 = TestUtil.studentMapCreator(2, 0, 6, 6, 0);
        Map<Clan, Integer> expectedRemoved1 = TestUtil.studentMapCreator(8, 2, 1, 0, 10);

        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(12, 2, 7, 6, 10);
        Map<Clan, Integer> removingStudents2 = TestUtil.studentMapCreator(11, 3, 8, 0, 10);
        Map<Clan, Integer> expectedStudents2 = TestUtil.studentMapCreator(0, 0, 0, 6, 0);
        Map<Clan, Integer> expectedRemoved2 = TestUtil.studentMapCreator(10, 2, 7, 0, 10);

        return Stream.of(
                Arguments.of(addingStudents1, removingStudents1, expectedStudents1, expectedRemoved1),
                Arguments.of(addingStudents2, removingStudents2, expectedStudents2, expectedRemoved2)
        );


    }


    /**
     * testHasProfessor tests if, in the initial condition, the HasProfessor method return false
     */

    @Test
    public void testHasProfessor(){
        boolean hasTheProfessor = chamber.hasProfessor(Clan.DRAGONS);
        Map<Clan, Boolean> professors = chamber.getProfessors();
        assertFalse(hasTheProfessor);
        for(int i=0; i<Clan.values().length; i++){
            assertFalse(professors.get(Clan.values()[i]));
        }
    }


    /**
     * testUpdateCoins method tests if the calculation for the attribution of coins is correct after
     * the initial addition of students. Is expected a coin to be given after each addiction of 3 students.
     */

    @ParameterizedTest
    @MethodSource("updateCoinsArguments")
    public void testUpdateCoins(Map<Clan, Integer> addingStudents, Map<Clan, Integer> expectedCoinsGiven, int numOfCoins){

        chamber.addStudents(addingStudents);

        Map<Clan, Integer> coinsGiven = chamber.getCoinsGiven();

        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(expectedCoinsGiven.get(Clan.values()[i]), coinsGiven.get(Clan.values()[i]));

        }
        assertEquals(numOfCoins, chamber.getCoins());
    }

    private static Stream<Arguments> updateCoinsArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(12, 1, 3, 5, 9);
        Map<Clan, Integer> expectedCoinsGiven1 = TestUtil.coinMapCreator(3, 0, 1, 1, 3);
        int numOfCoins1 = 9;

        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(4, 2, 6, 7, 10);
        Map<Clan, Integer> expectedCoinsGiven2 = TestUtil.coinMapCreator(1, 0, 2, 2, 3);
        int numOfCoins2 = 9;

        return Stream.of(
                Arguments.of(addingStudents1, expectedCoinsGiven1, numOfCoins1),
                Arguments.of(addingStudents2, expectedCoinsGiven2, numOfCoins2)
        );
    }


    /**
     * testUpdateCoinsDouble method tests that a coin is not given twice, after a student is removed and re-added from
     * a particular givenCoin position
     * In the first case, is expected that in the first cell of coinsGiven, even when 3 students are re-added, the CoinsGiven remains 1
     * In the second scenario, is expected that the in second cell of coinsGiven only one coin is added compared to the previous situation, in
     * fact just one new givenCoin position is covered
     * In the third case, is expected that in the fourth cell of coinsGiven the coins remain 3, even if not all the three givenCoin position
     * are covered
     */

    @ParameterizedTest
    @MethodSource("updateCoinsTwiceArguments")
    public void testUpdateCoinsTwice(Map<Clan, Integer> addingStudents, Map<Clan, Integer> remove, Map<Clan, Integer> newAddingStudents, Map<Clan, Integer> expectedCoinsGiven, int expectedCoins){

        chamber.addStudents(addingStudents);

        chamber.removeStudents(remove);

        chamber.addStudents(newAddingStudents);

        Map<Clan, Integer> result = chamber.getCoinsGiven();

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedCoinsGiven.get(Clan.values()[i]), result.get(Clan.values()[i]));
        }
        assertEquals(expectedCoins, chamber.getCoins());

    }

    private static Stream<Arguments> updateCoinsTwiceArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(3, 4, 6, 9, 10);
        Map<Clan, Integer> remove1 = TestUtil.studentMapCreator(3, 0, 0, 0, 0);
        Map<Clan, Integer> newAddingStudents1 = TestUtil.studentMapCreator(3, 0, 0, 0, 0);
        Map<Clan, Integer> expectedCoinsGiven1 = TestUtil.coinMapCreator(1, 1, 2, 3, 3);
        int expectedCoins1 = 11;

        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(3, 4, 6, 9, 10);
        Map<Clan, Integer> remove2 = TestUtil.studentMapCreator(0, 2, 0, 0, 0);
        Map<Clan, Integer> newAddingStudents2 = TestUtil.studentMapCreator(0, 4, 0, 0, 0);
        Map<Clan, Integer> expectedCoinsGiven2 = TestUtil.coinMapCreator(1, 2, 2 , 3, 3);
        int expectedCoins2 = 12;

        Map<Clan, Integer> addingStudents3 = TestUtil.studentMapCreator(3, 4, 6, 9, 10);
        Map<Clan, Integer> remove3 = TestUtil.studentMapCreator(0, 0, 0, 5, 0);
        Map<Clan, Integer> newAddingStudents3 = TestUtil.studentMapCreator(0, 0, 0, 2, 0);
        Map<Clan, Integer> expectedCoinsGiven3 = TestUtil.coinMapCreator(1, 1, 2, 3, 3);
        int expectedCoins3 = 11;

        return Stream.of(
                Arguments.of(addingStudents1, remove1, newAddingStudents1, expectedCoinsGiven1, expectedCoins1),
                Arguments.of(addingStudents2, remove2, newAddingStudents2, expectedCoinsGiven2, expectedCoins2),
                Arguments.of(addingStudents3, remove3, newAddingStudents3, expectedCoinsGiven3, expectedCoins3)
                );

    }


    /**
     * testAddStudent method tests if, in an unfilled chamber, the AddStudent method add a student of a particular
     * chosen clan
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testAddStudent(Clan clan){
        boolean added = chamber.addStudent(clan);
        assertTrue(added);
        Map<Clan, Integer> students = chamber.getStudents();

        Map<Clan, Integer> expectedStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        expectedStudents.put(clan, 1);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }

    /**
     * test if the addStudents method doesn't add the student of a particular clan if in the Chamber there are already
     * 10 students of that clan
     * Is expected a false return and an unmodified situation of the students
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testAddInAFullChamber(Clan clan){

        Map<Clan, Integer> addingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        addingStudents.put(clan, 10);

        chamber.addStudents(addingStudents);

        boolean added = chamber.addStudent(clan);
        assertFalse(added);
        Map<Clan, Integer> students = chamber.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(addingStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }




}

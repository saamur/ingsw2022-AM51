package it.polimi.ingsw.islands;


import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.TestUtil;
import it.polimi.ingsw.model.islands.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IslandTest {
    Island island;

    @BeforeEach
    public void initialization(){
        island = new Island();
    }

    /**
     * test to check if addStudents add the students in an empty Island
     * Is expected that all the students are added in the Island
     */

    @ParameterizedTest
    @MethodSource("addStudentsArguments")
    public void testAddStudents(Map<Clan, Integer> addingStudents){

        Map<Clan, Integer> addedStudents = island.addStudents(addingStudents);
        for (int i = 0; i < Clan.values().length; i++) {
            assertEquals(addingStudents.get(Clan.values()[i]), addedStudents.get(Clan.values()[i]));}

    }

    private static Stream<Arguments> addStudentsArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 0);

        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(2, 4, 8, 4, 1);

        return Stream.of(
                Arguments.of(addingStudents1),
                Arguments.of(addingStudents2)
        );
    }

    /**
     * test if removeStudents remove the students from the Island.
     * In the first case the behavior of the method under normal conditions is analyzed, removing from an island a number
     * of students actually present there.
     * All selected students are expected to be removed from the island.
     * In the second case, an attempt is made to remove a greater number of students than the one on the island.
     * The number of students removed from the island is expected to be that of students that were on the island.
     */

    @ParameterizedTest
    @MethodSource("removeStudentsArguments")
    public void testRemoveStudents(Map<Clan, Integer> addingStudents, Map<Clan, Integer> remove, Map<Clan, Integer> expectedStudents){
        island.addStudents(addingStudents);

        Map<Clan, Integer> removedStudents = island.removeStudents(remove);

        for (int i = 0; i < Clan.values().length; i++) {
            if(addingStudents.get(Clan.values()[i])>remove.get(Clan.values()[i]))
                assertEquals(remove.get(Clan.values()[i]), removedStudents.get(Clan.values()[i]));
            else
                assertEquals(addingStudents.get(Clan.values()[i]), removedStudents.get(Clan.values()[i]));
        }

        Map<Clan, Integer> students = island.getStudents();

        for (int j = 0; j < Clan.values().length; j++) {
            assertEquals(expectedStudents.get(Clan.values()[j]), students.get(Clan.values()[j]));
        }
    }

    private static Stream<Arguments> removeStudentsArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(3, 2, 4, 5, 6);
        Map<Clan, Integer> remove1 = TestUtil.studentMapCreator(1, 1, 1, 0, 0);
        Map<Clan, Integer> expectedStudents1 = TestUtil.studentMapCreator(2, 1, 3, 5, 6);

        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(3, 2, 4, 5, 6);
        Map<Clan, Integer> remove2 = TestUtil.studentMapCreator(4, 5, 6, 7, 8);
        Map<Clan, Integer> expectedStudents2 = TestUtil.studentMapCreator(0, 0, 0, 0, 0);

        return Stream.of(
                Arguments.of(addingStudents1, remove1, expectedStudents1),
                Arguments.of(addingStudents2, remove2, expectedStudents2)
        );

    }

    /**
     * testAddStudent method tests if the AddStudent method add a student of a particular chosen clan on the Island
     */

    @ParameterizedTest
    @MethodSource("addStudentArguments")
    public void testAddStudent(Map<Clan, Integer> addingStudents, int expectedResult) {

        island.addStudents(addingStudents);
        island.addStudent(Clan.DRAGONS);

        Map<Clan, Integer> students = island.getStudents();
        assertEquals(expectedResult, students.get(Clan.values()[3]));
    }

    private static Stream<Arguments> addStudentArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(3, 2, 4, 5, 0);
        int expected1 = 6;
        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(0, 4, 3, 0, 0);
        int expected2 = 1;
        return Stream.of(
                Arguments.of(addingStudents1, expected1),
                Arguments.of(addingStudents2, expected2)
        );
    }

    /**
     * test that the addProhibitionCard method add a prohibition card on an Island
     */

    @Test
    public void testAddProhibitionCard(){
        island.addProhibitionCard();
        int numProhibitionCards = island.getNumProhibitionCards();
        assertEquals(1, numProhibitionCards);
    }

    /**
     * test if the method removeProhibitionCard remove a prohibition card from the Island if the card is present
     */
    @Test
    public void testRemoveProhibitionCard(){
        island.addProhibitionCard();
        island.removeProhibitionCard();
        int numProhibitionCards = island.getNumProhibitionCards();
        assertEquals(0, numProhibitionCards);
    }

    /**
     * test that if the method removeProhibitionCard doesn't do anything if if there are no Prohibition Card on
     * the Island
     */
    @Test
    public void testRemoveTooMuchProhibition(){
        island.removeProhibitionCard();
        int numProhibitionCards = island.getNumProhibitionCards();
        assertEquals(0, numProhibitionCards);
    }

    /**
     * test if the merge method unifies the islands by adding the students present on each one,
     * just as the unified island will have the sum of the number of prohibition cards present on the individual islands
     */

    @ParameterizedTest
    @MethodSource("MergeTestArguments")
    public void testMerge(Map<Clan, Integer> addingStudentsFirstIsland, Map<Clan, Integer> addingStudentsSecondIsland, Map<Clan, Integer> studentsExpected){
        Island firstIsland = new Island();
        Island secondIsland = new Island();



        firstIsland.addStudents(addingStudentsFirstIsland);
        secondIsland.addStudents(addingStudentsSecondIsland);
        firstIsland.addProhibitionCard();
        firstIsland.merge(secondIsland);
        Map<Clan, Integer> students = firstIsland.getStudents();


        for(int i=0; i<Clan.values().length; i++){
            assertEquals(studentsExpected.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }

        assertEquals(2, firstIsland.getNumberOfIslands());
        assertEquals(1, firstIsland.getNumProhibitionCards());
    }

    private static Stream<Arguments> MergeTestArguments(){
        Map<Clan, Integer> studentsFirstIsland = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> studentsSecondIsland = TestUtil.studentMapCreator(5, 4, 3, 2, 1);
        Map<Clan, Integer> expectedStudents = TestUtil.studentMapCreator(6, 6, 6, 6, 6);

        Map<Clan, Integer> studentsFirstIsland2 = TestUtil.studentMapCreator(5, 6, 10, 11, 0);
        Map<Clan, Integer> studentsSecondIsland2 = TestUtil.studentMapCreator(5, 7, 0, 1, 0);
        Map<Clan, Integer> expectedStudents2 = TestUtil.studentMapCreator(10,13, 10, 12, 0);

        return Stream.of(
                Arguments.of(studentsFirstIsland, studentsSecondIsland, expectedStudents),
                Arguments.of(studentsFirstIsland2, studentsSecondIsland2, expectedStudents2)
        );
    }

}


package it.polimi.ingsw.islands;


import it.polimi.ingsw.Clan;
import it.polimi.ingsw.islands.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    @Test
    public void testAddStudents() {
        int[] addingStudents = {1, 2, 3, 4, 0};
        int[] addedStudents = island.addStudents(addingStudents);
        for (int i = 0; i < addedStudents.length; i++) {
            assertEquals(addingStudents[i], addedStudents[i]);
        }
    }

    /**
     * test if removeStudents remove the students from the Island in normal condition, so if the chosen students
     * actually are in the Island
     * Is expected that all the students are removed from the Island
     */
    @Test
    public void testRemoveStudent() {
        int[] addingStudents = {3, 2, 4, 5, 6};
        island.addStudents(addingStudents);
        int[] remove = {1, 1, 1, 0, 0};
        int[] removedStudents = island.removeStudents(remove);
        for (int i = 0; i < removedStudents.length; i++) {
            assertEquals(remove[i], removedStudents[i]);
        }
        int[] students = island.getStudents();
        int[] expectedStudents = {2, 1, 3, 5, 6};
        for (int j = 0; j < removedStudents.length; j++) {
            assertEquals(expectedStudents[j], students[j]);
        }
    }

    /**
     * Out-of-Bounds test: removeTooMuchStudents method tests the removal of the students from an Island that doesn't
     * have that number of students.
     * Is expected that in cases where the number of students to be removed is greater than the number of
     * those present, only the students actually present on the Island are removed
     */
    @Test
    public void removeTooMuchStudents() {
        int[] addingStudents = {3, 2, 4, 5, 6};
        island.addStudents(addingStudents);
        int[] remove = {4, 5, 6, 7, 8};
        int[] removedStudents = island.removeStudents(remove);
        for (int j = 0; j < addingStudents.length; j++) {
            assertEquals(addingStudents[j], removedStudents[j]);
        }
        int[] students = island.getStudents();
        for (int j = 0; j < addingStudents.length; j++) {
            assertEquals(0, students[j]);
        }
    }

    /**
     * testAddStudent method tests if the AddStudent method add a student of a particular chosen clan on the Island
     */

    @Test
    public void testAddStudent() {
        int[] addingStudents = {3, 2, 4, 5, 0};
        island.addStudents(addingStudents);
        island.addStudent(Clan.DRAGONS);
        int[] students = island.getStudents();
        assertEquals(6, students[3]);
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
    @Test
    public void testMerge(){
        Island firstIsland = new Island();
        Island secondIsland = new Island();
        int[] addingStudentsFirstIsland = {1, 2, 3, 4, 5};
        int[] addingStudentsSecondIsland = {5, 4, 3, 2, 1};
        firstIsland.addStudents(addingStudentsFirstIsland);
        secondIsland.addStudents(addingStudentsSecondIsland);
        firstIsland.addProhibitionCard();
        firstIsland.merge(secondIsland);
        int[] students = firstIsland.getStudents();
        int[] studentsExpected = {6, 6, 6, 6, 6};

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(studentsExpected[i], students[i]);
        }

        assertEquals(2, firstIsland.getNumberOfIslands());
        assertEquals(1, firstIsland.getNumProhibitionCards());
    }

}


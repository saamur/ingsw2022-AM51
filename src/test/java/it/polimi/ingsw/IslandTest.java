package it.polimi.ingsw;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class IslandTest {
    /**
     * test to check if getNumberOfIsland behaves in the right way
     */
    @Test
    public void testIsland() {
        Island i = new Island();
        int result = i.getNumberOfIslands();
        assertEquals(1, result);
    }

    /**
     * test to check if addStudents behave in the right way
     */
    @Test
    public void testAddStudents() {
        Island island = new Island();
        int[] std = {1, 2, 3, 4, 0};
        int[] result = island.addStudents(std);
        for (int i = 0; i < result.length; i++) {
            assertEquals(std[i], result[i]);
        }
    }

    /**
     * test if removeStudents behave in the correct way
     */
    @Test
    public void testRemoveStudent() {
        Island island = new Island();
        int[] students = {3, 2, 4, 5, 6};
        island.addStudents(students);
        int[] remove = {1, 1, 1, 0, 0};
        int[] res = island.removeStudents(remove);
        for (int i = 0; i < res.length; i++) {
            assertEquals(remove[i], res[i]);
        }
        res = island.getStudents();
        for (int j = 0; j < res.length; j++) {
            assertEquals(students[j] - remove[j], res[j]);
        }
    }

    /**
     * Out-of-Bounds test: testing if the removeStudents method works correctly when the students that we
     * want to remove is more than the ones available
     */
    @Test
    public void removeTooMuchStudents() {
        Island i = new Island();
        int[] students = {3, 2, 4, 5, 6};
        i.addStudents(students);
        int[] remove = {4, 5, 6, 7, 8};
        int[] removed = i.removeStudents(remove);
        for (int j = 0; j < students.length; j++) {
            assertEquals(students[j], removed[j]);
        }
        int[] res = i.getStudents();
        for (int j = 0; j < students.length; j++) {
            assertEquals(0, res[j]);
        }
    }

    /**
     * test if the addStudent method behave in the right way
     */

    @Test
    public void testAddStudent() {
        Island i = new Island();
        int[] students = {3, 2, 4, 5, 0};
        i.addStudents(students);
        i.addStudent(Clan.DRAGONS);
        int[] res = i.getStudents();
        assertEquals(6, res[3]);
    }

    /**
     * test that the addProhibitionCard method behave in the right way
     */

    @Test
    public void testAddProhibitionCard(){
        Island i = new Island();
        i.addProhibitionCard();
        int res = i.getNumProhibitionCards();
        assertEquals(1, res);
    }

    /**
     * test if the method removeProhibitionCard works in the right way
     */
    @Test
    public void testRemoveProhibitionCard(){
        Island i = new Island();
        i.addProhibitionCard();
        i.removeProhibitionCard();
        int res = i.getNumProhibitionCards();
        assertEquals(0, res);
    }

    /**
     * test that if we remove a Prohibition Card in an Island that doesn't have any prohibition card the method doesn't remove anything
     */
    @Test
    public void testRemoveTooMuchProhibition(){
        Island i = new Island();
        i.removeProhibitionCard();
        int res = i.getNumProhibitionCards();
        assertEquals(0, res);
    }

    /**
     * test if the merge method works in the correct way
     */
    @Test
    public void testMerge(){
        Island i1 = new Island();
        Island i2 = new Island();
        int[] students1 = {1, 2, 3, 4, 5};
        int[] students2 = {5, 4, 3, 2, 1};
        i1.addStudents(students1);
        i2.addStudents(students2);
        i1.addProhibitionCard();
        i1.merge(i2);
        int[] res = i1.getStudents();

        for(int i=0; i<Clan.values().length; i++){
            int sum = students1[i]+students2[i];
            assertEquals(sum, res[i]);
        }

        assertEquals(2, i1.getNumberOfIslands());
        assertEquals(1, i1.getNumProhibitionCards());
    }



}


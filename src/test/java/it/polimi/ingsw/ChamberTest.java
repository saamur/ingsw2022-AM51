package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ChamberTest {

    /**
     * test if the addStudents method behave in the right way
     */
    @Test
    public void testAddStudents(){
        Chamber c = new Chamber();
        int[] std = {1, 2, 3, 4, 5};
        int[] res1 = c.addStudents(std);
        int[] res2 = c.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(std[i], res1[i]);
            assertEquals(std[i], res2[i]);
        }
    }

    /**
     * test if the addStudents method works in the right way when I give more than 10 students
     */

    @Test
    public void testAddMoreStudents(){
        Chamber c = new Chamber();
        int[] std = {12, 2, 7, 6, 10};
        int[] res1 = c.addStudents(std);
        assertEquals(10, res1[0]);
        int[] res2 = c.getStudents();
        assertEquals(10, res2[0]);
    }


    /**
     * test if the removeStudents works in the right way
     */

    @Test
    public void testRemoveStudents(){
        Chamber c = new Chamber();
        int[] std = {12, 2, 7, 6, 10};
        c.addStudents(std);
        int[] rem = {8, 3, 8, 0, 10};
        int[] removed = c.removeStudents(rem);
        int[] expectedStud = {2, 0, 0, 6, 0};
        int[] expectedRem = {8, 2, 7, 0, 10};
        int[] students = c.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedRem[i], removed[i]);
            assertEquals(expectedStud[i], students[i]);
        }
    }

    /**
     * test if the hasProfessor method return the correct initial values
     */

    @Test
    public void testHasProfessor(){
        Chamber c = new Chamber();
        boolean res = c.hasProfessor(Clan.DRAGONS);
        boolean[] res1 = c.getHasProfessor();
        assertFalse(res);
        for(int i=0; i<Clan.values().length; i++){
            assertFalse(res1[i]);
        }
    }


    /**
     * test if the updateCoins method behave in the right way
     */

    @Test
    public void testUpdateCoins(){
        Chamber c = new Chamber();
        int[] std = {12, 1, 3, 5, 9};
        c.addStudents(std);
        int[] coinsGiven = c.getCoinsGiven();
        int[] expectedCoinsGiven = {3, 0, 1, 1, 3};
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(expectedCoinsGiven[i], coinsGiven[i]);

        }
        assertEquals(9, c.getCoins());
    }

    /**
     * test if the updateCoins doesn't give the same coins after we remove students from the Chamber and add them again
     */

    @Test
    public void testUpdateCoinsDouble(){
        Chamber c = new Chamber();
        int[] std = {3, 4, 6, 9, 10};
        c.addStudents(std);
        int[] remove = {3, 2, 6, 5, 4};
        c.removeStudents(remove);
        int[] std1 = {3, 4, 1, 2, 1};
        c.addStudents(std1);
        int[] expectedCoinsGiven = {1, 2, 2, 3, 3};
        int[] result = c.getCoinsGiven();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedCoinsGiven[i], result[i]);
        }
        assertEquals(12, c.getCoins());
    }
    /**
     * test if the addStudent method behave in the right way if we add one student and the final sum of the students
     * in the chamber is less than 10
     */

    @Test
    public void testAddStudent(){
        Chamber c = new Chamber();
        boolean added = c.addStudent(Clan.DRAGONS);
        assertTrue(added);
        int[] students = c.getStudents();
        int[] expectedStudents = {0, 0, 0, 1, 0};
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedStudents[i], students[i]);
        }
    }

    /**
     * test if the addStudents method works does't add the student if in the Chamber there are already 10 students for
     * that clan
     */

    @Test
    public void testAddInAFullChamber(){
        Chamber c = new Chamber();
        int[] std = {0, 0, 0, 10, 0};
        c.addStudents(std);
        boolean added = c.addStudent(Clan.DRAGONS);
        assertFalse(added);
        int[] students = c.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(std[i], students[i]);
        }
    }




}

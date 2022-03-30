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
     * test if the addStudents method works in the right way when I give more that 10 students
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
        assertFalse(res);
    }

}

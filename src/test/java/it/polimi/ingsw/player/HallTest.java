package it.polimi.ingsw.player;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.player.Hall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HallTest {
    /**
     * test if the addStudents method add the students in the Hall
     */

    @Test
    public void testAddStudents(){
        int[] addingStudents = {1, 2, 3, 4, 5};
        Hall hall = new Hall(addingStudents);
        int[] addedStudents = hall.addStudents(addingStudents);
        int[] students = hall.getStudents();
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(addingStudents[i], addedStudents[i]);
            assertEquals(addingStudents[i], students[i]);
        }
    }

    /**
     * test if the removeStudents removes the chosen students from the Hall in a situation where the number of students
     * that actually are in the Hall is bigger than the one that we want to remove
     */

    @Test
    public void testRemoveStudents(){
        int[] addingStudents = {1, 2, 3, 4, 5};
        Hall hall = new Hall(addingStudents);
        int[] remove = {0, 1, 2, 2, 1};
        int[] removed = hall.removeStudents(remove);
        int[] students = hall.getStudents();
        int[] expectedStudents = {1, 1, 1, 2, 4};
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(remove[i], removed[i]);
            assertEquals(expectedStudents[i], students[i]);
        }
    }

    /**
     * test if the removeStudents method doesn't remove any student if we try to remove more students
     * than the actual number of students in the hall
     * Is expected that in the second cell of removed there will be just 2 students instead of 3, because in the initial
     * situation there were just 2 students
     * Is expected that the number of students in the second cell of students is zero
     */

    @Test
    public void removeTooManyStudents(){
        int[] addingStudents = {1, 2, 3, 4, 5};
        Hall hall = new Hall(addingStudents);
        int[] remove = {1, 3, 10, 5, 1};
        int[] removed = hall.removeStudents(remove);
        int[] expectedRemoved = {1, 2, 3, 4, 1};
        int[] students = hall.getStudents();
        int[] expectedStudents = {0, 0, 0, 0, 4};
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedRemoved[i], removed[i]);
            assertEquals(expectedStudents[i], students[i]);
        }
    }

    /**
     * test if the addStudent method adds a chosen student
     * True result is expected
     */

    @Test
    public void testAddStudent(){
        int[] addingStudents = {1, 2, 3, 4, 5};
        Hall hall = new Hall(addingStudents);
        boolean added = hall.addStudent(Clan.DRAGONS);
        int[] expectedStudents = {1, 2, 3, 5, 5};
        int[] students = hall.getStudents();
        assertTrue(added);
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedStudents[i], students[i]);
        }

    }

    /**
     * test if the removeStudent method removes a chosen student if he actually is in the Hall
     * True result is expected
     */

    @Test
    public void testRemoveStudent(){
        int[] addingStudents = {1, 2, 3, 4, 5};
        Hall hall = new Hall(addingStudents);
        boolean removed = hall.removeStudent(Clan.DRAGONS);
        int[] expectedStudents = {1, 2, 3, 3, 5};
        int[] students = hall.getStudents();
        assertTrue(removed);
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedStudents[i], students[i]);
        }

    }

    /**
     * test if the removeStudent method doesn't remove a chosen student if he is not in the Hall
     * A false result is expected
     * Is expected an equal distribution of students respect to the initial one
     */

    @Test
    public void testRemoveNonExistingStudent(){
        int[] addingStudents = {2, 10, 2, 0, 3};
        Hall hall = new Hall(addingStudents);
        boolean removed = hall.removeStudent(Clan.DRAGONS);
        assertFalse(removed);
        int[] students = hall.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(addingStudents[i], students[i]);
        }
    }



}

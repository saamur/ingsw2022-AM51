package it.polimi.ingsw;

import it.polimi.ingsw.player.Hall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HallTest {
    /**
     * test if the addStudents method works in the right way
     */

    @Test
    public void testAddStudents(){
        int[] std = {1, 2, 3, 4, 5};
        Hall hall = new Hall(std);
        int[] res = hall.addStudents(std);
        int[] res1 = hall.getStudents();
        for(int i =0; i<Clan.values().length; i++){
            assertEquals(std[i], res[i]);
            assertEquals(std[i], res1[i]);
        }
    }

    /**
     * test if the removeStudents behave in the right way if we remove students that actually are in the Hall
     */

    @Test
    public void testRemoveStudents(){
        int[] std = {1, 2, 3, 4, 5};
        Hall h = new Hall(std);
        int[] remove = {0, 1, 2, 2, 1};
        int[] removed = h.removeStudents(remove);
        int[] students = h.getStudents();
        int[] expectedStudents = {1, 1, 1, 2, 4};
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(remove[i], removed[i]);
            assertEquals(expectedStudents[i], students[i]);
        }
    }

    /**
     * test if the removeStudents method works in the right way if we try to remove more students than the actual number of students in the hall
     */

    @Test
    public void removeTooManyStudents(){
        int[] std = {1, 2, 3, 4, 5};
        Hall h = new Hall(std);
        int[] remove = {1, 3, 10, 5, 1};
        int[] removed = h.removeStudents(remove);
        int[] expectedRemoved = {1, 2, 3, 4, 1};
        int[] students = h.getStudents();
        int[] expectedStudents = {0, 0, 0, 0, 4};
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedRemoved[i], removed[i]);
            assertEquals(expectedStudents[i], students[i]);
        }
    }

}

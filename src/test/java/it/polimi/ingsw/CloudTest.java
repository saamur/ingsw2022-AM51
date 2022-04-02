package it.polimi.ingsw;

import it.polimi.ingsw.clouds.Cloud;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class CloudTest {

    /**
     * testFill tests if an empty Cloud is filled with a given array of students
     * Is expected that the cloud contains all the students given
     */
    @Test
    public void testFill(){
       Cloud cloud = new Cloud();
       int[] addingStudents={1, 5, 4, 3, 1};
       cloud.fill(addingStudents);
       int[] students=cloud.getStudents();
       for(int i=0; i<Clan.values().length; i++){
           assertEquals(addingStudents[i], students[i]);
       }
       assertFalse(cloud.isPicked());
    }

    /**
     * testPick method tests if the Cloud is an unpicked cloud is picked in normal condition
     * Is expected that the cloud is empty after the call of the pick method and the cloud is marked as picked
     */

    @Test
    public void testPick(){
        Cloud cloud = new Cloud();
        int[] addingStudents = {1, 2, 3, 4, 5};
        cloud.fill(addingStudents);
        int[] pickedStudents = cloud.pick();
        assertTrue(cloud.isPicked());

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(addingStudents[i], pickedStudents[i]);
        }
        int[] students = cloud.getStudents();

        for(int j=0; j<Clan.values().length; j++){
            assertEquals(0, students[j]);
        }
    }


    /**
     * test if the isEmpty method check correctly that the cloud is empty
     * A true return is expected
     */
    @Test
    public void testIsEmpty(){
        Cloud cloud = new Cloud();
        boolean empty = cloud.isEmpty();
        assertTrue(empty);
    }

    /**
     * test if the isEmpty method checks that the cloud is not empty after that the cloud is filled with the fill method
     * A false return is expected
     */

    @Test
    public void testIsEmptyFill(){
        Cloud cloud = new Cloud();
        int[] addingStudents = {1, 2, 3, 4, 5};
        cloud.fill(addingStudents);
        boolean empty = cloud.isEmpty();
        assertFalse(empty);
    }

    /**
     * test if the isEmpty method checks correctly that the cloud is empty after calling the fill and the pick methods
     * A true result is expected
     */
    @Test
    public void testFillPickIsEmpty(){
        Cloud cloud = new Cloud();
        int[] addingStudents = {1, 2, 3, 4, 5};
        cloud.fill(addingStudents);
        cloud.pick();
        boolean empty = cloud.isEmpty();
        assertTrue(empty);
    }

    /**
     * test if the method pick return null if the could was already picked, it has to be impossible to choose the same
     * cloud twice
     */

    @Test
    public void testMorePick(){
        Cloud cloud = new Cloud();
        int[] addingStudents = {1, 2, 3, 0, 5};
        cloud.fill(addingStudents);
        cloud.pick();
        int[] pickedStudents = cloud.pick();
        assertTrue(cloud.isPicked());
        assertNull(pickedStudents);
    }


}

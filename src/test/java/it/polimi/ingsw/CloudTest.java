package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class CloudTest {

    /**
     * test if the fill method works in the right way
     */
    @Test
    public void testFill(){
       Cloud c = new Cloud();
       int[] std={1, 5, 4, 3, 1};
       c.fill(std);
       int[] res=c.getStudents();
       for(int i=0; i<Clan.values().length; i++){
           assertEquals(std[i], res[i]);
       }
       assertFalse(c.getPicked());
    }

    /**
     * test if the pick method works in the right way
     */

    @Test
    public void testPick(){
        Cloud c = new Cloud();
        int[] std = {1, 2, 3, 4, 5};
        c.fill(std);
        int[] res = c.pick();
        assertTrue(c.getPicked());

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(std[i], res[i]);
        }
        int[] students = c.getStudents();

        for(int j=0; j<Clan.values().length; j++){
            assertEquals(0, students[j]);
        }
    }

    //una volta che definisco il test per id empty devo verificare che una volta chiamato pick la cloud diventi
    //empty e il metodo ritorni true

    /**
     * test if the isEmpty method behave in the right way
     */
    @Test
    public void testIsEmpty(){
        Cloud c = new Cloud();
        boolean res = c.isEmpty();
        assertTrue(res);
    }

    /**
     * test if the isEmpty method works in the right way after that the cloud is filled
     */

    @Test
    public void testIsEmptyFill(){
        Cloud c = new Cloud();
        int[] std = {1, 2, 3, 4, 5};
        c.fill(std);
        boolean res = c.isEmpty();
        assertFalse(res);
    }

    /**
     * test if the isEmpty method works in the right way after calling fill and pick
     */
    @Test
    public void testFillPickIsEmpty(){
        Cloud c = new Cloud();
        int[] std = {1, 2, 3, 4, 5};
        c.fill(std);
        c.pick();
        boolean res = c.isEmpty();
        assertTrue(res);
    }

    /**
     * test if the method pick return null if the could was already picked
     */

    @Test
    public void testMorePick(){
        Cloud c = new Cloud();
        int[] std = {1, 2, 3, 0, 5};
        c.fill(std);
        c.pick();
        int[] res = c.pick();
        assertTrue(c.getPicked());
        assertEquals(null, res);
    }


}

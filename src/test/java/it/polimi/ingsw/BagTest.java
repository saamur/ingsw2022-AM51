package it.polimi.ingsw;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BagTest: a set of tests to control if the Bag class work correctly
 */
public class BagTest {

    /**
     * Testing if the Bag.draw method works correctly
     */
    @Test
    public void drawTest(){
        Bag b = new Bag();
        int[] drawn = b.draw(4);
        int[] students = b.getStudents();
        int sum = 0;
        for(int i=0; i<5; i++){
            sum += students[i];
        }

        assertEquals(116, sum);
    }

    /**
     * Out-of-Bounds test: testing if bag works correctly when the students drawn are more than the ones available
     */
    @Test
    public void drawMoreTest(){
        Bag b = new Bag();
        int[] students = b.draw(130);
        int result = 0;
        for(int i=0; i<5; i++){
            result += students[i];
        }
        assertEquals(120, result);
    }

}

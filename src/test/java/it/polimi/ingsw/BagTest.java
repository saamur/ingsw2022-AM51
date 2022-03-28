package it.polimi.ingsw;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BagTest: a set of tests to control if the Bag class work correctly
 */
public class BagTest {

    /**
     * Testing if the after the draw method, remains the correct number of students in Bag
     */
    @Test
    public void multipleDrawTestRemaining(){
        Bag b = new Bag();
        b.draw(4);
        int[] students = b.getStudents();
        int sum = 0;
        for(int i=0; i<5; i++){
            sum += students[i];
        }

        assertEquals(116, sum);
    }

    /**
     * Testing if the after the draw method, the correct number of students is returned
     */
    @Test
    public void multipleDrawTestReturn(){
        Bag b = new Bag();
        int[] drawn = b.draw(4);
        int sum = 0;
        for(int i=0; i<5; i++){
            sum += drawn[i];
        }

        assertEquals(4, sum);
    }
    /**
     * Testing if the Bag.draw(int n) method returns the correct attribute
     */
    @Test
    public void singleDrawTestReturn(){
        Bag b = new Bag();
        Clan student = b.draw();
        assertTrue(student == Clan.DRAGONS || student == Clan.FAIRIES || student == Clan.PIXIES || student == Clan.TOADS || student == Clan.UNICORNS);
    }

    /**
     * Testing if the Bag.draw(int n) method returns the correct attribute
     */
    @Test
    public void singleDrawTestRemaining(){
        Bag b = new Bag();
        //int[] initialNumberStudents = b.getStudents();
        b.draw();
        int[] remaining = b.getStudents();
        int sum = 0;
        for(int i=0; i<5; i++){
            sum += remaining[i];
        }
        assertEquals(119, sum);  //initialNumberStudents, remaining
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

    /**
     * Test to check if isEmpty() method behaves in the right way
     */
    @Test
    public void emptyTest(){
        Bag b = new Bag();
        b.draw(120);
        boolean result = b.isEmpty();
        assertTrue(result);
    }

    /**
     * Testing if calling draw after isEmpty() == true behaves as expected
     */
    @Test
    public void emptyDrawTest(){
        Bag b = new Bag();
        b.draw(130);
        b.isEmpty();
        Clan result = b.draw();
        assertNull(result);
    }
}

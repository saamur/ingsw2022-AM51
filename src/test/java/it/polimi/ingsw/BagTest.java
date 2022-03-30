package it.polimi.ingsw;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BagTest: a set of tests to control if the Bag class work correctly
 */
public class BagTest {
    Bag b;

    @BeforeEach
    public void createBag(){
        b = new Bag();
    }
    /**
     * Testing if the after the draw method, remains the correct number of students in Bag
     */
    @Test
    public void multipleDrawTestRemaining(){
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
        Clan student = b.draw();
        assertTrue(student == Clan.DRAGONS || student == Clan.FAIRIES || student == Clan.PIXIES || student == Clan.TOADS || student == Clan.UNICORNS);
    }

    /**
     * Testing if the Bag.draw(int n) method returns the correct attribute
     */
    @Test
    public void singleDrawTestRemaining(){
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
        b.draw(120);
        boolean result = b.isEmpty();
        assertTrue(result);
    }

    /**
     * Testing if calling draw() or draw(int n) after isEmpty() == true behaves as expected
     */
    @Test
    public void emptyDrawTest(){
        b.draw(130);
        b.isEmpty();
        Clan result = b.draw();
        int[] students = b.draw(5);
        int zero = 0;
        for(int i=0; i<5; i++){
            zero += students[i];
        }
        assertNull(result);
        assertEquals(0, zero);
    }

}

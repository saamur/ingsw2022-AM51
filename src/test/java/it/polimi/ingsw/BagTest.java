package it.polimi.ingsw;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BagTest tests class Bag
 *
 * @link Bag
 */
public class BagTest {
    Bag b;

    @BeforeEach
    public void createBag(){
        b = new Bag();
    }
    /**
     * Testing if the after the draw method is called, in Bag there is the correct number of students.
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
     * Testing if the after the draw method is called, the correct number of students is returned
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
     * Testing if the Bag.draw(int n) method returns the correct attribute.
     * The variable students is expected to be equal to one of the values of Card.
     */
    @Test
    public void singleDrawTestReturn(){
        Clan student = b.draw();
        assertTrue(student == Clan.DRAGONS || student == Clan.FAIRIES || student == Clan.PIXIES || student == Clan.TOADS || student == Clan.UNICORNS);
    }

    /**
     * Testing if the Bag.draw(int n) and removeStudents() method return the correct amount of students
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
        int[] studentsToBeRemoved = {0, 1, 0, 3, 4};
        int[] studentsRemoved = b.removeStudents(studentsToBeRemoved);
        for(int i=0; i<5; i++){
            sum -= studentsRemoved[i];
        }
        assertEquals(111, sum);
    }

    /**
     * Out-of-Bounds test: testing if bag works correctly when the students drawn are more than the ones available
     * Expected result: only the students that are actually available are expected to be removed.
     */
    @Test
    public void drawMoreTest(){
        int[] students = b.draw(130);
        int result = 0;
        for(int i=0; i<5; i++){
            result += students[i];
        }
        assertEquals(120, result);
        assertTrue(b.isEmpty());
    }

    /**
     * Test to check if isEmpty() method returns true if it is called after all of the students are drawn.
     */
    @Test
    public void emptyTest(){
        b.draw(120);
        boolean result = b.isEmpty();
        assertTrue(result);
    }

    /**
     * Testing if calling draw() after (isEmpty() == true) returns false and draw(int n) returns null.
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


    /**
     * Method addStudentsTest() calls method addStudentsTest.
     * After the method is called the bag is expected to have more students than before, accordingly to how many students were added.
     */
    @Test
    public void addStudentsTest(){
        int[] drawStudents = b.draw(6);
        int[] result = b.addStudents(drawStudents);
        for(int i=0; i<5; i++)
            assertEquals(result[i], drawStudents[i]);

        int[] filledBag = {24, 24, 24, 24, 24};
        int[] actualBag = b.getStudents();
        for(int i=0; i<5; i++)
            assertEquals(filledBag[i], actualBag[i]);
    }


}

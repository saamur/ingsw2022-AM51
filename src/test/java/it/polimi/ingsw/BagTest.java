package it.polimi.ingsw;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.channels.Pipe;
import java.util.EnumMap;
import java.util.Map;

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
        Map<Clan, Integer> students = b.getStudents();
        int sum = students.values().stream().mapToInt(a -> a).sum();
        assertEquals(116, sum);
    }

    /**
     * Testing if the after the draw method is called, the correct number of students is returned
     */
    @Test
    public void multipleDrawTestReturn(){
        Map<Clan, Integer> drawn = b.draw(4);
        int sum = drawn.values().stream().mapToInt(a -> a).sum();

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
        Map<Clan, Integer> remaining = b.getStudents();
        int sum = remaining.values().stream().mapToInt(a -> a).sum();
        assertEquals(119, sum);  //initialNumberStudents, remaining
        Map<Clan, Integer> studentsToBeRemoved = TestUtil.studentMapCreator(0, 1, 0, 3, 4);
        Map<Clan, Integer> studentsRemoved = b.removeStudents(studentsToBeRemoved);
        int removed = studentsRemoved.values().stream().mapToInt(a -> a).sum();

        assertEquals(111, sum-removed);
    }

    /**
     * Out-of-Bounds test: testing if bag works correctly when the students drawn are more than the ones available
     * Expected result: only the students that are actually available are expected to be removed.
     */
    @Test
    public void drawMoreTest(){
        Map<Clan, Integer> students = b.draw(130);
        int result = students.values().stream().mapToInt(a -> a).sum();
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
        Map<Clan, Integer> students = b.draw(5);
        int zero = students.values().stream().mapToInt(a -> a).sum();
        assertNull(result);
        assertEquals(0, zero);
    }


    /**
     * Method addStudentsTest() calls method addStudentsTest.
     * After the method is called the bag is expected to have more students than before, accordingly to how many students were added.
     */
    @Test
    public void addStudentsTest(){
        Map<Clan, Integer> drawStudents = b.draw(6);
        Map<Clan, Integer> result = b.addStudents(drawStudents);
        for(Clan c : Clan.values())
            assertEquals(result.get(c), drawStudents.get(c));

        Map<Clan, Integer> filledBag = new EnumMap<>(Clan.class);
        for(int i=0; i<Clan.values().length; i++){
            filledBag.put(Clan.values()[i], 24);
        }
        Map<Clan, Integer> actualBag = b.getStudents();
        for(int i=0; i<Clan.values().length; i++)
            assertEquals(filledBag.get(Clan.values()[i]), actualBag.get(Clan.values()[i]));
    }


}

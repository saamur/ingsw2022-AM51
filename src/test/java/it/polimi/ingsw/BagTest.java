package it.polimi.ingsw;



import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Clan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BagTest tests class Bag
 *
 * @see Bag
 */
public class BagTest {
    private Bag b;

    @BeforeEach
    public void createBag(){
        b = new Bag();
    }
    /**
     * Testing if the after the draw method is called, in Bag there is the correct number of students.
     * @param numOfDrawnOutStudents number of drawn students
     * @param numOfRemainingStudents number of remaining students in the bag
     */

    @ParameterizedTest
    @CsvSource(value = {"4, 116", "7, 113"})
    public void multipleDrawTestRemaining(int numOfDrawnOutStudents, int numOfRemainingStudents){
        b.draw(numOfDrawnOutStudents);
        Map<Clan, Integer> students = b.getStudents();
        int sum = students.values().stream().mapToInt(a -> a).sum();
        assertEquals(numOfRemainingStudents, sum);
    }

    /**
     * Testing if the after the draw method is called, the correct number of students is returned
     * @param drawnStudents number of drawn students
     */
    @ParameterizedTest
    @ValueSource(ints = {4, 7, 120})
    public void multipleDrawTestReturn(int drawnStudents){
        Map<Clan, Integer> drawn = b.draw(drawnStudents);
        int sum = drawn.values().stream().mapToInt(a -> a).sum();

        assertEquals(drawnStudents, sum);
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
     * @param studentsToBeRemoved students to remove from the bag
     * @param remainingStudents number of students expected to remain in the bag after calling removeStudents method
     */

    @ParameterizedTest
    @MethodSource("singleDrawArguments")
    public void singleDrawTestRemaining(Map<Clan, Integer> studentsToBeRemoved, int remainingStudents){

        b.draw();
        Map<Clan, Integer> remaining = b.getStudents();
        int sum = remaining.values().stream().mapToInt(a -> a).sum();
        assertEquals(119, sum);  //initialNumberStudents, remaining

        Map<Clan, Integer> studentsRemoved = b.removeStudents(studentsToBeRemoved);
        int removed = studentsRemoved.values().stream().mapToInt(a -> a).sum();

        assertEquals(remainingStudents, sum-removed);

    }

    private static Stream<Arguments> singleDrawArguments(){
        Map<Clan, Integer> studentsToBeRemoved1 = TestUtil.studentMapCreator(0, 1, 0, 3, 4);
        int remainingStudents1 = 111;

        Map<Clan, Integer> studentsToBeRemoved2 = TestUtil.studentMapCreator(5, 1, 10, 0, 4);
        int remainingStudents2 = 99;

        return Stream.of(
                Arguments.of(studentsToBeRemoved1, remainingStudents1),
                Arguments.of(studentsToBeRemoved2, remainingStudents2)
        );

    }


    /**
     * test that if you try to draw more students than the students currently in the bag, 120 students will be drawn
     * @param drawnStudents number of drawn students
     */
    @ParameterizedTest
    @ValueSource(ints = {130, 2500, 5000})
    public void drawMoreTest(int drawnStudents){
        Map<Clan, Integer> students = b.draw(drawnStudents);
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
     * @param drawnStudents1 number of drawn students to obtain an empty bag
     * @param drawnStudents2 number of drawn students
     */
    @ParameterizedTest
    @CsvSource(value ={"130, 5", "120, 1"})
    public void emptyDrawTest(int drawnStudents1, int drawnStudents2){
        b.draw(drawnStudents1);
        b.isEmpty();
        Clan result = b.draw();
        Map<Clan, Integer> students = b.draw(drawnStudents2);
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

package it.polimi.ingsw.player;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.TestUtil;
import it.polimi.ingsw.model.player.Hall;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HallTest tests the class Hall
 */
public class HallTest {
    /**
     * test if the addStudents method add the students in the Hall
     * @param addingStudents students to add to the hall
     */

    @ParameterizedTest
    @MethodSource("addStudentsArguments")
    public void testAddStudents(Map<Clan, Integer> addingStudents) {
        Hall hall = new Hall(TestUtil.studentMapCreator(0, 0, 0, 0, 0));
        Map<Clan, Integer> addedStudents = hall.addStudents(addingStudents);
        Map<Clan, Integer> students = hall.getStudents();
        for (int i = 0; i < Clan.values().length; i++) {
            assertEquals(addingStudents.get(Clan.values()[i]), addedStudents.get(Clan.values()[i]));
            assertEquals(addingStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }

    }

    private static Stream<Arguments> addStudentsArguments() {
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);

        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(7, 0, 1, 2, 4);

        return Stream.of(
                Arguments.of(addingStudents1),
                Arguments.of(addingStudents2)
        );
    }


    /**
     * test if the removeStudents removes the chosen students from the Hall
     * In the first scenario it is tested in a situation where the number of students
     * that actually are in the Hall is bigger than the one that we want to remove
     * In the second one, it tests if the removeStudents method doesn't remove any student if we try to remove more students
     * than the actual number of students in the hall
     * In this case is expected that in the second cell of removed there will be just 2 students instead of 3, because in the initial
     * situation there were just 2 students
     * Is expected that the number of students in the second cell of students is zero
     * @param addingStudents students to add to the hall and set the initial conditions
     * @param remove students to remove from the hall
     * @param expectedRemoved students expected to be removed after calling removeStudents method
     * @param expectedStudents students expected to be in the hall after calling removeStudents method
     */

    @ParameterizedTest
    @MethodSource("removeStudentsArguments")
    public void testRemoveStudents(Map<Clan, Integer> addingStudents, Map<Clan, Integer> remove, Map<Clan, Integer> expectedRemoved, Map<Clan, Integer> expectedStudents) {
        Hall hall = new Hall(addingStudents);

        Map<Clan, Integer> removed = hall.removeStudents(remove);

        Map<Clan, Integer> students = hall.getStudents();

        for (int i = 0; i < Clan.values().length; i++) {
            assertEquals(expectedRemoved.get(Clan.values()[i]), removed.get(Clan.values()[i]));
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }

    private static Stream<Arguments> removeStudentsArguments() {
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> remove1 = TestUtil.studentMapCreator(0, 1, 2, 2, 1);
        Map<Clan, Integer> expectedRemoved1 = TestUtil.studentMapCreator(0, 1, 2, 2, 1);
        Map<Clan, Integer> expectedStudents1 = TestUtil.studentMapCreator(1, 1, 1, 2, 4);

        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> remove2 = TestUtil.studentMapCreator(1, 3, 10, 5, 1);
        Map<Clan, Integer> expectedRemoved2 = TestUtil.studentMapCreator(1, 2, 3, 4, 1);
        Map<Clan, Integer> expectedStudents2 = TestUtil.studentMapCreator(0, 0, 0, 0, 4);

        return Stream.of(
                Arguments.of(addingStudents1, remove1, expectedRemoved1, expectedStudents1),
                Arguments.of(addingStudents2, remove2, expectedRemoved2, expectedStudents2)
        );

    }


    /**
     * test if the addStudent method adds a chosen student
     * True result is expected
     * @param addingStudents students to add to the hall and set the initial conditions
     * @param expectedStudents students expected to be in the hall after calling the addStudent method
     * @param clan is the clan of belonging of the students on which we are going to test the effect
     */

    @ParameterizedTest
    @MethodSource("addStudentArguments")
    public void testAddStudent(Map<Clan, Integer> addingStudents, Map<Clan, Integer> expectedStudents, Clan clan) {

        Hall hall = new Hall(addingStudents);
        boolean added = hall.addStudent(clan);

        Map<Clan, Integer> students = hall.getStudents();
        assertTrue(added);

        for (int i = 0; i < Clan.values().length; i++) {
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }

    }

    private static Stream<Arguments> addStudentArguments() {
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> expectedStudents1 = TestUtil.studentMapCreator(1, 2, 3, 5, 5);
        Clan clan1 = Clan.DRAGONS;

        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(0, 10, 8, 2, 1);
        Map<Clan, Integer> expectedStudents2 = TestUtil.studentMapCreator(1, 10, 8, 2, 1);
        Clan clan2 = Clan.PIXIES;

        return Stream.of(
                Arguments.of(addingStudents1, expectedStudents1, clan1),
                Arguments.of(addingStudents2, expectedStudents2, clan2)
        );
    }

    /**
     * test if the removeStudent method removes a chosen student.
     * In the first case it is remove a student that actually is in the Hall
     * True result is expected
     * In the second case it is tested that the method doesn't remove a chosen student if he is not in the Hall
     * A false result is expected
     * It is expected an equal distribution of students respect to the initial one
     * @param addingStudents students to add to the hall and set the initial conditions
     * @param expectedStudents students expected to be in the hall after calling the addStudent method
     * @param clan is the clan of belonging of the students on which we are going to test the effect
     * @param expectedRemoved outcome of an attempt to remove a student from the hall
     */

    @ParameterizedTest
    @MethodSource("removeStudentArguments")
    public void testRemoveStudent(Map<Clan, Integer> addingStudents, Map<Clan, Integer> expectedStudents, Clan clan, boolean expectedRemoved) {
        Hall hall = new Hall(addingStudents);
        boolean removed = hall.removeStudent(clan);

        Map<Clan, Integer> students = hall.getStudents();
        assertEquals(expectedRemoved, removed);
        for (int i = 0; i < Clan.values().length; i++) {
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }

    private static Stream<Arguments> removeStudentArguments() {
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> expectedStudents1 = TestUtil.studentMapCreator(1, 2, 3, 3, 5);
        Clan clan1 = Clan.DRAGONS;
        boolean removed1 = true;

        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(0, 10, 2, 2, 3);
        Map<Clan, Integer> expectedStudents2 = TestUtil.studentMapCreator(0, 10, 2, 2, 3);
        Clan clan2 = Clan.PIXIES;
        boolean removed2 = false;

        return Stream.of(
                Arguments.of(addingStudents1, expectedStudents1, clan1, removed1),
                Arguments.of(addingStudents2, expectedStudents2, clan2, removed2)
        );

    }

}



package it.polimi.ingsw.clouds;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.TestUtil;
import it.polimi.ingsw.clouds.Cloud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class CloudTest {
    Cloud cloud;

    @BeforeEach
    public void initialization(){
        cloud = new Cloud();
    }

    /**
     * testFill tests if an empty Cloud is filled with a given array of students
     * Is expected that the cloud contains all the students given
     */

    @ParameterizedTest
    @MethodSource("fillArguments")

    public void testFill(Map<Clan, Integer> addingStudents){

        cloud.fill(addingStudents);
        Map<Clan, Integer> students=cloud.getStudents();
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(addingStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
        assertFalse(cloud.isPicked());
    }

    private static Stream<Arguments> fillArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 5, 4, 3, 1);
        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(7, 1, 0, 4, 0);

        return Stream.of(
                Arguments.of(addingStudents1),
                Arguments.of(addingStudents2)
        );
    }

    /**
     * testPick method tests if the Cloud is an unpicked cloud is picked in normal condition
     * Is expected that the cloud is empty after the call of the pick method and the cloud is marked as picked
     */

    @ParameterizedTest
    @MethodSource("pickArguments")
    public void testPick(Map<Clan, Integer> addingStudents){

        cloud.fill(addingStudents);
        Map<Clan, Integer> pickedStudents = cloud.pick();
        assertTrue(cloud.isPicked());

        for(Clan c : Clan.values()){
            assertEquals(addingStudents.get(c), pickedStudents.get(c));
        }
        Map<Clan, Integer> students = cloud.getStudents();

        for(Clan c : Clan.values()){
            assertEquals(0, students.get(c));
        }
    }

    private static Stream<Arguments> pickArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(7, 1, 0, 2, 3);

        return Stream.of(
                Arguments.of(addingStudents1),
                Arguments.of(addingStudents2)
        );
    }




    /**
     * test if the isEmpty method check correctly that the cloud is empty
     * A true return is expected
     */
    @Test
    public void testIsEmpty(){
        boolean empty = cloud.isEmpty();
        assertTrue(empty);
    }

    /**
     * test if the isEmpty method checks that the cloud is not empty after that the cloud is filled with the fill method
     * A false return is expected
     */

    @ParameterizedTest
    @MethodSource("isEmptyFillArguments")
    public void testIsEmptyFill(Map<Clan, Integer> addingStudents){

        cloud.fill(addingStudents);
        boolean empty = cloud.isEmpty();
        assertFalse(empty);

    }

    private static Stream<Arguments> isEmptyFillArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(4, 0, 0, 0, 0);

        return Stream.of(
                Arguments.of(addingStudents1),
                Arguments.of(addingStudents2)
        );
    }

    /**
     * test if the isEmpty method checks correctly that the cloud is empty after calling the fill and the pick methods
     * A true result is expected
     */
    @ParameterizedTest
    @MethodSource("fillPickIsEmptyArguments")
    public void testFillPickIsEmpty(Map<Clan, Integer> addingStudents){

        cloud.fill(addingStudents);
        cloud.pick();
        boolean empty = cloud.isEmpty();
        assertTrue(empty);

    }

    private static Stream<Arguments> fillPickIsEmptyArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(7, 1, 0, 0, 0);

        return Stream.of(
                Arguments.of(addingStudents1),
                Arguments.of(addingStudents2)
        );
    }


    /**
     * test if the method pick return null if the could was already picked, it has to be impossible to choose the same
     * cloud twice
     */

    @ParameterizedTest
    @MethodSource("morePickArguments")
    public void testMorePick(Map<Clan, Integer> addingStudents){

        cloud.fill(addingStudents);
        cloud.pick();
        Map<Clan, Integer> pickedStudents = cloud.pick();
        assertTrue(cloud.isPicked());
        assertNull(pickedStudents);

    }

    private static Stream<Arguments> morePickArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 0, 5);
        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(1, 7, 0, 0, 1);

        return Stream.of(
                Arguments.of(addingStudents1),
                Arguments.of(addingStudents2)
        );
    }


}

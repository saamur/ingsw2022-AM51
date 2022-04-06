package it.polimi.ingsw.clouds;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.clouds.Cloud;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

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
    @Test
    public void testFill(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 5);
        addingStudents.put(Clan.TOADS, 4);
        addingStudents.put(Clan.DRAGONS, 3);
        addingStudents.put(Clan.FAIRIES, 1);

        cloud.fill(addingStudents);
        Map<Clan, Integer> students=cloud.getStudents();
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(addingStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
        assertFalse(cloud.isPicked());
    }

    /**
     * testPick method tests if the Cloud is an unpicked cloud is picked in normal condition
     * Is expected that the cloud is empty after the call of the pick method and the cloud is marked as picked
     */

    @Test
    public void testPick(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 5);

        cloud.fill(addingStudents);
        Map<Clan, Integer> pickedStudents = cloud.pick();
        assertTrue(cloud.isPicked());

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(addingStudents.get(Clan.values()[i]), Clan.values()[i]);
        }
        Map<Clan, Integer> students = cloud.getStudents();

        for(int j=0; j<Clan.values().length; j++){
            assertEquals(0, students.get(Clan.values()[j]));
        }
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

    @Test
    public void testIsEmptyFill(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 5);

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
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 5);

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
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 0);
        addingStudents.put(Clan.FAIRIES, 5);

        cloud.fill(addingStudents);
        cloud.pick();
        Map<Clan, Integer> pickedStudents = cloud.pick();
        assertTrue(cloud.isPicked());
        assertNull(pickedStudents);
    }


}

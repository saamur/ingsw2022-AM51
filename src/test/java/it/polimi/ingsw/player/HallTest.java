package it.polimi.ingsw.player;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.player.Hall;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HallTest {
    /**
     * test if the addStudents method add the students in the Hall
     */

    @Test
    public void testAddStudents(){

        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 5);

        Hall hall = new Hall(new EnumMap<>(Clan.class));
        Map<Clan, Integer> addedStudents = hall.addStudents(addingStudents);
        Map<Clan, Integer> students = hall.getStudents();
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(addingStudents.get(Clan.values()[i]), addedStudents.get(Clan.values()[i]));
            assertEquals(addingStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }

    /**
     * test if the removeStudents removes the chosen students from the Hall in a situation where the number of students
     * that actually are in the Hall is bigger than the one that we want to remove
     */

    @Test
    public void testRemoveStudents(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 5);

        Hall hall = new Hall(addingStudents);

        Map<Clan, Integer> remove = new EnumMap<>(Clan.class);
        remove.put(Clan.PIXIES, 0);
        remove.put(Clan.UNICORNS, 1);
        remove.put(Clan.TOADS, 2);
        remove.put(Clan.DRAGONS, 2);
        remove.put(Clan.FAIRIES, 1);

        Map<Clan, Integer> removed = hall.removeStudents(remove);
        Map<Clan, Integer> students = hall.getStudents();
        Map<Clan, Integer> expectedStudents = new EnumMap<>(Clan.class);

        expectedStudents.put(Clan.PIXIES, 1);
        expectedStudents.put(Clan.UNICORNS, 1);
        expectedStudents.put(Clan.TOADS, 1);
        expectedStudents.put(Clan.DRAGONS, 2);
        expectedStudents.put(Clan.FAIRIES, 4);

        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(remove.get(Clan.values()[i]), removed.get(Clan.values()[i]));
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }

    /**
     * test if the removeStudents method doesn't remove any student if we try to remove more students
     * than the actual number of students in the hall
     * Is expected that in the second cell of removed there will be just 2 students instead of 3, because in the initial
     * situation there were just 2 students
     * Is expected that the number of students in the second cell of students is zero
     */

    @Test
    public void removeTooManyStudents(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 5);

        Hall hall = new Hall(addingStudents);

        Map<Clan, Integer> remove = new EnumMap<>(Clan.class);
        remove.put(Clan.PIXIES, 1);
        remove.put(Clan.UNICORNS, 3);
        remove.put(Clan.TOADS, 10);
        remove.put(Clan.DRAGONS, 5);
        remove.put(Clan.FAIRIES, 1);

        Map<Clan, Integer> removed = hall.removeStudents(remove);

        Map<Clan, Integer> expectedRemoved = new EnumMap<>(Clan.class);
        expectedRemoved.put(Clan.PIXIES, 1);
        expectedRemoved.put(Clan.UNICORNS, 2);
        expectedRemoved.put(Clan.TOADS, 3);
        expectedRemoved.put(Clan.DRAGONS, 4);
        expectedRemoved.put(Clan.FAIRIES, 1);

        Map<Clan, Integer> students = hall.getStudents();

        Map<Clan, Integer> expectedStudents = new EnumMap<>(Clan.class);

        expectedStudents.put(Clan.PIXIES, 0);
        expectedStudents.put(Clan.UNICORNS, 0);
        expectedStudents.put(Clan.TOADS, 0);
        expectedStudents.put(Clan.DRAGONS, 0);
        expectedStudents.put(Clan.FAIRIES, 4);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedRemoved.get(Clan.values()[i]), removed.get(Clan.values()[i]) );
            assertEquals(expectedStudents.get(Clan.values()[i]) , students.get(Clan.values()[i]) );
        }
    }

    /**
     * test if the addStudent method adds a chosen student
     * True result is expected
     */

    @Test
    public void testAddStudent(){

        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 5);

        Hall hall = new Hall(addingStudents);
        boolean added = hall.addStudent(Clan.DRAGONS);

        Map<Clan, Integer> expectedStudents = new EnumMap<>(Clan.class);
        expectedStudents.put(Clan.PIXIES, 1);
        expectedStudents.put(Clan.UNICORNS, 2);
        expectedStudents.put(Clan.TOADS, 3);
        expectedStudents.put(Clan.DRAGONS, 5);
        expectedStudents.put(Clan.FAIRIES, 5);

        Map<Clan, Integer> students = hall.getStudents();
        assertTrue(added);
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }

    }

    /**
     * test if the removeStudent method removes a chosen student if he actually is in the Hall
     * True result is expected
     */

    @Test
    public void testRemoveStudent(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);

        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 5);

        Hall hall = new Hall(addingStudents);
        boolean removed = hall.removeStudent(Clan.DRAGONS);

        Map<Clan, Integer> expectedStudents = new EnumMap<>(Clan.class);
        expectedStudents.put(Clan.PIXIES, 1);
        expectedStudents.put(Clan.UNICORNS, 2);
        expectedStudents.put(Clan.TOADS, 3);
        expectedStudents.put(Clan.DRAGONS, 3);
        expectedStudents.put(Clan.FAIRIES, 5);

        Map<Clan, Integer> students = hall.getStudents();
        assertTrue(removed);
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }

    }

    /**
     * test if the removeStudent method doesn't remove a chosen student if he is not in the Hall
     * A false result is expected
     * Is expected an equal distribution of students respect to the initial one
     */

    @Test
    public void testRemoveNonExistingStudent(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);

        addingStudents.put(Clan.PIXIES, 2);
        addingStudents.put(Clan.UNICORNS, 10);
        addingStudents.put(Clan.TOADS, 2);
        addingStudents.put(Clan.DRAGONS, 0);
        addingStudents.put(Clan.FAIRIES, 3);

        Hall hall = new Hall(addingStudents);
        boolean removed = hall.removeStudent(Clan.DRAGONS);
        assertFalse(removed);
        Map<Clan, Integer> students = hall.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(addingStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }



}

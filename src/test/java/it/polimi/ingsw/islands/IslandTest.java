package it.polimi.ingsw.islands;


import it.polimi.ingsw.Clan;
import it.polimi.ingsw.islands.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IslandTest {
    Island island;

    @BeforeEach
    public void initialization(){
        island = new Island();
    }

    /**
     * test to check if addStudents add the students in an empty Island
     * Is expected that all the students are added in the Island
     */
    @Test
    public void testAddStudents() {
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 0);

        Map<Clan, Integer> addedStudents = island.addStudents(addingStudents);
        for (int i = 0; i < Clan.values().length; i++) {
            assertEquals(addingStudents.get(Clan.values()[i]), addedStudents.get(Clan.values()[i]));
        }
    }

    /**
     * test if removeStudents remove the students from the Island in normal condition, so if the chosen students
     * actually are in the Island
     * Is expected that all the students are removed from the Island
     */
    @Test
    public void testRemoveStudent() {
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 4);
        addingStudents.put(Clan.DRAGONS, 5);
        addingStudents.put(Clan.FAIRIES, 6);

        island.addStudents(addingStudents);

        Map<Clan, Integer> remove = new EnumMap<>(Clan.class);
        remove.put(Clan.PIXIES, 1);
        remove.put(Clan.UNICORNS, 1);
        remove.put(Clan.TOADS, 1);
        remove.put(Clan.DRAGONS, 0);
        remove.put(Clan.FAIRIES, 0);

        Map<Clan, Integer> removedStudents = island.removeStudents(remove);

        for (int i = 0; i < Clan.values().length; i++) {
            assertEquals(remove.get(Clan.values()[i]), removedStudents.get(Clan.values()[i]));
        }

        Map<Clan, Integer> students = island.getStudents();

        Map<Clan, Integer> expectedStudents = new EnumMap<>(Clan.class);
        expectedStudents.put(Clan.PIXIES, 2);
        expectedStudents.put(Clan.UNICORNS, 1);
        expectedStudents.put(Clan.TOADS, 3);
        expectedStudents.put(Clan.DRAGONS, 5);
        expectedStudents.put(Clan.FAIRIES, 6);

        for (int j = 0; j < Clan.values().length; j++) {
            assertEquals(expectedStudents.get(Clan.values()[j]), students.get(Clan.values()[j]));
        }
    }

    /**
     * Out-of-Bounds test: removeTooMuchStudents method tests the removal of the students from an Island that doesn't
     * have that number of students.
     * Is expected that in cases where the number of students to be removed is greater than the number of
     * those present, only the students actually present on the Island are removed
     */
    @Test
    public void removeTooMuchStudents() {
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 4);
        addingStudents.put(Clan.DRAGONS, 5);
        addingStudents.put(Clan.FAIRIES, 6);

        island.addStudents(addingStudents);

        Map<Clan, Integer> remove = new EnumMap<>(Clan.class);
        remove.put(Clan.PIXIES, 4);
        remove.put(Clan.UNICORNS, 5);
        remove.put(Clan.TOADS, 6);
        remove.put(Clan.DRAGONS, 7);
        remove.put(Clan.FAIRIES, 8);

        Map<Clan, Integer> removedStudents = island.removeStudents(remove);

        for (int j = 0; j < Clan.values().length; j++) {
            assertEquals(addingStudents.get(Clan.values()[j]), removedStudents.get(Clan.values()[j]));
        }

        Map<Clan, Integer> students = island.getStudents();

        for (int j = 0; j < Clan.values().length; j++) {
            assertEquals(0, students.get(Clan.values()[j]));
        }
    }

    /**
     * testAddStudent method tests if the AddStudent method add a student of a particular chosen clan on the Island
     */

    @Test
    public void testAddStudent() {
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 4);
        addingStudents.put(Clan.DRAGONS, 5);
        addingStudents.put(Clan.FAIRIES, 0);

        island.addStudents(addingStudents);
        island.addStudent(Clan.DRAGONS);

        Map<Clan, Integer> students = island.getStudents();
        assertEquals(6, students.get(Clan.values()[3]));
    }

    /**
     * test that the addProhibitionCard method add a prohibition card on an Island
     */

    @Test
    public void testAddProhibitionCard(){
        island.addProhibitionCard();
        int numProhibitionCards = island.getNumProhibitionCards();
        assertEquals(1, numProhibitionCards);
    }

    /**
     * test if the method removeProhibitionCard remove a prohibition card from the Island if the card is present
     */
    @Test
    public void testRemoveProhibitionCard(){
        island.addProhibitionCard();
        island.removeProhibitionCard();
        int numProhibitionCards = island.getNumProhibitionCards();
        assertEquals(0, numProhibitionCards);
    }

    /**
     * test that if the method removeProhibitionCard doesn't do anything if if there are no Prohibition Card on
     * the Island
     */
    @Test
    public void testRemoveTooMuchProhibition(){
        island.removeProhibitionCard();
        int numProhibitionCards = island.getNumProhibitionCards();
        assertEquals(0, numProhibitionCards);
    }

    /**
     * test if the merge method unifies the islands by adding the students present on each one,
     * just as the unified island will have the sum of the number of prohibition cards present on the individual islands
     */
    @Test
    public void testMerge(){
        Island firstIsland = new Island();
        Island secondIsland = new Island();

        Map<Clan, Integer> addingStudentsFirstIsland = new EnumMap<>(Clan.class);
        addingStudentsFirstIsland.put(Clan.PIXIES, 1);
        addingStudentsFirstIsland.put(Clan.UNICORNS, 2);
        addingStudentsFirstIsland.put(Clan.TOADS, 3);
        addingStudentsFirstIsland.put(Clan.DRAGONS, 4);
        addingStudentsFirstIsland.put(Clan.FAIRIES, 5);


        Map<Clan, Integer> addingStudentsSecondIsland = new EnumMap<>(Clan.class);
        addingStudentsSecondIsland.put(Clan.PIXIES, 5);
        addingStudentsSecondIsland.put(Clan.UNICORNS, 4);
        addingStudentsSecondIsland.put(Clan.TOADS, 3);
        addingStudentsSecondIsland.put(Clan.DRAGONS, 2);
        addingStudentsSecondIsland.put(Clan.FAIRIES, 1);

        firstIsland.addStudents(addingStudentsFirstIsland);
        secondIsland.addStudents(addingStudentsSecondIsland);
        firstIsland.addProhibitionCard();
        firstIsland.merge(secondIsland);
        Map<Clan, Integer> students = firstIsland.getStudents();

        Map<Clan, Integer> studentsExpected = new EnumMap<>(Clan.class);
        studentsExpected.put(Clan.PIXIES, 6);
        studentsExpected.put(Clan.UNICORNS, 6);
        studentsExpected.put(Clan.TOADS, 6);
        studentsExpected.put(Clan.DRAGONS, 6);
        studentsExpected.put(Clan.FAIRIES, 6);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(studentsExpected.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }

        assertEquals(2, firstIsland.getNumberOfIslands());
        assertEquals(1, firstIsland.getNumProhibitionCards());
    }

}


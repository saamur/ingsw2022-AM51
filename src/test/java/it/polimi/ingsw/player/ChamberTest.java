package it.polimi.ingsw.player;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.player.Chamber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ChamberTest {

    Chamber chamber;

    @BeforeEach
    public void initialization(){
        chamber = new Chamber();
    }

    /**
     * Method testAddStudents tests the adding of students in the Chamber in normal condition. Is expected that all the
     * students are added
     */
    @Test
    public void testAddStudents(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 1);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 4);
        addingStudents.put(Clan.FAIRIES, 5);

        Map<Clan, Integer> addedStudents = chamber.addStudents(addingStudents);
        Map<Clan, Integer> students = chamber.getStudents();
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(addingStudents.get(Clan.values()[i]), addedStudents.get(Clan.values()[i]));
            assertEquals(addingStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }

    /**
     * The testAddMoreStudents tests if the Chamber doesn't add student in the Chamber when it is full
     * The  first cell is expected to have a value of 10, since only 10 students should be added
     */

    @Test
    public void testAddMoreStudents(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 12);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 7);
        addingStudents.put(Clan.DRAGONS, 6);
        addingStudents.put(Clan.FAIRIES, 10);

        Map<Clan, Integer> addedStudents = chamber.addStudents(addingStudents);
        assertEquals(10, addedStudents.get(Clan.values()[0]));
        Map<Clan, Integer> students = chamber.getStudents();
        assertEquals(10, students.get(Clan.values()[0]));
    }


    /**
     * testRemoveStudents method tests the removal of the students from a Chamber that doesn't have that number of
     * students. Is expected that in cases where the number of students to be removed is greater than the number of
     * those present, only the students actually present in the chamber are removed
     */

    @Test
    public void testRemoveStudents(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 12);
        addingStudents.put(Clan.UNICORNS, 2);
        addingStudents.put(Clan.TOADS, 7);
        addingStudents.put(Clan.DRAGONS, 6);
        addingStudents.put(Clan.FAIRIES, 10);

        chamber.addStudents(addingStudents);

        Map<Clan, Integer> removingStudents = new EnumMap<>(Clan.class);
        removingStudents.put(Clan.PIXIES, 8);
        removingStudents.put(Clan.UNICORNS, 3);
        removingStudents.put(Clan.TOADS, 8);
        removingStudents.put(Clan.DRAGONS, 0);
        removingStudents.put(Clan.FAIRIES, 10);

        Map<Clan, Integer> removed = chamber.removeStudents(removingStudents);

        Map<Clan, Integer> expectedStudents = new EnumMap<>(Clan.class);
        expectedStudents.put(Clan.PIXIES, 2);
        expectedStudents.put(Clan.UNICORNS, 0);
        expectedStudents.put(Clan.TOADS, 0);
        expectedStudents.put(Clan.DRAGONS, 6);
        expectedStudents.put(Clan.FAIRIES, 0);

        Map<Clan, Integer> expectedRemoved = new EnumMap<>(Clan.class);
        expectedRemoved.put(Clan.PIXIES, 8);
        expectedRemoved.put(Clan.UNICORNS, 2);
        expectedRemoved.put(Clan.TOADS, 7);
        expectedRemoved.put(Clan.DRAGONS, 0);
        expectedRemoved.put(Clan.FAIRIES, 10);

        Map<Clan, Integer> students = chamber.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedRemoved.get(Clan.values()[i]), removed.get(Clan.values()[i]));
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }

    /**
     * testHasProfessor tests if, in the initial condition, the HasProfessor method return false
     */

    @Test
    public void testHasProfessor(){
        boolean hasTheProfessor = chamber.hasProfessor(Clan.DRAGONS);
        Map<Clan, Boolean> professors = chamber.getProfessors();
        assertFalse(hasTheProfessor);
        for(int i=0; i<Clan.values().length; i++){
            assertFalse(professors.get(Clan.values()[i]));
        }
    }


    /**
     * testUpdateCoins method tests if the calculation for the attribution of coins is correct after
     * the initial addition of students. Is expected a coin to be given after each addiction of 3 students.
     */

    @Test
    public void testUpdateCoins(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 12);
        addingStudents.put(Clan.UNICORNS, 1);
        addingStudents.put(Clan.TOADS, 3);
        addingStudents.put(Clan.DRAGONS, 5);
        addingStudents.put(Clan.FAIRIES, 9);

        chamber.addStudents(addingStudents);
        Map<Clan, Integer> coinsGiven = chamber.getCoinsGiven();

        Map<Clan, Integer> expectedCoinsGiven = new EnumMap<>(Clan.class);
        expectedCoinsGiven.put(Clan.PIXIES, 3);
        expectedCoinsGiven.put(Clan.UNICORNS, 0);
        expectedCoinsGiven.put(Clan.TOADS, 1);
        expectedCoinsGiven.put(Clan.DRAGONS, 1);
        expectedCoinsGiven.put(Clan.FAIRIES, 3);

        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(expectedCoinsGiven.get(Clan.values()[i]), coinsGiven.get(Clan.values()[i]));

        }
        assertEquals(9, chamber.getCoins());
    }

    /**
     * testUpdateCoinsDouble method tests that a coin is not given twice, after a student is removed and re-added from
     * a particular givenCoin position
     * Is expected that in the first cell of coinsGiven, even when 3 students are re-added, the CoinsGiven remains 1
     */

    @Test
    public void testUpdateCoinsTwiceCaseOne(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 4);
        addingStudents.put(Clan.TOADS, 6);
        addingStudents.put(Clan.DRAGONS, 9);
        addingStudents.put(Clan.FAIRIES, 10);

        chamber.addStudents(addingStudents);

        Map<Clan, Integer> remove = new EnumMap<>(Clan.class);
        remove.put(Clan.PIXIES, 3);
        remove.put(Clan.UNICORNS, 0);
        remove.put(Clan.TOADS, 0);
        remove.put(Clan.DRAGONS, 0);
        remove.put(Clan.FAIRIES, 0);

        chamber.removeStudents(remove);

        Map<Clan, Integer> newAddingStudents = new EnumMap<>(Clan.class);
        newAddingStudents.put(Clan.PIXIES, 3);
        newAddingStudents.put(Clan.UNICORNS, 0);
        newAddingStudents.put(Clan.TOADS, 0);
        newAddingStudents.put(Clan.DRAGONS, 0);
        newAddingStudents.put(Clan.FAIRIES, 0);

        chamber.addStudents(newAddingStudents);

        Map<Clan, Integer> expectedCoinsGiven = new EnumMap<>(Clan.class);
        expectedCoinsGiven.put(Clan.PIXIES, 1);
        expectedCoinsGiven.put(Clan.UNICORNS, 1);
        expectedCoinsGiven.put(Clan.TOADS, 2);
        expectedCoinsGiven.put(Clan.DRAGONS, 3);
        expectedCoinsGiven.put(Clan.FAIRIES, 3);

        Map<Clan, Integer> result = chamber.getCoinsGiven();

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedCoinsGiven.get(Clan.values()[i]), result.get(Clan.values()[i]));
        }

        assertEquals(11, chamber.getCoins());
    }
    /**
     * testUpdateCoinsDouble method tests that a coin is not given twice, after a student is removed and re-added from
     * a particular givenCoin position
     * Is expected that the in second cell of coinsGiven only one coin is added compared to the previous situation, in
     * fact just one new givenCoin position is covered
     */

    @Test
    public void testUpdateCoinsTwiceCaseTwo(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 4);
        addingStudents.put(Clan.TOADS, 6);
        addingStudents.put(Clan.DRAGONS, 9);
        addingStudents.put(Clan.FAIRIES, 10);

        chamber.addStudents(addingStudents);

        Map<Clan, Integer> remove = new EnumMap<>(Clan.class);
        remove.put(Clan.PIXIES, 0);
        remove.put(Clan.UNICORNS, 2);
        remove.put(Clan.TOADS, 0);
        remove.put(Clan.DRAGONS, 0);
        remove.put(Clan.FAIRIES, 0);

        chamber.removeStudents(remove);

        Map<Clan, Integer> newAddingStudents = new EnumMap<>(Clan.class);
        newAddingStudents.put(Clan.PIXIES, 0);
        newAddingStudents.put(Clan.UNICORNS, 4);
        newAddingStudents.put(Clan.TOADS, 0);
        newAddingStudents.put(Clan.DRAGONS, 0);
        newAddingStudents.put(Clan.FAIRIES, 0);

        chamber.addStudents(newAddingStudents);

        Map<Clan, Integer> expectedCoinsGiven = new EnumMap<>(Clan.class);
        expectedCoinsGiven.put(Clan.PIXIES, 1);
        expectedCoinsGiven.put(Clan.UNICORNS, 2);
        expectedCoinsGiven.put(Clan.TOADS, 2);
        expectedCoinsGiven.put(Clan.DRAGONS, 3);
        expectedCoinsGiven.put(Clan.FAIRIES, 3);

        Map<Clan, Integer> result = chamber.getCoinsGiven();

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedCoinsGiven.get(Clan.values()[i]), result.get(Clan.values()[i]));
        }
        assertEquals(12, chamber.getCoins());
    }

    /**
     * testUpdateCoinsDouble method tests that a coin is not given twice, after a student is removed and re-added from
     * a particular givenCoin position
     * Is expected that in the fourth cell of coinsGiven the coins remain 3, even if not all the three givenCoin position
     * are covered
     */

    @Test
    public void testUpdateCoinsTwiceCaseThree(){
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 4);
        addingStudents.put(Clan.TOADS, 6);
        addingStudents.put(Clan.DRAGONS, 9);
        addingStudents.put(Clan.FAIRIES, 10);

        chamber.addStudents(addingStudents);

        Map<Clan, Integer> remove = new EnumMap<>(Clan.class);
        remove.put(Clan.PIXIES, 0);
        remove.put(Clan.UNICORNS, 0);
        remove.put(Clan.TOADS, 0);
        remove.put(Clan.DRAGONS, 5);
        remove.put(Clan.FAIRIES, 0);

        chamber.removeStudents(remove);

        Map<Clan, Integer> newAddingStudents = new EnumMap<>(Clan.class);
        newAddingStudents.put(Clan.PIXIES, 0);
        newAddingStudents.put(Clan.UNICORNS, 0);
        newAddingStudents.put(Clan.TOADS, 0);
        newAddingStudents.put(Clan.DRAGONS, 2);
        newAddingStudents.put(Clan.FAIRIES, 0);

        chamber.addStudents(newAddingStudents);

        Map<Clan, Integer> expectedCoinsGiven = new EnumMap<>(Clan.class);
        expectedCoinsGiven.put(Clan.PIXIES, 1);
        expectedCoinsGiven.put(Clan.UNICORNS, 1);
        expectedCoinsGiven.put(Clan.TOADS, 2);
        expectedCoinsGiven.put(Clan.DRAGONS, 3);
        expectedCoinsGiven.put(Clan.FAIRIES, 3);

        Map<Clan, Integer> result = chamber.getCoinsGiven();

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedCoinsGiven.get(Clan.values()[i]), result.get(Clan.values()[i]));
        }

        assertEquals(11, chamber.getCoins());
    }


    /**
     * testAddStudent method tests if, in an unfilled chamber, the AddStudent method add a student of a particular
     * chosen clan
     */

    @Test
    public void testAddStudent(){
        boolean added = chamber.addStudent(Clan.DRAGONS);
        assertTrue(added);
        Map<Clan, Integer> students = chamber.getStudents();

        Map<Clan, Integer> expectedStudents = new EnumMap<>(Clan.class);
        expectedStudents.put(Clan.PIXIES, 0);
        expectedStudents.put(Clan.UNICORNS, 0);
        expectedStudents.put(Clan.TOADS, 0);
        expectedStudents.put(Clan.DRAGONS, 1);
        expectedStudents.put(Clan.FAIRIES, 0);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }

    /**
     * test if the addStudents method doesn't add the student of a particular clan if in the Chamber there are already
     * 10 students of that clan
     * Is expected a false return and an unmodified situation of the students
     */

    @Test
    public void testAddInAFullChamber(){

        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 0);
        addingStudents.put(Clan.UNICORNS, 0);
        addingStudents.put(Clan.TOADS, 0);
        addingStudents.put(Clan.DRAGONS, 10);
        addingStudents.put(Clan.FAIRIES, 0);

        chamber.addStudents(addingStudents);

        boolean added = chamber.addStudent(Clan.DRAGONS);
        assertFalse(added);
        Map<Clan, Integer> students = chamber.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(addingStudents.get(Clan.values()[i]), students.get(Clan.values()[i]));
        }
    }




}

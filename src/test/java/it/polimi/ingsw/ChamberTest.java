package it.polimi.ingsw;

import it.polimi.ingsw.player.Chamber;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ChamberTest {

    /**
     * Method testAddStudents tests the adding of students in the Chamber in normal condition. Is expected that all the
     * students are added
     */
    @Test
    public void testAddStudents(){
        Chamber chamber = new Chamber();
        int[] addingStudents = {1, 2, 3, 4, 5};
        int[] addedStudents = chamber.addStudents(addingStudents);
        int[] students = chamber.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(addingStudents[i], addedStudents[i]);
            assertEquals(addingStudents[i], students[i]);
        }
    }

    /**
     * The testAddMoreStudents tests if the Chamber doesn't add student in the Chamber when it is full
     * The  first cell is expected to have a value of 10, since only 10 students should be added
     */

    @Test
    public void testAddMoreStudents(){
        Chamber chamber = new Chamber();
        int[] addingStudents = {12, 2, 7, 6, 10};
        int[] addedStudents = chamber.addStudents(addingStudents);
        assertEquals(10, addedStudents[0]);
        int[] students = chamber.getStudents();
        assertEquals(10, students[0]);
    }


    /**
     * testRemoveStudents method tests the removal of the students from a Chamber that doesn't have that number of
     * students. Is expected that in cases where the number of students to be removed is greater than the number of
     * those present, only the students actually present in the chamber are removed
     */

    @Test
    public void testRemoveStudents(){
        Chamber chamber = new Chamber();
        int[] addingStudents = {12, 2, 7, 6, 10};
        chamber.addStudents(addingStudents);
        int[] removingStudents = {8, 3, 8, 0, 10};
        int[] removed = chamber.removeStudents(removingStudents);
        int[] expectedStudents = {2, 0, 0, 6, 0};
        int[] expectedRemoved = {8, 2, 7, 0, 10};
        int[] students = chamber.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedRemoved[i], removed[i]);
            assertEquals(expectedStudents[i], students[i]);
        }
    }

    /**
     * testHasProfessor tests if, in the initial condition, the HasProfessor method return false
     */

    @Test
    public void testHasProfessor(){
        Chamber chamber = new Chamber();
        boolean hasTheProfessor = chamber.hasProfessor(Clan.DRAGONS);
        boolean[] professors = chamber.getProfessors();
        assertFalse(hasTheProfessor);
        for(int i=0; i<Clan.values().length; i++){
            assertFalse(professors[i]);
        }
    }


    /**
     * testUpdateCoins method tests if the calculation for the attribution of coins is correct after
     * the initial addition of students. Is expected a coin to be given after each addiction of 3 students.
     */

    @Test
    public void testUpdateCoins(){
        Chamber chamber = new Chamber();
        int[] addingStudents = {12, 1, 3, 5, 9};
        chamber.addStudents(addingStudents);
        int[] coinsGiven = chamber.getCoinsGiven();
        int[] expectedCoinsGiven = {3, 0, 1, 1, 3};
        for(int i = 0; i< Clan.values().length; i++){
            assertEquals(expectedCoinsGiven[i], coinsGiven[i]);

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
        Chamber chamber = new Chamber();
        int[] addingStudents = {3, 4, 6, 9, 10};
        chamber.addStudents(addingStudents);
        int[] remove = {3, 0, 0, 0, 0};
        chamber.removeStudents(remove);
        int[] newAddingStudents = {3, 0, 0, 0, 0};
        chamber.addStudents(newAddingStudents);
        int[] expectedCoinsGiven = {1, 1, 2, 3, 3};
        int[] result = chamber.getCoinsGiven();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedCoinsGiven[i], result[i]);
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
        Chamber chamber = new Chamber();
        int[] addingStudents = {3, 4, 6, 9, 10};
        chamber.addStudents(addingStudents);
        int[] remove = {0, 2, 0, 0, 0};
        chamber.removeStudents(remove);
        int[] newAddingStudents = {0, 4, 0, 0, 0};
        chamber.addStudents(newAddingStudents);
        int[] expectedCoinsGiven = {1, 2, 2, 3, 3};
        int[] result = chamber.getCoinsGiven();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedCoinsGiven[i], result[i]);
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
        Chamber chamber = new Chamber();
        int[] addingStudents = {3, 4, 6, 9, 10};
        chamber.addStudents(addingStudents);
        int[] remove = {0, 0, 0, 5, 0};
        chamber.removeStudents(remove);
        int[] newAddingStudents = {0, 0, 0, 2, 0};
        chamber.addStudents(newAddingStudents);
        int[] expectedCoinsGiven = {1, 1, 2, 3, 3};
        int[] result = chamber.getCoinsGiven();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedCoinsGiven[i], result[i]);
        }
        assertEquals(11, chamber.getCoins());
    }


    /**
     * testAddStudent method tests if, in an unfilled chamber, the AddStudent method add a student of a particular
     * chosen clan
     */

    @Test
    public void testAddStudent(){
        Chamber chamber = new Chamber();
        boolean added = chamber.addStudent(Clan.DRAGONS);
        assertTrue(added);
        int[] students = chamber.getStudents();
        int[] expectedStudents = {0, 0, 0, 1, 0};
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedStudents[i], students[i]);
        }
    }

    /**
     * test if the addStudents method doesn't add the student of a particular clan if in the Chamber there are already
     * 10 students of that clan
     * Is expected a false return and an unmodified situation of the students
     */

    @Test
    public void testAddInAFullChamber(){
        Chamber c = new Chamber();
        int[] std = {0, 0, 0, 10, 0};
        c.addStudents(std);
        boolean added = c.addStudent(Clan.DRAGONS);
        assertFalse(added);
        int[] students = c.getStudents();
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(std[i], students[i]);
        }
    }




}

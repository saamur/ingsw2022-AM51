package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterCardCreator;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.charactercards.StudentMoverCharacterCard;
import it.polimi.ingsw.model.player.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.Clan.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * StudentMoverCharacterCardTest tests the StudentMoverCharacterCards
 * @see StudentMoverCharacterCard
 */
class StudentMoverCharacterCardTest {

    private Game game;
    private Bag bag;
    private List<CharacterCard> studentMoverCards = new ArrayList<>();

    /**
     * Method initialization() creates an istance of every Character that has an effect on moving students.
     * These istances are saved in a List called StudentMoverCards.
     * This method is called before each test is called
     */


    @BeforeEach
    public void initialization(){
        bag = new Bag();
        studentMoverCards.add(CharacterCardCreator.createCharacterCard(CharacterID.MONK, bag));
        studentMoverCards.add(CharacterCardCreator.createCharacterCard(CharacterID.JESTER, bag));
        studentMoverCards.add(CharacterCardCreator.createCharacterCard(CharacterID.MINSTREL, bag));
        studentMoverCards.add(CharacterCardCreator.createCharacterCard(CharacterID.PRINCESS, bag));
        studentMoverCards.add(CharacterCardCreator.createCharacterCard(CharacterID.THIEF, bag));

    }

    /**
     * Method availableTest() tests, for every element in studentMoverCards, the isAvailable() method.
     * A true return is expected
     */

    @Test
    public void availableTest(){
        for(CharacterCard c : studentMoverCards){
            assertTrue(c.isAvailable());
        }
    }

    /**
     * Method tests the initial cost of every CharacterCard contained in studentMoverCards.
     * The cost is expected to be equal to the initialCost of the CharacterID associated to the CharacterCard.
     */

    @Test
    public void costTest(){
        for(CharacterCard c : studentMoverCards){
            assertEquals(c.getCharacterID().getInitialCost(), c.getCost());
        }
    }


    /**
     * Method tests the ability to increase the cost of the CharacterCard.
     * The cost is expected to be increased by one after the method increaseCost() is called.
     */

    @Test
    public void increaseCostTest(){
        for(CharacterCard c : studentMoverCards){
            int initialCost = c.getCost();
            c.updateCost();
            assertEquals(initialCost + 1, c.getCost() );
        }
    }



    /**
     * Method creates an array of Players. This methods serves as a shortcut for some tests.
     *
     * @return array of Players
     */
    public Player[] createPlayers(){
        Player player1 = new Player("Giulia", TowerColor.BLACK, 2, bag);
        Player player2 = new Player("Samu", TowerColor.WHITE, 2, bag);
        Player[] players = new Player[2];
        players[0] = player1;
        players[1] = player2;
        return players;
    }

    /**
     * Method tests the effectInfluence() method for every CharacterCard contained in studentMoverCards
     * they should have no effect on the influence calculation
     * The resulting array is expected to be an array of 0s, for every CharacterCard
     */

    @Test
    public void influenceTest(){
        Player[] players = createPlayers();
        Island island = new Island();
        island.addStudent(DRAGONS);
        for(CharacterCard c : studentMoverCards) {
            int[] result = c.effectInfluence(players, players[0], island, null);
            for (int j = 0; j < Clan.values().length; j++) {
                assertEquals(0, result[j]);
            }
        }
    }

    /**
     * Method tests effectMotherNature() method.
     * effectMotherNature() is expected to return 0.
     */

    @Test
    public void stepsMotherNatureTest() {
        for (CharacterCard c : studentMoverCards) {
            assertEquals(0, c.effectStepsMotherNature());
        }
    }

    /**
     * Method effectProfessorTest() tests the outcome of calling effectPlayerProfessor on a influenceCharacterCard class.
     * The expected result is that a player has to have greater students than any other player.
     */
    @ParameterizedTest
    @EnumSource(Clan.class)
    public void effectPlayerProfessor(Clan clan) {
        Player[] players = createPlayers();
        players[0].getChamber().addStudent(clan);
        players[0].getChamber().addStudent(clan);
        players[1].getChamber().addStudent(clan);
        players[1].getChamber().addStudent(clan);
        for (CharacterCard c : studentMoverCards)
            assertNull(c.effectPlayerProfessor(players, players[1], clan));
    }

    /**
     * Method initialEffectTest() tests the method applyInitialEffect() for both 2 and 3 players game.
     * A true return is expected.
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    public void initialEffectTest(int numOfPlayers){
        Player[] players = createPlayers();
        Turn turn = new Turn(players[0], numOfPlayers);
        for(CharacterCard cc: studentMoverCards)
            assertTrue(cc.applyInitialEffect(turn, players));
    }

    /**
     * Method applyTest1() tests the method applyEffect(Game game, Island island).
     * The method is expected to return false
     */
    @Test
    public void applyTest1(){
        assertDoesNotThrow(() -> game = new Game(2, "Giulia", true));
        assertDoesNotThrow(() -> game.addPlayer("Samu"));
        Island island =game.getIslandManager().getIsland(1);
        if (game.getIndexCurrPlayer() == 0) {
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CAT));
        } else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CAT));
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
        }

        for(CharacterCard cc: studentMoverCards)
                assertFalse(cc.applyEffect(game, island));
    }

    /**
     * Method applyTest2Monk() tests the applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2) when
     * the CharacterID is MONK. The method has to move a student, present on the Monk card to a chosen Island
     * Is expected that the chosen student, if it is present on the Monk card, is added on the island
     * A true result is expected
     * @param clan is the clan of belonging of the students on which we are going to test the effect
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void applyTest2Monk(Clan clan){
        assertDoesNotThrow(() -> game = new Game(2, "Giulia", true));
        assertDoesNotThrow(() -> game.addPlayer("Samu"));
        StudentContainer island = game.getIslandManager().getIsland(1);
        Map<Clan, Integer> initialStudents = game.getIslandManager().getIsland(1).getStudents();

        if(((StudentMoverCharacterCard) studentMoverCards.get(0)).getStudents().get(clan)>=1){
            Map<Clan, Integer> students = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
            students.put(clan, 1);
            boolean moved = studentMoverCards.get(0).applyEffect(game, island, students, null );
            assertTrue(moved);
            Map<Clan, Integer> finalStudents = game.getIslandManager().getIsland(1).getStudents();
            assertEquals(initialStudents.get(clan) + 1, finalStudents.get(clan));
        }
    }


    /**
     * Method applyTest2Monk() tests the applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2) when
     * the CharacterID is MONK and moving a student who is not on the card
     * Is expected a false return
     * @param clan is the clan of belonging of the students on which we are going to test the effect
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void applyTest2MonkStudentNotOnTheCard(Clan clan){
        assertDoesNotThrow(() -> game = new Game(2, "Giulia", true));
        StudentContainer island = game.getIslandManager().getIsland(1);

        if(((StudentMoverCharacterCard) studentMoverCards.get(0)).getStudents().get(clan)==0){
            Map<Clan, Integer> students = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
            students.put(clan, 1);
            boolean moved = studentMoverCards.get(0).applyEffect(game, island, students, null );
            assertFalse(moved);
        }

    }

    /**
     * Method applyTest2Jester() tests the method applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2)
     * when the CharacterID is Jester.
     * Is expected a true result and that the chosen students are moved in the player's hall
     * @param clan is the clan of belonging of the students on which we are going to test the effect
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void applyTest2Jester(Clan clan){
        setGame();

        Hall hall = game.getPlayers()[0].getHall();
        if(((StudentMoverCharacterCard) studentMoverCards.get(1)).getStudents().get(clan)>=1){
            hall.addStudent(Clan.values()[(clan.ordinal()+1)%5]);
            Map<Clan, Integer> initialStudents = hall.getStudents();
            Map<Clan, Integer> addingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
            addingStudents.put(clan, 1);
            Map<Clan, Integer> removingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
            removingStudents.put(Clan.values()[(clan.ordinal()+1)%5], 1);
            boolean moved = studentMoverCards.get(1).applyEffect(game, null, addingStudents, removingStudents);
            assertTrue(moved);
            Map<Clan, Integer> finalStudents = hall.getStudents();
            assertEquals(initialStudents.get(clan)+1, finalStudents.get(clan));
        }

    }

    /**
     * Method applyTest2Minstrel() tests the method applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2)
     * when the CharacterID is Minstrel.
     * Is expected a true result and that the chosen students are moved from the hall to the chamber, and from the chamber
     * to the hall
     */

    @Test
    public void applyTest2Minstrel(){
        setGame();

        game.getPlayers()[0].getHall().addStudent(DRAGONS);
        game.getPlayers()[0].getChamber().addStudent(PIXIES);

        Map<Clan, Integer> initialStudentsHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> initialStudentsChamber = game.getPlayers()[0].getChamber().getStudents();

        Map<Clan, Integer> moveToHall = TestUtil.studentMapCreator(1, 0, 0, 0, 0);
        Map<Clan, Integer> moveToChamber = TestUtil.studentMapCreator(0, 0, 0, 1, 0);

        boolean moved = studentMoverCards.get(2).applyEffect(game, null , moveToHall, moveToChamber);
        assertTrue(moved);

        Map<Clan, Integer> finalStudentsHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> finalStudentsChamber = game.getPlayers()[0].getChamber().getStudents();

        assertEquals(initialStudentsHall.get(PIXIES) + 1, finalStudentsHall.get(PIXIES));
        assertEquals(initialStudentsHall.get(DRAGONS) - 1, finalStudentsHall.get(DRAGONS));

        assertEquals(initialStudentsChamber.get(DRAGONS) + 1, finalStudentsChamber.get(DRAGONS));
        assertEquals(initialStudentsChamber.get(PIXIES) - 1, finalStudentsChamber.get(PIXIES));
    }

    /**
     * Method applyTestLimitCase1 tests the method applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2)
     * when the CharacterID is Minstrel if the chamber is empty.
     * Is expected a false result, none of the selected student is moved.
     */
    @Test
    public void applyTestLimitCase1(){
        setGame();
        Map<Clan, Integer> stud = new HashMap<>();
        for(Clan c : Clan.values()){
            stud.put(c, game.getPlayers()[0].getChamber().getNumStudents(c));
        }
        game.getPlayers()[0].getChamber().removeStudents(stud);
        Map<Clan, Integer> moveToHall = TestUtil.studentMapCreator(1, 0, 0, 0, 0);
        Map<Clan, Integer> moveToChamber = TestUtil.studentMapCreator(0, 0, 0, 1, 0);


        boolean moved = studentMoverCards.get(2).applyEffect(game, null, moveToHall, moveToChamber);

        assertFalse(moved);
    }

    /**
     * Method applyTestLimitCase1 tests the method applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2)
     * when the CharacterID is Minstrel if the chamber is full.
     * Is expected a false result, none of the selected student is moved.
     */

    @Test
    public void applyTestLimitCase2(){
        setGame();
        Map<Clan, Integer> stud = new HashMap<>();
        for(Clan c : Clan.values()){
            stud.put(c, game.getPlayers()[0].getChamber().getNumStudents(c));
        }
        game.getPlayers()[0].getChamber().removeStudents(stud);
        game.getPlayers()[0].getChamber().addStudents(TestUtil.studentMapCreator(10, 10, 10, 10, 10));

        Map<Clan, Integer> moveToHall = TestUtil.studentMapCreator(1, 0, 0, 0, 0);
        Map<Clan, Integer> moveToChamber = TestUtil.studentMapCreator(0, 0, 0, 1, 0);

        boolean moved = studentMoverCards.get(2).applyEffect(game, null, moveToHall, moveToChamber);

        Map<Clan, Integer> studentChamber = game.getPlayers()[0].getChamber().getStudents();

        assertFalse(moved);
        assertEquals(TestUtil.studentMapCreator(10, 10, 10, 10, 10), studentChamber );
    }

    /**
     * Method applyTest2Princess() tests the method applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2)
     * when the CharacterID is Princess.
     * Is expected a true result and that the chosen student is moved from the card to the chamber.
     * @param clan is the clan of belonging of the students on which we are going to test the effect
     */


    @ParameterizedTest
    @EnumSource(Clan.class)
    public void applyTest2Princess(Clan clan){
        setGame();

        Chamber chamber = game.getPlayers()[0].getChamber();
        Map<Clan, Integer> initialStudents = chamber.getStudents();

        if(((StudentMoverCharacterCard) studentMoverCards.get(3)).getStudents().get(clan)>=1){
            Map<Clan, Integer> students = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
            students.put(clan, 1);
            boolean moved = studentMoverCards.get(3).applyEffect(game, null, students, null);
            assertTrue(moved);
            Map<Clan, Integer> finalStudents = chamber.getStudents();
            assertEquals(initialStudents.get(clan) + 1, finalStudents.get(clan));

        }

    }

    /**
     * Method applyTest2Thief() tests the method applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2)
     * when the CharacterID is Thief.
     * Is expected a true result and that the for each student 3 students, if present, belonging to a specific clan,
     * are removed from each room. It is expected that if a player has fewer than three students, all he has is removed.
     * @param clan is the clan of belonging of the students on which we are going to test the effect
     */


    @ParameterizedTest
    @EnumSource(Clan.class)
    public void applyTest2Thief(Clan clan){
        setGame();

        game.getTurn().setCharacterClan(clan);

        Map<Clan, Integer> students = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        students.put(clan, 3);

        game.getPlayers()[1].getChamber().addStudents(students);
        game.getPlayers()[0].getChamber().addStudent(clan);

        boolean moved = studentMoverCards.get(4).applyEffect(game, null, null, null );

        assertTrue(moved);

        assertEquals(0, game.getPlayers()[0].getChamber().getStudents().get(clan));
        assertEquals(0, game.getPlayers()[1].getChamber().getStudents().get(clan));


    }

    /**
     * This method set the game. It is a shortcut for some tests
     */
    public void setGame(){
        assertDoesNotThrow(() -> game = new Game(2, "Giulia", true));
        assertDoesNotThrow(() -> game.addPlayer("Samu"));

        if (game.getIndexCurrPlayer() == 0) {
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CAT));
        } else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CAT));
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
        }
    }

}
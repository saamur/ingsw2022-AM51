package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.*;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Card;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.Clan.*;
import static org.junit.jupiter.api.Assertions.*;

//TODO this cannot extend well CharacterCardTest
public class InfluenceCharacterCardTest{
    Bag bag;
    CharacterCardCreator characterCardCreator = new CharacterCardCreator();
    List<CharacterCard> influenceCards = new ArrayList<>();

    /**
     * Method initialization() creates an istance of every Character that has an effect on influence.
     * These instances are saved in a List called influenceCards.
     * This method is called before each test is called.
     */
    @BeforeEach
    public void initialization(){
        bag = new Bag();
        influenceCards.add(characterCardCreator.createCharacterCard(CharacterID.HERALD, bag));
        influenceCards.add(characterCardCreator.createCharacterCard(CharacterID.CENTAUR, bag));
        influenceCards.add(characterCardCreator.createCharacterCard(CharacterID.KNIGHT, bag));
        influenceCards.add(characterCardCreator.createCharacterCard(CharacterID.MUSHROOMPICKER, bag));

    }

    /**
     * Method availableTest() for every element in influenceCards, tests the method isAvailable().
     * A true return is expected.
     */
    @Test
    public void availableTest(){
        for(CharacterCard c : influenceCards){
            assertTrue(c.isAvailable());
        }
    }

    /**
     * Method tests the initial cost of every CharacterCard contained in influenceCards.
     * The cost is expected to be equal to the initialCost of the CharacterID associated to the CharacterCard.
     */
    @Test
    public void costTest(){
        for(CharacterCard c : influenceCards){
            assertEquals(c.getCharacterID().getInitialCost(), c.getCost());
        }
    }

    /**
     * Method tests the ability to increase the cost of the CharacterCard.
     * The cost is expected to be increased by one after the method increaseCost() is called.
     */
    @Test
    public void increaseCostTest(){
        for(CharacterCard c: influenceCards){
            int initialCost = c.getCost();
            c.updateCost();
            assertEquals(initialCost + 1, c.getCost());
        }
    }

    /**
     * Method tests the effectInfluence() method called when the CharacterID is HERALD.
     * The resulting array is expected to be an array of 0s.
     */
    @Test
    public void influenceTestHerald(){
        Player[] players = createPlayers();
        Island island = new Island(); //the parameter island can be null, it is not used for HERALD
        island.addStudent(DRAGONS);
        int[] result = influenceCards.get(0).effectInfluence(players, players[0], island, null);
        for(int i=0; i<players.length; i++){
            assertEquals(0, result[i]);
        }
    }

    /**
     * Method tests the effectInfluence() method called when the CharacterID is CENTAUR.
     * The towers present on an island are not calculated.
     * The influence should be decreased by the number of islands merged only for the player that has the island.
     */
    @Test
    public void influenceTestCentaur(){
        Player[] players = createPlayers();
        IslandManager islandManager = new IslandManager();
        islandManager.conquerIsland(players[0], islandManager.getIsland(0));
        int[] expectedDelta = {-1 , 0};
        int[] result = influenceCards.get(1).effectInfluence(players, players[0], islandManager.getIsland(0), DRAGONS);
        for(int i=0; i<players.length; i++)
            assertEquals(expectedDelta[i], result[i]);
    }

    /**
     * Method tests the effectInfluence() method called when the CharacterID is KNIGHT.
     * The resulting array is expected to have a value of 2 in the position corresponding to the currPlayer.
     * All other position are expected to have a value of 0.
     */
    @Test
    public void influenceTestKnight(){
        Player[] players = createPlayers();
        //I don't have to input the island, delta ifluence will be +2 for the currPlayer
        //Clan can be null, it won't have an effect on this method call
        int[] result = influenceCards.get(2).effectInfluence(players, players[1], null, null);
        assertEquals(0, result[0]);
        assertEquals(2, result[1]);
    }

    /**
     * Method tests the effectInfluence() method called when the CharacterID is MUSHROOMPICKER.
     * The array returned by the method is expected to have a negative value in the position of the player that has the professor of the color corresponding to the Clan parameter.
     * The absolute value corresponds to the number of students present on the island.
     * All other positions are expected to have value 0.
     */
    @Test
    public void influenceTestMushroomPicker(){
        Player[] players = createPlayers();
        IslandManager islandManager = new IslandManager();
        Map<Clan, Integer> studentsOnIsland = islandManager.getIsland(3).getStudents();
        Map<Clan, Integer> studentsToBeAdded = TestUtil.studentMapCreator(0, 2, 3, 5, 2);

        islandManager.getIsland(3).addStudents(studentsToBeAdded);
        players[0].getChamber().setProfessor(DRAGONS, true);
        int[] expectedResult = {(-1) * (studentsOnIsland.get(DRAGONS) + studentsToBeAdded.get(DRAGONS)), 0};
        int [] result = influenceCards.get(3).effectInfluence(players, players[0], islandManager.getIsland(3), DRAGONS); //TODO HashMap for Players too?
        for(int i=0; i<players.length; i++)
            assertEquals(expectedResult[i], result[i]);
    }

    /**
     * Method tests effectMotherNature() method.
     * effectMotherNature() is expected to return 0.
     */
    @Test
    public void stepsMotherNatureTest(){
        for(CharacterCard cc: influenceCards){
            assertEquals(0, cc.effectStepsMotherNature());
        }
    }

    /**
     * Method effectProfessorTest() tests the outcome of calling effectPlayerProfessor on a influenceCharacterCard class.
     * The expected result is that a player has to have greater students than any other player.
     */
    @Test
    public void effectPlayerProfessor() {
        Player[] players = createPlayers();
        players[0].getChamber().addStudent(DRAGONS); //FIXME is it a problem that I can change (addStudents) a private attribute (Chamber)?
        players[0].getChamber().addStudent(DRAGONS);
        players[1].getChamber().addStudent(DRAGONS);
        players[1].getChamber().addStudent(DRAGONS);
        for (CharacterCard c : influenceCards)
            assertNull(c.effectPlayerProfessor(players, players[1], DRAGONS));
    }

    /**
     * Method initialEffectTest() tests the method applyInitialEffect().
     * A true return is expected.
     */
    @Test
    public void initialEffectTest(){
        Player[] players = createPlayers();
        Turn turn = new Turn(players[0], 2);
        for(CharacterCard cc: influenceCards)
            assertTrue(cc.applyInitialEffect(turn, players));
    }

    /**
     * Method applyTest1() tests the method applyEffect(Game game, Island island).
     * The method is expected to return false for every InfluenceCards except for HERALD .
     */
    @Test
    public void applyTest1(){
        Game game = new Game(2, "Fede", true);
        assertDoesNotThrow(() -> game.addPlayer("Samu"));
        Island island =game.getIslandManager().getIsland(1);
        if (game.getIndexCurrPlayer() == 0) {
            assertDoesNotThrow(() -> game.chosenCard("Fede", Card.CHEETAH));
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.TURTLE));
        }
        else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.TURTLE));
            assertDoesNotThrow(() -> game.chosenCard("Fede", Card.CHEETAH));
        }

        for(CharacterCard cc: influenceCards)
            if (cc.getCharacterID() == CharacterID.HERALD)
                assertTrue(cc.applyEffect(game, island));
            else
                assertFalse(cc.applyEffect(game, island));
    }

    /**
     * Method applyTest2() tests the method applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2).
     * The method when called is expected to return false.
     */
    @Test
    public void applyTest2(){
        Game game = new Game(2, "Fede", true);
        StudentContainer island = game.getIslandManager().getIsland(1);

        Map<Clan, Integer> students1 = TestUtil.studentMapCreator(0, 2, 3, 4, 1);

        Map<Clan, Integer> students2 = TestUtil.studentMapCreator(2, 2, 5, 7, 6);

        for(CharacterCard cc: influenceCards) {
            assertFalse(cc.applyEffect(game, island, null, null));
            assertFalse(cc.applyEffect(game, island, students1, students2));
        }
    }


    /**
     * Method creates an array of Players. This methods serves as a shortcut for some tests.
     *
     * @return array of Players
     */
    public Player[] createPlayers(){
        Player player1 = new Player("Fede", TowerColor.GRAY, 2, bag);
        Player player2 = new Player("Samu", TowerColor.WHITE, 2, bag);
        Player[] players = new Player[2];
        players[0] = player1;
        players[1] = player2;
        return players;
    }

}

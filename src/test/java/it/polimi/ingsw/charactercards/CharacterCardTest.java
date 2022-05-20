package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.exceptions.NumberOfPlayerNotSupportedException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterCardCreator;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

//TODO have added test to general charactercard package
public abstract class CharacterCardTest {
    CharacterCardCreator characterCardCreator = new CharacterCardCreator();
    Bag bag;
    CharacterCard characterCard;

    /**
     * Method stepsTest() tests how many steps are added to Mother Nature in a turn.
     * Expected result is 2.
     */
    @Test
    public void stepsTest(){
        assertEquals(0, characterCard.effectStepsMotherNature());
    }

    /**
     * Method costTest() tests if the initial cost is set correctly.
     * Expected result is 1.
     * After calling increaseCost(), the cost is expected to increase by one.
     */
    @Test
    public void costTest(){
        assertEquals(1, characterCard.getCost());
        characterCard.updateCost();
        assertEquals(2, characterCard.getCost());
    }

    /**
     * Method tests whether StepsCharacterCardTest has any effect on the influence.
     * Return is expected to be 0.
     */
    @Test
    public void influenceTest(){
        Player[] players = createPlayers();
        IslandManager islandManager = new IslandManager();
        islandManager.conquerIsland(players[0], islandManager.getIsland(1));
        islandManager.getIsland(1).addStudent(Clan.DRAGONS);

        int expected = 0;
        for(int i=0; i<Clan.values().length; i++)
            assertEquals(0, characterCard.effectInfluence(players, players[1], islandManager.getIsland(1), Clan.DRAGONS)[i]);
    }

    /**
     * Method effectProfessorTest() tests the outcome of calling effectPlayerProfessor on a StepsCharacterCard class.
     * The expected result is that a player has to have greater students than any other player.
     */
    @Test
    public void effectProfessorTest(){
        Player[] players = createPlayers();
        players[0].getChamber().addStudent(Clan.DRAGONS); //FIXME is it a problem that I can change (addStudents) a private attribute (Chamber)?
        players[0].getChamber().addStudent(Clan.DRAGONS);
        players[1].getChamber().addStudent(Clan.DRAGONS);
        players[1].getChamber().addStudent(Clan.DRAGONS);
        assertNull(characterCard.effectPlayerProfessor(players, players[1], Clan.DRAGONS));
    }

    /**
     * Method initialEffectTest() tests the method applyInitialEffect().
     * A true return is expected.
     * @param numOfPlayers number of players in the game
     *
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    public void initialEffectTest(int numOfPlayers){
        Player[] players = createPlayers();
        Turn turn = new Turn(players[0], numOfPlayers);
        assertTrue(characterCard.applyInitialEffect(turn, players));
    }

    /**
     * Method applyTest1() tests the method applyEffect(Game game, Island island).
     * The return is expected to be false.
     * @param numOfPlayers number of players in the game
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    public void applyTest1(int numOfPlayers){
        Game game = null;
        try {
            game = new Game(numOfPlayers, "Fede", true);
        } catch (NumberOfPlayerNotSupportedException e) {
            e.printStackTrace();
        }
        Island island = new Island();
        assertFalse(characterCard.applyEffect(game, island));
    }


    /**
     * Method applyTest2() tests the method applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2).
     * A false return is expected.
     * @param numOfPlayers number of players in the game
     */

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    public void applyTest2(int numOfPlayers){
        Game game = null;
        try {
            game = new Game(numOfPlayers, "Giu", true);
        } catch (NumberOfPlayerNotSupportedException e) {
            e.printStackTrace();
        }
        StudentContainer destination = new Island();
        Map<Clan, Integer> students1 = new EnumMap<>(Clan.class);
        assertFalse(characterCard.applyEffect(game, destination, students1, students1)); //game, destination and students1 can be null
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

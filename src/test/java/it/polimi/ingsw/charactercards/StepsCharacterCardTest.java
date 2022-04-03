package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.Clan;
import it.polimi.ingsw.Turn;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StepsCharacterCardTest tests the class StepsCharacterCard
 * @link StepsCharacterCard
 */
public class StepsCharacterCardTest {
    CharacterCardCreator ccc = new CharacterCardCreator();
    Bag bag;
    CharacterCard postman;

    @BeforeEach
    public void createCharacter(){
        bag = new Bag();
        postman = ccc.createCharacterCard(CharacterID.POSTMAN, bag);
    }

    /**
     * Method stepsTest() tests how many steps are added to Mother Nature in a turn.
     * Expected result is 2.
     */
    @Test
    public void stepsTest(){
        int stepsToBeAdded = postman.effectStepsMotherNature();
        assertEquals(2, stepsToBeAdded);
    }

    /**
     * Method costTest() tests if the initial cost is set correctly.
     * Expected result is 1.
     * After calling increaseCost(), the cost is expected to increase by one.
     */
    @Test
    public void costTest(){
        assertEquals(1, postman.getCost());
        postman.increaseCost();
        assertEquals(2, postman.getCost());
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

        for(int i=0; i<Clan.values().length; i++)
            assertEquals(0, postman.effectInfluence(players, players[1], islandManager.getIsland(1), Clan.DRAGONS)[i]);
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
        assertNull(postman.effectPlayerProfessor(players, players[1], Clan.DRAGONS));
    }

    /**
     * Method initialEffectTest() tests the method applyInitialEffect().
     * A true return is expected.
     *
     */
    @Test
    public void initialEffectTest(){
        Player[] players = createPlayers();
        Turn turn = new Turn(players[0], 2);
        assertTrue(postman.applyInitialEffect(turn, players));
    }

    /**
     * Method applyTest() tests the method applyEffect().
     */
    @Test
    public void applyTest(){
        Island island = new Island();
        //TODO create Game ??
        assertFalse(postman.applyEffect(null, island));
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

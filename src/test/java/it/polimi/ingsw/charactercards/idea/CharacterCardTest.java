package it.polimi.ingsw.charactercards.idea;

import it.polimi.ingsw.*;
import it.polimi.ingsw.charactercards.CharacterCard;
import it.polimi.ingsw.charactercards.CharacterCardCreator;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        characterCard.increaseCost();
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
     *
     */
    @Test
    public void initialEffectTest(){
        Player[] players = createPlayers();
        Turn turn = new Turn(players[0], 2);
        assertTrue(characterCard.applyInitialEffect(turn, players));
    }

    /**
     * Method applyTest1() tests the method applyEffect(Game game, Island island).
     * The return is expected to be false.
     */
    @Test
    public void applyTest1(){
        Game game = new Game(2, "Fede", true);
        Island island = new Island();
        assertFalse(characterCard.applyEffect(game, island));
    }

    /**
     * Method applyTest2() tests the method applyEffect(Game game, StudentContainer sc, int[] stud1, int[] stud2).
     * A false return is expected.
     */
    @Test
    public void applyTest2(){
        Game game = new Game(3, "Giu", true);
        StudentContainer destination = new Island();
        int[] students1 = new int[Clan.values().length];
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

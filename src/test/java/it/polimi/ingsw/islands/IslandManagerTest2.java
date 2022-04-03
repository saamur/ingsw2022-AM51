package it.polimi.ingsw.islands;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test checkMerge() method in IslandManager
 *
 * @link checkMerge()
 */
public class IslandManagerTest2 {
    List<Player> players = new ArrayList<>();
    IslandManager islandManager;
    Bag bag;

    @BeforeEach
    public void initialization() {
        bag = new Bag();
        players.add(new Player("Giulia", TowerColor.BLACK, 3, bag));
        players.add(new Player("Fede", TowerColor.GRAY, 3, bag));
        players.add(new Player("Samu", TowerColor.WHITE, 3, bag));

        islandManager = new IslandManager();
    }

    /**
     * test check if after conquerIsland() is called, the number of islands is decreased.
     * Number of Islands is expected to be 11 after method gets called on two adjacent islands.
     */
    @Test
    public void normalConquerTest() {
        List<Island> islands = islandManager.getIslands();

        islandManager.conquerIsland(players.get(1), islands.get(5));
        islandManager.conquerIsland(players.get(1), islands.get(6));

        islands = islandManager.getIslands();

        assertEquals(11, islands.size());

    }

    /**
     * Checking if merge works at the extremes of the array
     */
    @Test
    public void limitsConquerTest() {
        islandManager.conquerIsland(players.get(1), islandManager.getIslands().get(0));
        islandManager.conquerIsland(players.get(1), islandManager.getIslands().get(11));

        assertEquals(11, islandManager.getIslands().size());
    }

    /**
     * Checking if merge works if both neighbouring islands have been conquered by the same player
     */
    @Test
    public void tripleMergingTest() {
        islandManager.conquerIsland(players.get(1), islandManager.getIsland(11));
        islandManager.conquerIsland(players.get(1), islandManager.getIsland(1));
        islandManager.conquerIsland(players.get(1), islandManager.getIsland(0));

        assertEquals(10, islandManager.getNumberOfIslands());
    }

    /**
     * Checking if merge works when island is already conquered  by another player
     */
    @Test
    public void alreadyConqueredTest() {
        for (int i = 0; i < 4; i++) {
            islandManager.conquerIsland(players.get(0), islandManager.getIslands().get(i * 3));
            islandManager.conquerIsland(players.get(1), islandManager.getIslands().get((i * 3) + 1));
            islandManager.conquerIsland(players.get(2), islandManager.getIslands().get((i * 3) + 2));
        }

        islandManager.conquerIsland(players.get(0), islandManager.getIslands().get(1));
        assertEquals(11, islandManager.getIslands().size());
        islandManager.conquerIsland(players.get(0), islandManager.getIslands().get(1));
        assertEquals(9, islandManager.getIslands().size());
    }


    //TODO code 100% covered, but IDK if tests are complete
}

package it.polimi.ingsw.islands;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

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
        int numOfPlayers = 3;
        bag = new Bag();
        players.add(new Player("Giulia", TowerColor.BLACK, numOfPlayers, bag));
        players.add(new Player("Fede", TowerColor.GRAY, numOfPlayers, bag));
        players.add(new Player("Samu", TowerColor.WHITE, numOfPlayers, bag));

        islandManager = new IslandManager();
    }

    /**
     * test check if after conquerIsland() is called, the number of islands is decreased if the conquered islands are adjacent.
     * tests all cases, the normal conquest, the conquest of two islands at the ends of the array and the conquest of two not adjacent islands
     * Number of Islands is expected to be 11 after method gets called on two adjacent islands, 12 otherwise.
     */

    @ParameterizedTest
    @CsvSource({"5, 6, 11", "0, 11, 11", "5, 7, 12"})
    public void ConquerTest(int indexFirstIsland, int indexSecondIsland, int expectedNumOfIslands) {
        List<Island> islands = islandManager.getIslands();
        int playerIndex = 1;

        islandManager.conquerIsland(players.get(playerIndex), islands.get(indexFirstIsland));
        islandManager.conquerIsland(players.get(playerIndex), islands.get(indexSecondIsland));

        islands = islandManager.getIslands();

        assertEquals(expectedNumOfIslands, islands.size());

    }


    /**
     * Checking if merge works if the same player conquer three islands
     * In the first case all three conquered islands are close to each other.
     * these are expected to unite and the final number of islands to become 10.
     * In the second case, only 2 of the 3 conquered islands are close.
     * Only the two neighboring islands are expected to merge and the number of islands will become 11.
     */
    @ParameterizedTest
    @CsvSource(value = {"11, 1, 0, 10", "1, 2, 5, 11"})
    public void tripleMergingTest(int indexFirstIsland, int indexSecondIsland, int indexThirdIsland, int expectedNumberOfIsland) {
        int playerIndex = 1;
        islandManager.conquerIsland(players.get(playerIndex), islandManager.getIsland(indexFirstIsland));
        islandManager.conquerIsland(players.get(playerIndex), islandManager.getIsland(indexSecondIsland));
        islandManager.conquerIsland(players.get(playerIndex), islandManager.getIsland(indexThirdIsland));

        assertEquals(expectedNumberOfIsland, islandManager.getNumberOfIslands());
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

    @Test
    public void insufficientTowersTest() {
        islandManager.conquerIsland(players.get(1), islandManager.getIsland(4));
        islandManager.conquerIsland(players.get(1), islandManager.getIsland(3));
        islandManager.conquerIsland(players.get(1), islandManager.getIsland(2));
        assertEquals(3, islandManager.getIsland(2).getNumberOfIslands());

        players.get(0).removeTowers(4);
        assertEquals(2, players.get(0).getNumberOfTowers());

        islandManager.conquerIsland(players.get(0), islandManager.getIsland(2));

        assertEquals(3, islandManager.getIsland(2).getNumberOfIslands());
        assertEquals(2, islandManager.getIsland(2).getNumberOfTowers());
        assertEquals(0, players.get(0).getNumberOfTowers());
    }


}

package it.polimi.ingsw.islands;

import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Class tests IslandManager.
 *
 * @link IslandManager
 */
public class IslandManagerTest {
    IslandManager islandManager;

    /**
     * Method initializes class islandManager before every test
     */
    @BeforeEach
    public void createIslandManager(){
        islandManager = new IslandManager();
    }

    /**
     * Method that check whether the distance from motherNature and an Island is calculated correctly
     */
    @Test
    public void distanceTest(){
        Random random = new Random();
        Island motherNaturePosition = islandManager.getMotherNaturePosition();
        List<Island> islands = islandManager.getIslands();
        int chosenIslandPosition = random.nextInt(12);
        int distance = islandManager.distanceFromMotherNature(islands.get(chosenIslandPosition));
        int difference = chosenIslandPosition - islands.indexOf(motherNaturePosition);

        assertEquals(difference >= 0 ? difference : 12 + difference, distance);
    }


    /**
     * Testing if distanceFromCurrentIsland can handle situation when parameter is island with Mother Nature.
     * Distance is expected to be 0.
     */
    @Test
    public void distanceZeroTest(){
        Random random = new Random();
        int distance = islandManager.distanceFromMotherNature(islandManager.getMotherNaturePosition());
        assertEquals(0, distance);
    }

    /**
     * Method tests whether setMotherNature accepts an island which is not contained in islandManager.
     */
    @Test
    public void setMotherNatureTest(){
        Island island = new Island();
        islandManager.setMotherNaturePosition(island);
        assertNotEquals(island, islandManager.getMotherNaturePosition());

        List<Island> islands = islandManager.getIslands();
        Random random = new Random();
        int islandPosition = random.nextInt(islands.size());
        islandManager.setMotherNaturePosition(islands.get(islandPosition));
        assertEquals(islandManager.getMotherNaturePosition(), islands.get(islandPosition));
    }

    //TODO use in tests getIslands e getNumberOfIslands

}

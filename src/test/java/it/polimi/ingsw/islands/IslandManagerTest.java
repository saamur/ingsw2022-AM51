package it.polimi.ingsw.islands;

import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

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
        int bound = 12;
        Random random = new Random();
        Island motherNaturePosition = islandManager.getMotherNaturePosition();
        List<Island> islands = islandManager.getIslands();
        int chosenIslandPosition = random.nextInt(bound);
        int distance = islandManager.distanceFromMotherNature(islands.get(chosenIslandPosition));
        int difference = chosenIslandPosition - islands.indexOf(motherNaturePosition);

        assertEquals(difference >= 0 ? difference : bound + difference, distance);
    }


    /**
     * Testing if distanceFromCurrentIsland can handle situation when parameter is island with Mother Nature.
     * Distance is expected to be 0.
     */
    @Test
    public void distanceZeroTest(){
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

    /**
     * Method tests case when an invalid index is called.
     * Method is expected to return null.
     * @param islandIndex invalid island index
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 13})
    public void invalidIndexTest(int islandIndex){
        assertNull(islandManager.getIsland(islandIndex));

    }

}

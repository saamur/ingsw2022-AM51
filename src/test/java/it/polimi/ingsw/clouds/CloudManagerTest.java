package it.polimi.ingsw.clouds;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.clouds.CloudManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * CloudManagerTest tests the class CloudManager
 * @see CloudManager
 */
public class CloudManagerTest {
    CloudManager cloudManager;
    Bag bag;



    public void createCloudManager(int numOfPlayers) {
        cloudManager = new CloudManager(numOfPlayers);
        bag = new Bag();
    }

    /**
     * Method fillCloudsTest() tests the method fillClouds() for 2 and 3 player games
     * @param numOfPlayers number of players in the game
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    public void fillCloudsTest(int numOfPlayers){
        createCloudManager(numOfPlayers);
        for(int i=0; i<numOfPlayers; i++) {
            cloudManager.getCloud(i).pick();
        }
        cloudManager.fillClouds(bag);

        for(int i=0; i<numOfPlayers; i++)
            assertFalse(cloudManager.getCloud(i).isEmpty());
    }

    /**
     * Method getUnexistentCloud() tests the method getCloud(int index).
     * When an index less than 0 and greater than the length of the array the method should return null.
     * @param numOfPlayers number of players in the game
     * @param outOfBoundsIndex index of an unexistent cloud
     */

    @ParameterizedTest
    @CsvSource({"2, 12", "3, 12", "2, -2", "3, -5"})
    public void getUnexistentCloud(int numOfPlayers, int outOfBoundsIndex) {
        createCloudManager(numOfPlayers);
        assertNull(cloudManager.getCloud(outOfBoundsIndex));
    }

}

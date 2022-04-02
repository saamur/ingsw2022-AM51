package it.polimi.ingsw.clouds;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.clouds.CloudManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * CloudManagerTest tests the class CloudManager
 * @link CloudManager
 */
public class CloudManagerTest {
    CloudManager cloudManager;
    Bag bag;

    @BeforeEach
    public void createCloudManager(){
        cloudManager = new CloudManager(3);
        bag = new Bag();
    }
    /**
     * Method fillCloudsTest() tests the method fillClouds()
     *
     */
    @Test
    public void fillCloudsTest(){
        for(int i=0; i<3; i++) {
            cloudManager.getCloud(i).pick();
        }
        cloudManager.fillClouds(bag);

        for(int i=0; i<3; i++)
            assertFalse(cloudManager.getCloud(i).isEmpty());
    }

    /**
     * Method getUnexistentCloud() tests the method getCloud(int index).
     * When an index less than 0 and greater than the length of the array the method should return null.
     */
    @Test
    public void getUnexistentCloud() {
        assertNull(cloudManager.getCloud(12));
    }

    //I have decided not to test getClouds() method yet because it has not been called yet by any other class.
    // Maybe it can be deleted.
}

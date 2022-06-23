package it.polimi.ingsw.model.clouds;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.constants.GameConstants;

import java.io.Serializable;

/**
 * The CloudManager class manages the clouds of the game and their filling
 *
 */
public class CloudManager implements Serializable {

    private final Cloud[] clouds;
    private final int numStudentsPerCloud;

    /**
     * Contructs the CloudManager with its Clouds
     * @param numPlayers    the number of the players of the game
     */
    public CloudManager(int numPlayers) {
        clouds = new Cloud[numPlayers];
        for (int i = 0; i < clouds.length; i++)
            clouds[i] = new Cloud();
        numStudentsPerCloud = GameConstants.getNumStudentsPerCloud(numPlayers);
    }

    /**
     * The method fillClouds fills all the Clouds in clouds with a number of students of numStudentsPerCloud,
     * taking the students from the parameter bag
     * @param bag   the Bag from which the students are taken
     */
    public void fillClouds(Bag bag) {
        for (Cloud c : clouds)
            c.fill(bag.draw(numStudentsPerCloud));
    }


    /**
     * The method getCloud returns the island in the position of the parameter,
     * null if the index is out of bounds
     * @param index the index of the desired Cloud
     * @return      the Cloud in position index of the array clouds
     */
    public Cloud getCloud(int index) {
        if (index < 0 || index >= clouds.length)
            return null;
        return clouds[index];
    }

    public Cloud[] getClouds() {
        return clouds.clone();
    }


}

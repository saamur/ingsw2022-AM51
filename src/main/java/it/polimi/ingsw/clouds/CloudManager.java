package it.polimi.ingsw.clouds;

import it.polimi.ingsw.Bag;

public class CloudManager {

    private final Cloud[] clouds;
    private final int numStudentsPerCloud;

    public CloudManager(int numPlayers) {
        clouds = new Cloud[numPlayers];
        numStudentsPerCloud = numPlayers == 2 ? 3 : 4;
    }

    public void fillClouds(Bag bag) {
        for (Cloud c : clouds)
            c.fill(bag.draw(numStudentsPerCloud));
    }

    public Cloud[] getClouds() {
        return clouds.clone();
    }
}

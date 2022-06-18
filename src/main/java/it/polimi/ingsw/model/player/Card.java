package it.polimi.ingsw.model.player;

/**
 * The Card class is an enumeration with the list of the assistant cards of the game
 * and their constants
 *
 */
public enum Card {

    CHEETAH(1, 1),
    OSTRICH(2, 1),
    CAT(3, 2),
    EAGLE(4, 2),
    FOX(5, 3),
    LIZARD(6, 3),
    OCTOPUS(7, 4),
    DOG(8, 4),
    ELEPHANT(9, 5),
    TURTLE(10, 5);


    private final int priority;
    private final int maxStepsMotherNature;

    Card(int priority, int maxStepsMotherNature) {
        this.priority = priority;
        this.maxStepsMotherNature = maxStepsMotherNature;
    }

    public int getPriority() {
        return priority;
    }

    public int getMaxStepsMotherNature() {
        return maxStepsMotherNature;
    }
}

package it.polimi.ingsw.charactercards;

/**
 * CharacterID is an enumeration with the list of the game's characters
 * and their initial cost as a constant
 *
 */
public enum CharacterID {

    MONK(1),
    FARMER(2),
    HERALD(3),
    POSTMAN(1),
    GRANDMA(2),
    CENTAUR(3),
    JESTER(1),
    KNIGHT(2),
    MUSHROOMPICKER(3),
    MINISTREL(1),
    PRINCESS(2),
    THIEF(3);

    private final int initialCost;

    CharacterID(int initialCost){
        this.initialCost=initialCost;
    }

    public int getInitialCost() {
        return initialCost;
    }


}

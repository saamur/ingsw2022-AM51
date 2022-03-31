package it.polimi.ingsw.charactercards;

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

    private CharacterID(int initialCost){
        this.initialCost=initialCost;
    }

    public int getInitialCost() {
        return initialCost;
    }


}

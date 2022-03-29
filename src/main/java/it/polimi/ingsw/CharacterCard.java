package it.polimi.ingsw;

public abstract class CharacterCard {

    private final CharacterID characterID;
    private int cost;

    public CharacterCard (CharacterID characterID) {
        this.characterID = characterID;
        cost = characterID.getInitialCost();
    }

    public CharacterID getCharacterID() {
        return characterID;
    }

    public int getCost() {
        return cost;
    }

    public int[] effectInfluence(Player[] players, Player currPlayer, Island island) {
        return new int[Clan.values().length];
    }

    public int effectStepsMotherNature () {
        return 0;
    }

}

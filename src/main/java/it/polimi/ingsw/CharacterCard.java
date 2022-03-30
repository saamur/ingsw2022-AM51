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

    public void increaseCost() {
        cost++;
    }

    public int[] effectInfluence(Player[] players, Player currPlayer, Island island, Clan clan) {
        return new int[Clan.values().length];
    }

    public int effectStepsMotherNature () {
        return 0;
    }

    public boolean applyEffect(Island isl) {
        return false;
    }

    public Player effectPlayerProfessor (Player[] players, Player currPlayer, Clan clan) {
        return Turn.defaultPlayerProfessor(players, currPlayer, clan);
    }

}

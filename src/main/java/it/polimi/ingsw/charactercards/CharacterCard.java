package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.*;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;

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

    public boolean isAvailable() {
        return true;
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

    public Player effectPlayerProfessor (Player[] players, Player currPlayer, Clan clan) {
        return Turn.defaultPlayerProfessor(players, clan);
    }

    public boolean applyInitialEffect (Turn turn, Player[] players) {
        return true;
    }

    public boolean applyEffect (Game game, Island island) {
        return true;
    }

}

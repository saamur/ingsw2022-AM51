package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.*;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;

public class InfluenceCharacterCard extends CharacterCard {

    private interface DeltaInfluence {
        int[] calculateDeltaInfluence(Player[] players, Player currPlayer, Island island, Clan clan);
    }

    private interface Effect {
        boolean applyEffect(Game game, Island island);
    }

    private static final DeltaInfluence[] DELTA_INFLUENCES;
    private static final Effect[] EFFECTS;

    static {

        DELTA_INFLUENCES = new DeltaInfluence[CharacterID.values().length];

        DELTA_INFLUENCES[CharacterID.HERALD.ordinal()] = (players, currPlayer, island, clan) -> new int[players.length];

        DELTA_INFLUENCES[CharacterID.CENTAUR.ordinal()] = (players, currPlayer, island, clan) -> {
            int [] delta = new int[players.length];
            for (int i = 0; i < players.length; i++)
                if (players[i] == island.getControllingPlayer())
                    delta[i] = (-1) * island.getNumberOfTowers();
            return delta;
        };

        DELTA_INFLUENCES[CharacterID.KNIGHT.ordinal()] = (players, currPlayer, island, clan) -> {
            int [] delta = new int[players.length];
            for (int i = 0; i < players.length; i++)
                if (players[i] == currPlayer)
                    delta[i] = 2;
            return delta;
        };

        DELTA_INFLUENCES[CharacterID.MUSHROOMPICKER.ordinal()] = (players, currPlayer, island, clan) -> {
            int [] delta = new int[players.length];
            for (int i = 0; i < players.length; i++)
                if (players[i].getChamber().hasProfessor(clan))
                    delta[i] = island.getStudents()[clan.ordinal()];
            return delta;
        };

    }

    static {

        EFFECTS = new Effect[CharacterID.values().length];

        EFFECTS[CharacterID.HERALD.ordinal()] = (game, island) -> {
            game.checkInfluence(island);
            return true;
        };
        EFFECTS[CharacterID.CENTAUR.ordinal()] = (game, island) -> true;
        EFFECTS[CharacterID.KNIGHT.ordinal()] = (game, island) -> true;
        EFFECTS[CharacterID.MUSHROOMPICKER.ordinal()] = (game, island) -> true;

    }

    public InfluenceCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public int[] effectInfluence(Player[] players, Player currPlayer, Island island, Clan clan) {
        return DELTA_INFLUENCES[getCharacterID().ordinal()].calculateDeltaInfluence(players, currPlayer, island, clan);
    }

    @Override
    public boolean applyEffect(Game game, Island island) {
        return EFFECTS[getCharacterID().ordinal()].applyEffect(game, island);
    }

}

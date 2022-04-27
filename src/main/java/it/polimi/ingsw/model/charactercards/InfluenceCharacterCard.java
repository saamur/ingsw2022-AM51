package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.player.Player;

import java.util.EnumMap;
import java.util.Map;

/**
 * InfluenceCharacterCard class models the character cards
 * that have an impact on the calculus of the influence of an island
 *
 */
public class InfluenceCharacterCard extends CharacterCard {

    private interface DeltaInfluence {
        int[] calculateDeltaInfluence(Player[] players, Player currPlayer, Island island, Clan clan);
    }

    private interface Effect {
        boolean applyEffect(Game game, Island island);
    }

    private static final Map<CharacterID, DeltaInfluence> DELTA_INFLUENCES;
    private static final Map<CharacterID, Effect> EFFECTS;

    static {

        DELTA_INFLUENCES = new EnumMap<>(CharacterID.class);

        DELTA_INFLUENCES.put(CharacterID.HERALD,  (players, currPlayer, island, clan) -> new int[players.length] );

        DELTA_INFLUENCES.put(CharacterID.CENTAUR,  (players, currPlayer, island, clan) -> {
            int[] delta = new int[players.length];
            for (int i = 0; i < players.length; i++)
                if (players[i] == island.getControllingPlayer())
                    delta[i] = (-1) * island.getNumberOfTowers();
            return delta;
        });

        DELTA_INFLUENCES.put(CharacterID.KNIGHT,  (players, currPlayer, island, clan) -> {
            int[] delta = new int[players.length];
            for (int i = 0; i < players.length; i++)
                if (players[i] == currPlayer)
                    delta[i] = 2;
            return delta;
        });

        DELTA_INFLUENCES.put(CharacterID.MUSHROOMPICKER,  (players, currPlayer, island, clan) -> {
            int[] delta = new int[players.length];
            for (int i = 0; i < players.length; i++)
                if (players[i].getChamber().hasProfessor(clan))
                    delta[i] = (-1) * island.getStudents().get(clan);
            return delta;
        });

    }

    static {

        EFFECTS = new EnumMap<>(CharacterID.class);

        EFFECTS.put(CharacterID.HERALD, (game, island) -> {
            game.checkInfluence(island);
            return true;
        });
        EFFECTS.put(CharacterID.CENTAUR, (game, island) -> false);
        EFFECTS.put(CharacterID.KNIGHT, (game, island) -> false);
        EFFECTS.put(CharacterID.MUSHROOMPICKER, (game, island) -> false);

    }

    public InfluenceCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public int[] effectInfluence(Player[] players, Player currPlayer, Island island, Clan clan) {
        return DELTA_INFLUENCES.get(getCharacterID()).calculateDeltaInfluence(players, currPlayer, island, clan);
    }

    @Override
    public boolean applyEffect(Game game, Island island) {
        return EFFECTS.get(getCharacterID()).applyEffect(game, island);
    }

}

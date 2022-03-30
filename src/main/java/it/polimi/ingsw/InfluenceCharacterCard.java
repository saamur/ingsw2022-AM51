package it.polimi.ingsw;

public class InfluenceCharacterCard extends CharacterCard {

    private interface DeltaInfluence {
        int[] calculateDeltaInfluence(Player[] players, Player currPlayer, Island island, Clan clan);
    }

    private interface InitialEffect {
        boolean applyEffect(Turn turn, IslandManager islandManager, Island island, Player[] players);
    }

    private static final DeltaInfluence[] DELTA_INFLUENCES;
    private static final InitialEffect[] INITIAL_EFFECT;

    static {

        DELTA_INFLUENCES = new DeltaInfluence[CharacterID.values().length];

        DELTA_INFLUENCES[CharacterID.HERALD.ordinal()] = (players, currPlayer, island, clan) -> new int[players.length];

        DELTA_INFLUENCES[CharacterID.CENTAUR.ordinal()] = (players, currPlayer, island, clan) -> {
            int [] delta = new int[players.length];
            for (int i = 0; i < players.length; i++)
                if (players[i] == island.getControllingPlayer())
                    delta[i] = (-1) * island.getNumberOfIslands();
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

        INITIAL_EFFECT = new InitialEffect[CharacterID.values().length];

        INITIAL_EFFECT[CharacterID.HERALD.ordinal()] = (turn, islandManager, island, players) -> {
            turn.updateInfluence(islandManager, island, players);
            return true;
        };
        INITIAL_EFFECT[CharacterID.CENTAUR.ordinal()] = (turn, islandManager, island, players) -> true;
        INITIAL_EFFECT[CharacterID.KNIGHT.ordinal()] = (turn, islandManager, island, players) -> true;
        INITIAL_EFFECT[CharacterID.MUSHROOMPICKER.ordinal()] = (turn, islandManager, island, players) -> true;

    }

    public InfluenceCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public int[] effectInfluence(Player[] players, Player currPlayer, Island island, Clan clan) {
        return DELTA_INFLUENCES[getCharacterID().ordinal()].calculateDeltaInfluence(players, currPlayer, island, clan);
    }

    @Override
    public boolean applyInitialEffect(Turn turn, IslandManager islandManager, Island island, Player[] players) {
        return INITIAL_EFFECT[getCharacterID().ordinal()].applyEffect(turn, islandManager, island, players);
    }

}

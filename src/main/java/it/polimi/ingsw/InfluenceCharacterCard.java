package it.polimi.ingsw;

public class InfluenceCharacterCard extends CharacterCard {

    private interface DeltaInfluence{
        int[] calculateDeltaInfluence(Player[] players, Player currPlayer, Island island, Clan clan);
    }

    private static final DeltaInfluence[] DELTA_INFLUENCES;

    static {

        DELTA_INFLUENCES = new DeltaInfluence[CharacterID.values().length];

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

    public InfluenceCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public int[] effectInfluence(Player[] players, Player currPlayer, Island island, Clan clan) {
        return DELTA_INFLUENCES[getCharacterID().ordinal()].calculateDeltaInfluence(players, currPlayer, island, clan);
    }
}

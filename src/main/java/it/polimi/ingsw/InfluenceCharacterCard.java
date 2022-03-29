package it.polimi.ingsw;

public class InfluenceCharacterCard extends CharacterCard {

    private interface DeltaInfluence{
        int[] calculateDeltaInfluence(Player[] players, Player currPlayer, Island island, Clan clan);
    }

    private final DeltaInfluence deltaInfluence;

    public InfluenceCharacterCard(CharacterID characterID) {

        super(characterID);

        switch (getCharacterID()) {

            case CENTAUR:
                deltaInfluence = (players, currPlayer, island, clan) -> {
                    int [] delta = new int[players.length];
                    for (int i = 0; i < players.length; i++)
                        if (players[i] == island.getControllingPlayer())
                            delta[i] = (-1) * island.getNumberOfIslands();
                    return delta;
                };
                break;

            case KNIGHT:
                deltaInfluence = (players, currPlayer, island, clan) -> {
                    int [] delta = new int[players.length];
                    for (int i = 0; i < players.length; i++)
                        if (players[i] == currPlayer)
                            delta[i] = 2;
                    return delta;
                };
                break;

            case MUSHROOMPICKER:
                deltaInfluence = (players, currPlayer, island, clan) -> {
                    int [] delta = new int[players.length];
                    for (int i = 0; i < players.length; i++)
                        if (players[i].getChamber().hasProfessor(clan))
                            delta[i] = island.getStudents()[clan.ordinal()];
                    return delta;
                };
                break;

            default:
                deltaInfluence = (players,currPlayer,island, clan) -> new int[players.length];

        }

    }

    @Override
    public int[] effectInfluence(Player[] players, Player currPlayer, Island island, Clan clan) {
        return deltaInfluence.calculateDeltaInfluence(players, currPlayer, island, clan);
    }
}

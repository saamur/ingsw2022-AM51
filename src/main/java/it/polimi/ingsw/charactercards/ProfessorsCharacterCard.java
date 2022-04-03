package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.*;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;

public class ProfessorsCharacterCard extends CharacterCard {

    private interface PlayerProfessor {
        Player calculatePlayerProfessor (Player[] players, Player currPlayer, Clan clan);
    }

    private static final PlayerProfessor[] PLAYER_PROFESSORS;

    static {

        PLAYER_PROFESSORS = new PlayerProfessor[CharacterID.values().length];

        PLAYER_PROFESSORS[CharacterID.FARMER.ordinal()] = (players, currPlayer, clan) -> {
            int[] clanStud = new int[players.length];
            for (int i = 0; i < clanStud.length; i++)
                clanStud[i] = players[i].getChamber().getNumStudents(clan);
            int posMax = 0;
            boolean unique = true;
            for (int i = 1;  i < clanStud.length; i++) {
                if (clanStud[i] > clanStud[posMax]){
                    posMax = i;
                    unique = true;
                }
                else if (clanStud[i] == clanStud[posMax]) {
                    if(players[i] == currPlayer){
                        posMax = i;
                        unique = true;
                    }
                    else if (players[posMax] != currPlayer)
                        unique = false;
                }
            }
            if (unique)
                return players[posMax];
            return null;
        };

    }

    public ProfessorsCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public Player effectPlayerProfessor(Player[] players, Player currPlayer, Clan clan) {
        return PLAYER_PROFESSORS[getCharacterID().ordinal()].calculatePlayerProfessor(players, currPlayer, clan);
    }

    @Override
    public boolean applyInitialEffect(Turn turn, Player[] players) {
        turn.updateProfessors(players);
        return true;
    }

}

package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.*;
import it.polimi.ingsw.player.Player;

import java.util.EnumMap;
import java.util.Map;

/**
 * ProfessorsCharacterCard class models the character cards
 * that have an impact on the rules that govern the ownership of the professors
 *
 */
public class ProfessorsCharacterCard extends CharacterCard {

    private interface PlayerProfessor {
        Player calculatePlayerProfessor (Player[] players, Player currPlayer, Clan clan);
    }

    private static final Map<CharacterID, PlayerProfessor> PLAYER_PROFESSORS;

    static {

        PLAYER_PROFESSORS = new EnumMap<>(CharacterID.class);

        PLAYER_PROFESSORS.put(CharacterID.FARMER, (players, currPlayer, clan) -> {
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
        });

    }

    public ProfessorsCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public Player effectPlayerProfessor(Player[] players, Player currPlayer, Clan clan) {
        return PLAYER_PROFESSORS.get(getCharacterID()).calculatePlayerProfessor(players, currPlayer, clan);
    }

    @Override
    public boolean applyInitialEffect(Turn turn, Player[] players) {
        turn.updateProfessors(players);
        return true;
    }

}

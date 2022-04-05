package it.polimi.ingsw;

import it.polimi.ingsw.charactercards.CharacterID;
import it.polimi.ingsw.player.Card;

import java.util.Map;

public interface GameInterface {

    boolean addPlayer (String nickname);
    boolean chosenCard (String playerNickname, Card card);
    boolean moveStudentToChamber (String playerNickname, Clan clan);
    boolean moveStudentToIsland (String playerNickname, Clan clan, int islandIndex);
    boolean moveMotherNature (String playerNickname, int islandIndex);
    boolean chosenCloud (String playerNickname, int cloudIndex);
    boolean endTurn (String playerNickname);
    boolean activateCharacterCard (String playerNickname, CharacterID characterID);
    boolean applyCharacterCardEffect (String playerNickname, int islandIndex);
    boolean setClanCharacter (String playerNickname, Clan clan);
    boolean applyCharacterCardEffect (String playerNickname, StudentContainer destination, Map<Clan, Integer> students1, Map<Clan, Integer> students2);

}

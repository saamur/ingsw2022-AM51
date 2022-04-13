package it.polimi.ingsw;

import it.polimi.ingsw.charactercards.CharacterID;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.player.Card;

import java.util.Map;

public interface GameInterface {

    void addPlayer (String nickname) throws NicknameNotAvailableException, WrongGamePhaseException;
    void chosenCard (String playerNickname, Card card) throws NonExistingPlayerException, WrongPlayerException, WrongGamePhaseException, NotValidMoveException;
    boolean moveStudentToChamber (String playerNickname, Clan clan);
    boolean moveStudentToIsland (String playerNickname, Clan clan, int islandIndex);
    boolean moveMotherNature (String playerNickname, int islandIndex);
    boolean chosenCloud (String playerNickname, int cloudIndex);
    boolean endTurn (String playerNickname);
    boolean activateCharacterCard (String playerNickname, CharacterID characterID);
    boolean applyCharacterCardEffect (String playerNickname, int islandIndex);
    boolean setClanCharacter (String playerNickname, Clan clan);
    boolean applyCharacterCardEffect (String playerNickname, int islandIndex, Map<Clan, Integer> students1, Map<Clan, Integer> students2);

}

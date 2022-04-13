package it.polimi.ingsw;

import it.polimi.ingsw.charactercards.CharacterID;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.player.Card;

import java.util.Map;

public interface GameInterface {

    void addPlayer (String nickname) throws NicknameNotAvailableException, WrongGamePhaseException;
    void chosenCard (String playerNickname, Card card) throws NonExistingPlayerException, WrongPlayerException, WrongGamePhaseException, NotValidMoveException;
    void moveStudentToChamber (String playerNickname, Clan clan) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, WrongTurnPhaseException, NotValidMoveException;
    void moveStudentToIsland (String playerNickname, Clan clan, int islandIndex) throws WrongTurnPhaseException, NotValidMoveException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException;
    void moveMotherNature (String playerNickname, int islandIndex) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, WrongTurnPhaseException, NotValidMoveException;
    void chosenCloud (String playerNickname, int cloudIndex) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, WrongTurnPhaseException, NotValidMoveException;
    void endTurn (String playerNickname) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, WrongTurnPhaseException;
    boolean activateCharacterCard (String playerNickname, CharacterID characterID);
    boolean applyCharacterCardEffect (String playerNickname, int islandIndex);
    boolean setClanCharacter (String playerNickname, Clan clan);
    boolean applyCharacterCardEffect (String playerNickname, int islandIndex, Map<Clan, Integer> students1, Map<Clan, Integer> students2);

}

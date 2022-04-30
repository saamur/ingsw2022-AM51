package it.polimi.ingsw.model;

import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.player.Card;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GameInterface extends Serializable {

    void addPlayer (String nickname) throws NicknameNotAvailableException, WrongGamePhaseException;
    void chosenCard (String playerNickname, Card card) throws NonExistingPlayerException, WrongPlayerException, WrongGamePhaseException, NotValidMoveException;
    void moveStudentToChamber (String playerNickname, Clan clan) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, WrongTurnPhaseException, NotValidMoveException;
    void moveStudentToIsland (String playerNickname, Clan clan, int islandIndex) throws WrongTurnPhaseException, NotValidMoveException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException;
    void moveMotherNature (String playerNickname, int islandIndex) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, WrongTurnPhaseException, NotValidMoveException;
    void chosenCloud (String playerNickname, int cloudIndex) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, WrongTurnPhaseException, NotValidMoveException;
    void endTurn (String playerNickname) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, WrongTurnPhaseException;
    void activateCharacterCard (String playerNickname, CharacterID characterID) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException;
    void applyCharacterCardEffect (String playerNickname, int islandIndex) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, NotValidMoveException;
    void setClanCharacter (String playerNickname, Clan clan) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException;
    void applyCharacterCardEffect (String playerNickname, int islandIndex, Map<Clan, Integer> students1, Map<Clan, Integer> students2) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException;
    GameState getGameState();
    List<String> getPlayersNicknames();
    boolean isExpertModeEnabled();
    void addListeners(PropertyChangeListener listener);

}

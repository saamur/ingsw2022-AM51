package it.polimi.ingsw.model;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.player.Card;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The GameInterface interface contains all the necessary methods to play that, when called,
 * bring a game from one valid state to another valid state
 *
 */
public interface GameInterface extends Serializable {

    /**
     * The method addPlayer adds a Player to the Game if there isn't already another with the same nickname
     * @param nickname  the nickname that will be assigned to the new Player
     * @throws WrongGamePhaseException          when it is called not during initialisation
     * @throws NicknameNotAvailableException    when the given nickname is already used by another Player
     */
    void addPlayer (String nickname) throws NicknameNotAvailableException, WrongGamePhaseException;

    /**
     * The method sets currCard of the currentPlayer to card if the game phase is correct,
     * the choice is valid and playerNickname is the nickname of the current Player.
     * If every Player has chosen a Card the method calls createOrderActionPhase and initActionPhase
     * @param playerNickname    the nickname of the Player that called this method
     * @param card              the chosen Card
     * @throws WrongGamePhaseException      when it is called not during planning
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException        when the card choice is not valid
     */
    void chosenCard (String playerNickname, Card card) throws NonExistingPlayerException, WrongPlayerException, WrongGamePhaseException, NotValidMoveException;

    /**
     * The method moveStudentToChamber moves a student of the specified Clan from the Hall to the Chamber of the current Player
     * @param playerNickname    the nickname of the Player that requested this move
     * @param clan              the Clan of the student to move
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws WrongTurnPhaseException      when it is called not during the student moving phase
     * @throws NotValidMoveException        when there is no student of the given clan in the hall of the current Player
     *                                      or the chamber has already the maximum number of students of the given clan
     */
    void moveStudentToChamber (String playerNickname, Clan clan) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, WrongTurnPhaseException, NotValidMoveException;

    /**
     * The method moveStudentToChamber moves a student of the specified Clan from the Hall of the current Player
     * to the Island with the specified index
     * @param playerNickname    the nickname of the Player that requested this move
     * @param clan              the Clan of the student to move
     * @param islandIndex       the index of the Island on which to move the student
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws NotValidIndexException       when there is no Island with the given index
     * @throws WrongTurnPhaseException      when it is called not during the student moving phase
     * @throws NotValidMoveException        when there is no student of the given clan in the hall of the current Player
     */
    void moveStudentToIsland (String playerNickname, Clan clan, int islandIndex) throws WrongTurnPhaseException, NotValidMoveException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException;

    /**
     * The method moveMotherNature moves Mother Nature on the Island with the specified index,
     * calls checkInfluence and updates the state of the Turn.
     * If it's the last round it calls endTurn
     * @param playerNickname    the nickname of the Player that requested this move
     * @param islandIndex       the index of the Island on which to move Mother Nature
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws WrongTurnPhaseException      when it is called not during the student moving phase
     * @throws NotValidIndexException       when there is no Island with the given index
     * @throws NotValidMoveException        when the Island with the given index is too distant to be reached by Mother Nature
     */
    void moveMotherNature (String playerNickname, int islandIndex) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, WrongTurnPhaseException, NotValidMoveException;

    /**
     * The method chosenCloud, if the choice is valid, adds the students on the Cloud with the given index
     * to the Hall of the current Player
     * @param playerNickname    the nickname of the Player that requested this move
     * @param cloudIndex        the index of the chosen Cloud
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws NotValidIndexException       when there is no Cloud with the given index
     * @throws WrongTurnPhaseException      when it is called not during the cloud choosing phase
     * @throws NotValidMoveException        when the Cloud with the given index has already been picked
     */
    void chosenCloud (String playerNickname, int cloudIndex) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, WrongTurnPhaseException, NotValidMoveException;

    /**
     * The method endTurn calls InitRound if the current Player is the last one of this round phase,
     * otherwise it instantiates a new Turn for the next Player
     * @param playerNickname    the nickname of the Player that requested this move
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws WrongTurnPhaseException      when it is called not during the end turn phase
     */
    void endTurn (String playerNickname) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, WrongTurnPhaseException;

    /**
     * The method activateCharacterCard, if the move is valid,
     * activates the CharacterCard with that CharacterID for the current turn
     * and performs any potential initial effect
     * @param playerNickname    the nickname of the Player that requested this move
     * @param characterID       the CharacterID of the CharacterCard to activate
     * @throws ExpertModeNotEnabledException    when it is called in a not expert game
     * @throws WrongGamePhaseException          when it is called not during the action phase
     * @throws NonExistingPlayerException       when there is no Player with the given nickname
     * @throws WrongPlayerException             when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException            when the selected character is not in this game or it is not available,
     *                                          another character card has been activated in this turn or the Player
     *                                          does not have enough coins to pay for it
     */
    void activateCharacterCard (String playerNickname, CharacterID characterID) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException;

    /**
     * The method applyCharacterEffect applies the effect of the CharacterCard currently active
     * on the Island with the specified index
     * @param playerNickname    the nickname of the Player that requested this move
     * @param islandIndex       the index of the Island on which the effect has to be applied
     * @throws ExpertModeNotEnabledException    when it is called in a not expert game
     * @throws WrongGamePhaseException          when it is called not during the action phase
     * @throws NonExistingPlayerException       when there is no Player with the given nickname
     * @throws WrongPlayerException             when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException            when there is no activated character card, its effect has already been
     *                                          applied or the move is not valid
     * @throws NotValidIndexException           when there is no Island with the given index
     */
    void applyCharacterCardEffect (String playerNickname, int islandIndex) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, NotValidMoveException;

    /**
     * The method setClanCharacter calls the method setCharacterClan on the current Turn, setting a Clan variable
     * necessary for some CharacterCards to perform their effects
     * @param playerNickname    the nickname of the Player that requested this move
     * @param clan              the Clan chosen by the Player
     * @throws ExpertModeNotEnabledException    when it is called in a not expert game
     * @throws WrongGamePhaseException          when it is called not during the action phase
     * @throws NonExistingPlayerException       when there is no Player with the given nickname
     * @throws WrongPlayerException             when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException            when there is no activated character card, or the Clan for the effect
     *                                          of the character card has already been set
     */
    void setClanCharacter (String playerNickname, Clan clan) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException;

    /**
     * The method applyCharacterEffect applies the effect of the CharacterCard currently active with the given parameters
     * @param playerNickname    the nickname of the Player that requested this move
     * @param islandIndex       the index of the destination Island for the students moved (if necessary)
     * @param students1         the students to move to the destination (if necessary)
     * @param students2         the students to move from the destination, in case of an exchange of students (if necessary)
     * @throws ExpertModeNotEnabledException    when it is called in a not expert game
     * @throws WrongGamePhaseException          when it is called not during the action phase
     * @throws NonExistingPlayerException       when there is no Player with the given nickname
     * @throws WrongPlayerException             when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException            when there is no activated character card, its effect has already been
     *                                          applied or the move is not valid
     */
    void applyCharacterCardEffect (String playerNickname, int islandIndex, Map<Clan, Integer> students1, Map<Clan, Integer> students2) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException;

    GameState getGameState();

    TurnState getTurnState();

    int getNumOfPlayers();

    List<String> getPlayersNicknames();

    String getNicknameCurrPlayer();

    boolean isExpertModeEnabled();

    boolean isLastRound();

    CharacterID getActivatedCharacterCard();

    boolean isActivatedCharacterCardPunctualEffectApplied();

    GameData getGameData();

    void setListeners(PropertyChangeListener listener);

    void removeListeners();

}

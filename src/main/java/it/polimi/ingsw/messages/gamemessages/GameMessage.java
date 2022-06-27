package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;

/**
 * The GameMessage interface contains the method that performs a move on the game, and it is implemented
 * by all game related messages
 *
 */
public interface GameMessage extends Message {

    /**
     * The method performMove, calls the method that corresponds to the message
     * @param nickname      of the player who performs the move
     * @param game          the game on which to perform the move
     * @return              the answer to be sent to the client
     * @throws WrongGamePhaseException          when the action is not valid in this game phase
     * @throws ExpertModeNotEnabledException    when one tries to perform an expert action on a non-expert game
     * @throws WrongPlayerException             when a player tries to make a move when it is not their turn
     * @throws NonExistingPlayerException       when there is no player with the given nickname in the game
     * @throws NotValidMoveException            when the move is not valid according to the game rules
     * @throws NotValidIndexException           when one tries to perform an action giving an ndex of on an island or a cloud that does not exist
     * @throws WrongTurnPhaseException          when the action is not valid in this turn phase
     */
    Message performMove (String nickname, GameInterface game) throws WrongGamePhaseException, ExpertModeNotEnabledException, WrongPlayerException, NonExistingPlayerException, NotValidMoveException, NotValidIndexException, WrongTurnPhaseException;

}

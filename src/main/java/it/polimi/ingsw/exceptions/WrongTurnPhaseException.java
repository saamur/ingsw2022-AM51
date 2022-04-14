package it.polimi.ingsw.exceptions;

/**
 * The WrongTurnPhaseException is thrown when a move is invoked in the wrong turn phase
 *
 */
public class WrongTurnPhaseException extends Exception {

    public WrongTurnPhaseException(String message) {
        super(message);
    }

}

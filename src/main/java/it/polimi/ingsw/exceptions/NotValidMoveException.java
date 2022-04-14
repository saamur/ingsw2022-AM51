package it.polimi.ingsw.exceptions;

/**
 * The NotValidMoveException is thrown when the current player tries to make any move not legal
 * accordingly with the game rules
 *
 */
public class NotValidMoveException extends Exception {

    public NotValidMoveException(String message) {
        super(message);
    }

}

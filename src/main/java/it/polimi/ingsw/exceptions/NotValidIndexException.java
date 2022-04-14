package it.polimi.ingsw.exceptions;

/**
 * The NotValidIndexException is thrown when an incorrect index is provided when trying to make a move
 *
 */
public class NotValidIndexException extends Exception {

    public NotValidIndexException(String message) {
        super(message);
    }

}

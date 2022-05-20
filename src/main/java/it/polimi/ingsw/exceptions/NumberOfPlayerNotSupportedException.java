package it.polimi.ingsw.exceptions;

/**
 * The NumberOfPlayerNotSupportedException is thrown when trying to create a Game with a number of player not supported
 *
 */
public class NumberOfPlayerNotSupportedException extends Exception {

    public NumberOfPlayerNotSupportedException (String message) {
        super(message);
    }

}

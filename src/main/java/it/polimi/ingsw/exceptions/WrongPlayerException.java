package it.polimi.ingsw.exceptions;

/**
 * The WrongPlayerException is thrown when any player except the current one tries to make a move
 *
 */
public class WrongPlayerException extends Exception {

    public WrongPlayerException(String message) {
        super(message);
    }

}

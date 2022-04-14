package it.polimi.ingsw.exceptions;

/**
 * The NonExistingPlayerException is thrown when a move with a nickname not owned by any player in the game is invoked
 *
 */
public class NonExistingPlayerException extends Exception {

    public NonExistingPlayerException(String message) {
        super(message);
    }

}

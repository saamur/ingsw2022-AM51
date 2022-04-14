package it.polimi.ingsw.exceptions;

/**
 * The NicknameNotAvailableException is thrown when trying to add a Player with a nickname already used by another one
 *
 */
public class NicknameNotAvailableException extends Exception {

    public NicknameNotAvailableException(String message) {
        super(message);
    }

}

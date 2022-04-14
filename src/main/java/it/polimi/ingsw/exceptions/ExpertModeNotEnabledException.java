package it.polimi.ingsw.exceptions;

/**
 * The ExpertModeNotEnabledException is thrown when trying to access to expert mode functionalities in a non-expert game
 *
 */
public class ExpertModeNotEnabledException extends Exception {

    public ExpertModeNotEnabledException(String message) {
        super(message);
    }

}

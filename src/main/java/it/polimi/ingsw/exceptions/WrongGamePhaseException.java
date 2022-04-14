package it.polimi.ingsw.exceptions;

/**
 * The WrongGamePhaseException is thrown when a move is invoked in the wrong game phase
 *
 */
public class WrongGamePhaseException extends Exception {

    public WrongGamePhaseException(String message) {
        super(message);
    }
}

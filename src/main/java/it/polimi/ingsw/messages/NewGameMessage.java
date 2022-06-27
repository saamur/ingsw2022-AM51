package it.polimi.ingsw.messages;

/**
 * The NewGameMessage record models the message for asking the server to create a new game with the given parameters
 * @param numOfPlayers  the number of the players
 * @param expertMode    flag for the expert mode of the game
 */

public record NewGameMessage(int numOfPlayers, boolean expertMode) implements Message {

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "NewGameMessage{" +
                "numOfPlayers=" + numOfPlayers +
                ", expertMode=" + expertMode +
                '}';
    }

}

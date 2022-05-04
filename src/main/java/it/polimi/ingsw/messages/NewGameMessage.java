package it.polimi.ingsw.messages;

/**
 * GameInitializationMessage receives the message to initialize the game
 * @param numOfPlayers number of the players
 * @param expertMode flag for the expert mode of the game
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

package it.polimi.ingsw.messages;

/**
 * GameInitializationMessage receives the message to initialize the game
 * @param numOfPlayers number of players in the game
 * @param expertMode flag for the expert mode of the game
 */

public record GameInitializationMessage(int numOfPlayers, boolean expertMode) implements Message {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "GameInitializationMessage{" +
                "numOfPlayers=" + numOfPlayers +
                ", expertMode=" + expertMode +
                '}';
    }

}

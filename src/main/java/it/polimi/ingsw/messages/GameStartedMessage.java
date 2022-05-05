package it.polimi.ingsw.messages;

/**
 * GameStartedMessage receives the message notifying the start of the game
 */

public record GameStartedMessage() implements Message {

    @Override
    public String getMessage(){
        return toString();
    }

    @Override
    public String toString() {
        return "GameStartedMessage{}";
    }
}

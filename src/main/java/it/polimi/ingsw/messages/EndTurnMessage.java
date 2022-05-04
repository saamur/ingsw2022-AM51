package it.polimi.ingsw.messages;

/**
 * EndTurnMessage gets the message that notify the end of a player's turn
 */
public record EndTurnMessage() implements Message {
    @Override
    public String getMessage(){
        return toString();
    }

    @Override
    public String toString() {
        return "EndTurnMessage{}";
    }
}

package it.polimi.ingsw.messages;

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

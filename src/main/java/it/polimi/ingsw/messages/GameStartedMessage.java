package it.polimi.ingsw.messages;

public record GameStartedMessage() implements Message {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "GameStartedMessage{}";
    }
}

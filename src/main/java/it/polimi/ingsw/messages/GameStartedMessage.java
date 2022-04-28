package it.polimi.ingsw.messages;

public record GameStartedMessage() implements Message{
    @Override
    public String getMessage(){
        return this.toString();
    }
}

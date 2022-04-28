package it.polimi.ingsw.messages;

public record AddPlayerMessage(int gameID) implements Message{

    @Override
    public String getMessage(){
        return this.toString();
    }
}

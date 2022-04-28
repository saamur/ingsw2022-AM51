package it.polimi.ingsw.messages;

public record GameInitializationMessage(int numOfPlayers, boolean expertMode) implements Message{

    @Override
    public String getMessage(){
        return this.toString();
    }
}

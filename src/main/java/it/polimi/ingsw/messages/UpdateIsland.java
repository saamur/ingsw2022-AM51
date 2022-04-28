package it.polimi.ingsw.messages;

public record UpdateIsland(int modifiedIslandIndex) implements Message{
    @Override
    public String getMessage(){
        return this.toString();
    }
}

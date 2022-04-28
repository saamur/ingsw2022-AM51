package it.polimi.ingsw.messages;

public record ChosenCloudMessage(int cloudIndex) implements Message{
    @Override
    public String getMessage(){
        return this.toString();
    }
}

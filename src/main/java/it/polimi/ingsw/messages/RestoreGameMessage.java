package it.polimi.ingsw.messages;

public record RestoreGameMessage(String fileName) implements Message {

    @Override
    public String getMessage() {
        return toString();
    }

}

package it.polimi.ingsw.messages;

public record ErrorMessage(String error) implements Message {
    @Override
    public String getMessage() {
        return error;
    }
}

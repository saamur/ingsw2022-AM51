package it.polimi.ingsw.messages;

public record GenericMessage(String message) implements Message {

    @Override
    public String getMessage() {
        return message;
    }

}

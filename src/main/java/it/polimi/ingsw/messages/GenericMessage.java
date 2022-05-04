package it.polimi.ingsw.messages;

/**
 * GenericMessage receives a generic message that cannot be categorized in the previous categories
 * @param message
 */

public record GenericMessage(String message) implements Message {

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GenericMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}

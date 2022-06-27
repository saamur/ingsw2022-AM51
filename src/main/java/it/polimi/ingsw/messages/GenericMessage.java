package it.polimi.ingsw.messages;

/**
 * The GenericMessage class models a generic message that cannot be categorized in the other categories
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

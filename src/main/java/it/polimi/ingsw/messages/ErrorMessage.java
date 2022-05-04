package it.polimi.ingsw.messages;

/**
 * gets an error message
 * @param error specifies the error
 */
public record ErrorMessage(String error) implements Message {
    @Override
    public String getMessage() {
        return error;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "error='" + error + '\'' +
                '}';
    }
}

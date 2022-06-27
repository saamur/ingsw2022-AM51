package it.polimi.ingsw.messages;

/**
 * The ErrorMessage record models an error message sent from the server to the clients
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

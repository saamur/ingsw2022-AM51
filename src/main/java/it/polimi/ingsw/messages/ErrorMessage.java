package it.polimi.ingsw.messages;

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

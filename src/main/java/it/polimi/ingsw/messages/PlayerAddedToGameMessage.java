package it.polimi.ingsw.messages;

public record PlayerAddedToGameMessage(String message) implements Message {

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PlayerAddedToGameMessage{" +
                "message='" + message + '\'' +
                '}';
    }

}

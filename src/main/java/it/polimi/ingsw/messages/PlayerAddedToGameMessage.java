package it.polimi.ingsw.messages;

/**
 * PlayerAddedToGameMessage the message that confirms that a player has been added to the game
 * @param message the message to show the user
 */

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

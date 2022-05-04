package it.polimi.ingsw.messages;

/**
 * PlayerDisconnectedMessage receives a message that notify that a player is disconnected
 * @param disconnectedPlayerNickname nickname of the disconnected player
 */

public record PlayerDisconnectedMessage(String disconnectedPlayerNickname) implements Message {

    @Override
    public String getMessage() {
        return disconnectedPlayerNickname + " has disconnected";
    }

    @Override
    public String toString() {
        return "PlayerDisconnectedMessage{" +
                "disconnectedPlayerNickname='" + disconnectedPlayerNickname + '\'' +
                '}';
    }
}

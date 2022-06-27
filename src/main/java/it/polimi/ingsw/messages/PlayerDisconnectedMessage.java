package it.polimi.ingsw.messages;

/**
 * The PlayerDisconnectedMessage record models the message that notifies that a player has disconnected
 * @param disconnectedPlayerNickname    nickname of the disconnected player
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

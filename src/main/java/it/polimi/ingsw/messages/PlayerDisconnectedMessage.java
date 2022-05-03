package it.polimi.ingsw.messages;

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

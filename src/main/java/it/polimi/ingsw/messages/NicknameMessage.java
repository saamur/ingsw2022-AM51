package it.polimi.ingsw.messages;

/**
 * The NicknameMessage record is sent by the client to the server and contains the nickname chosen by the player
 * @param nickname nickname chosen by the player
 */

public record NicknameMessage(String nickname) implements Message {

    @Override
    public String getMessage() {
        return nickname;
    }

    @Override
    public String toString() {
        return "NicknameMessage{" +
                "nickname='" + nickname + '\'' +
                '}';
    }

}

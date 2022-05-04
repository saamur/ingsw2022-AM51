package it.polimi.ingsw.messages;

/**
 * NicknameMessage receive the nickname of the player
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

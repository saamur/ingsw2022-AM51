package it.polimi.ingsw.messages;

public record NicknameAcceptedMessage(String nickname) implements Message {

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "NicknameAcceptedMessage{" +
                "nickname='" + nickname + '\'' +
                '}';
    }

}

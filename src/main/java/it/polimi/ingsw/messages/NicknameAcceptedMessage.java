package it.polimi.ingsw.messages;

/**
 * NicknameAcceptedMessage is the message sent by the Server to a client when the nickname chosen by the client has been accepted
 */
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

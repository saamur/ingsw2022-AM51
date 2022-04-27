package it.polimi.ingsw.messages;

public record NicknameMessage(String nickname) implements Message {

    @Override
    public String getMessage() {
        return nickname;
    }

}

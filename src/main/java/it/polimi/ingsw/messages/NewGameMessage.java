package it.polimi.ingsw.messages;

public record NewGameMessage(int numOfPlayers, boolean expertMode) implements Message {

    @Override
    public String getMessage() {
        return toString();
    }

}

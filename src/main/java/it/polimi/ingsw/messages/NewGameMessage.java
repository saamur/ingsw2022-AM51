package it.polimi.ingsw.messages;

public record NewGameMessage(int numOfPlayers, boolean expertMode) implements Message {

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "NewGameMessage{" +
                "numOfPlayers=" + numOfPlayers +
                ", expertMode=" + expertMode +
                '}';
    }
}

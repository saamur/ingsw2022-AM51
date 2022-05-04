package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GameData;

public record UpdateGameInfo(GameData gameData) implements UpdateMessage{
    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "UpdateGameInfo{" +
                "gameData=" + gameData +
                '}';
    }
}

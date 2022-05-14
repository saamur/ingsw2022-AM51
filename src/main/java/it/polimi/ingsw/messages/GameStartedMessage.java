package it.polimi.ingsw.messages;

import it.polimi.ingsw.client.modeldata.GameData;

/**
 * GameStartedMessage receives the message notifying the start of the game
 */

public record GameStartedMessage(GameData gameData) implements Message {

    @Override
    public String getMessage(){
        return toString();
    }

    @Override
    public String toString() {
        return "GameStartedMessage{" +
                "gameData=" + gameData +
                '}';
    }

}

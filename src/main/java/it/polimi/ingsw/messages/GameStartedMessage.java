package it.polimi.ingsw.messages;

import it.polimi.ingsw.client.modeldata.GameData;

/**
 * The GameStartedMessage record is the message that notifies the start of the game. It contains a GameData object
 * that contains all the data of the played Game
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

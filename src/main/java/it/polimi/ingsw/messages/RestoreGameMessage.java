package it.polimi.ingsw.messages;

import it.polimi.ingsw.controller.gamerecords.SavedGameData;

/**
 * RestoreGameMessage receives a message to restore a previously saved game
 * @param savedGameData data of the game to restore
 */

public record RestoreGameMessage(SavedGameData savedGameData) implements Message {

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "RestoreGameMessage{" +
                "savedGameData=" + savedGameData +
                '}';
    }

}

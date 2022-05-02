package it.polimi.ingsw.messages;

import it.polimi.ingsw.controller.SavedGameData;

public record RestoreGameMessage(SavedGameData savedGameData) implements Message {

    @Override
    public String getMessage() {
        return toString();
    }

}

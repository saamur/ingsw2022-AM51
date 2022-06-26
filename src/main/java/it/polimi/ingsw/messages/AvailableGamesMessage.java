package it.polimi.ingsw.messages;

import it.polimi.ingsw.controller.gamerecords.OpeningNewGameData;
import it.polimi.ingsw.controller.gamerecords.OpeningRestoredGameData;
import it.polimi.ingsw.controller.gamerecords.SavedGameData;

import java.util.List;

public record AvailableGamesMessage(List<OpeningNewGameData> openingNewGameDataList,
                                    List<OpeningRestoredGameData> openingRestoredGameDataList,
                                    List<SavedGameData> savedGameData) implements Message {
    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "AvailableGamesMessage{" +
                "openingNewGameDataList=" + openingNewGameDataList +
                ", openingRestoredGameDataList=" + openingRestoredGameDataList +
                ", savedGameData=" + savedGameData +
                '}';
    }

}

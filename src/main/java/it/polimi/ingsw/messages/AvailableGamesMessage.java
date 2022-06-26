package it.polimi.ingsw.messages;

import it.polimi.ingsw.controller.gamerecords.OpeningNewGameData;
import it.polimi.ingsw.controller.gamerecords.OpeningRestoredGameData;
import it.polimi.ingsw.controller.gamerecords.SavedGameData;

import java.util.List;

/**
 * The AvaiableGamesMessage is the message sent by the server to communicate the different games that can be opened or joined
 */
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

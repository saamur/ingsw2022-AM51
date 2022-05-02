package it.polimi.ingsw.messages;

import it.polimi.ingsw.controller.OpeningNewGameData;
import it.polimi.ingsw.controller.OpeningRestoredGameData;
import it.polimi.ingsw.controller.SavedGameData;

import java.io.Serializable;
import java.util.List;

public record AvailableGamesMessage(List<OpeningNewGameData> openingNewGameDataList,
                                    List<OpeningRestoredGameData> openingRestoredGameDataList,
                                    List<SavedGameData> savedGameData) implements Serializable {
}

package it.polimi.ingsw.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record OpeningRestoredGameData(int numOfPlayers,
                                      boolean expertMode,
                                      LocalDateTime localDateTime,
                                      List<String> nicknamesAlreadyJoined,
                                      List<String> missingNicknames) implements Serializable {
}

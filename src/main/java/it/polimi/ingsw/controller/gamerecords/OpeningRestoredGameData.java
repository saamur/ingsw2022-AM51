package it.polimi.ingsw.controller.gamerecords;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The OpeningRestoredGameData class is a record that contains all the data that represent an opening restored game
 *
 */
public record OpeningRestoredGameData(int id,
                                      int numOfPlayers,
                                      boolean expertMode,
                                      LocalDateTime localDateTime,
                                      List<String> nicknamesAlreadyJoined,
                                      List<String> missingNicknames) implements Serializable {
}
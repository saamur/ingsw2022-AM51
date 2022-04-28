package it.polimi.ingsw.controller;

import java.time.LocalDateTime;
import java.util.List;

public record SavedGameData(String fileName,
                            int numOfPlayers,
                            boolean expertMode,
                            LocalDateTime localDateTime,
                            List<String> nicknames) {
}

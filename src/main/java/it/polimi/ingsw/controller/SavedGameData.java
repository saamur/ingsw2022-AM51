package it.polimi.ingsw.controller;

import java.time.LocalDateTime;
import java.util.List;

public record SavedGameData(int numOfPlayers, boolean expertMode, LocalDateTime localDateTime, String fileName, List<String> nicknames) {
}

package it.polimi.ingsw.controller;

import java.io.Serializable;
import java.util.List;

public record OpeningNewGameData(int numOfPlayers,
                                 boolean expertMode,
                                 List<String> nicknames) implements Serializable {
}

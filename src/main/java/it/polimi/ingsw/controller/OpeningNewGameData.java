package it.polimi.ingsw.controller;

import java.io.Serializable;
import java.util.List;

/**
 * OpeningNewGameData class is a record that contains all the data that represent an opening new game
 *
 */
public record OpeningNewGameData(int id,
                                 int numOfPlayers,
                                 boolean expertMode,
                                 List<String> nicknames) implements Serializable {
}

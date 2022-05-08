package it.polimi.ingsw.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SavedGameData class is a record that contains all the data that represent a saved game
 *
 */
public record SavedGameData(String fileName,
                            int numOfPlayers,
                            boolean expertMode,
                            LocalDateTime localDateTime,
                            List<String> nicknames) implements Serializable {

    /**
     * savedGameDataParser static method creates a SavedGameData object given a standardized String
     * containing the necessary data.
     * The String returned by toString() of the returned objects will be equals to the given parameter
     * @param line  the String with the needed data
     * @return      a SavedGameData object with the data in line
     */
    public static SavedGameData savedGameDataParser (String line) {

        String[] words = line.split(" ");

        String fileName = words[0];
        int numOfPlayers = Integer.parseInt(words[1]);
        boolean expertMode = Boolean.parseBoolean(words[2]);
        LocalDateTime localDateTime = LocalDateTime.parse(words[3]);
        List<String> nicknames = new ArrayList<>(Arrays.asList(words).subList(4, 4 + numOfPlayers));

        return new SavedGameData(fileName, numOfPlayers, expertMode, localDateTime, nicknames);

    }

    @Override
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fileName).append(" ");
        stringBuilder.append(numOfPlayers).append(" ");
        stringBuilder.append(expertMode).append(" ");
        stringBuilder.append(localDateTime).append(" ");
        for (String nickname : nicknames)
            stringBuilder.append(nickname).append(" ");

        return stringBuilder.toString();
    }

}

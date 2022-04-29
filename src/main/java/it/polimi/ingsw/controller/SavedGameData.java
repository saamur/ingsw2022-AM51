package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameInterface;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record SavedGameData(String fileName,
                            int numOfPlayers,
                            boolean expertMode,
                            LocalDateTime localDateTime,
                            List<String> nicknames) {

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

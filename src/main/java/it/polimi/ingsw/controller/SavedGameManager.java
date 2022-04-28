package it.polimi.ingsw.controller;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavedGameManager {

    private static final String SAVED_GAMES_DIRECTORY = "SavedGames";
    private static final String SAVED_GAMES_INDEX = "index.txt";

    static {
        File index = new File(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX);
        index.mkdirs();
        try {
            index.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized static List<SavedGameData> getSavedGameList () {

        List<SavedGameData> savedGameDataList = new ArrayList<>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line;
        try {

            while ((line = br.readLine()) != null)
                if (!line.equals(""))
                    savedGameDataList.add(savedGameDataParser(line));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedGameDataList;

    }

    private static SavedGameData savedGameDataParser (String line) {

        String[] words = line.split(" ");

        String fileName = words[0];
        int numOfPlayers = Integer.parseInt(words[1]);
        boolean expertMode = Boolean.parseBoolean(words[2]);
        LocalDateTime localDateTime = LocalDateTime.parse(words[3]);
        List<String> nicknames = new ArrayList<>();
        nicknames.addAll(Arrays.asList(words).subList(4, 4 + numOfPlayers));

        return new SavedGameData(fileName, numOfPlayers, expertMode, localDateTime, nicknames);

    }

}

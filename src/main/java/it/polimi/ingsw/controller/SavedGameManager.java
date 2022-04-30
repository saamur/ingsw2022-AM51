package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameInterface;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SavedGameManager {

    private static final String SAVED_GAMES_DIRECTORY = "SavedGames";
    private static final String SAVED_GAMES_INDEX = "index.txt";
    private static final String SAVED_GAME_BASE_NAME = "savedGame";
    private static final String SAVED_GAME_EXTENSION = ".game";

    static {
        File directory = new File(SAVED_GAMES_DIRECTORY);
        directory.mkdir();
        File index = new File(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX);
        try {
            index.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static List<SavedGameData> getSavedGameList () {

        List<SavedGameData> savedGameDataList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX));
            String line;
            while ((line = br.readLine()) != null)
                if (!line.equals(""))
                    savedGameDataList.add(SavedGameData.savedGameDataParser(line));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedGameDataList;

    }

    public synchronized static void saveGame (GameInterface game) throws IOException {

        List<String> alreadySavedGamesFileNames = getSavedGameList().stream().map(SavedGameData::fileName).toList();

        int i = 0;
        while (alreadySavedGamesFileNames.contains(SAVED_GAME_BASE_NAME + i + SAVED_GAME_EXTENSION))
            i++;

        try (ObjectOutputStream out = new ObjectOutputStream(new
                BufferedOutputStream(new FileOutputStream(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAME_BASE_NAME + i + SAVED_GAME_EXTENSION)))) {

            out.writeObject(game);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX, true));

        bw.write(new SavedGameData(
                SAVED_GAME_BASE_NAME + i + SAVED_GAME_EXTENSION,
                game.getPlayersNicknames().size(),
                game.isExpertModeEnabled(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                game.getPlayersNicknames()) + "\n");
        bw.close();

    }

    public synchronized static GameInterface restoreGame (String fileName) throws IOException {

        ObjectInputStream in = null;
        GameInterface restoredGame = null;

        File fileGame = new File(SAVED_GAMES_DIRECTORY + "/" + fileName);
        if(!fileGame.exists())
            throw new FileNotFoundException("There is no saved game with this file name");

        try {
            in = new ObjectInputStream(new
                    BufferedInputStream(new FileInputStream(fileGame)));

            restoredGame = (GameInterface) in.readObject();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }

        fileGame.delete();

        File file = new File(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX);
        List<String> out = Files.lines(file.toPath())
                .filter(line -> !SavedGameData.savedGameDataParser(line).fileName().equals(fileName))
                .collect(Collectors.toList());
        Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        return restoredGame;

    }

}

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

/**
 * SavedGameManager class contains all static methods needed to save and restore games
 *
 */
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

    /**
     * method getSavedGameList creates a list with all SavedGameData that represent the games currently saved
     * @return  a list with a SavedGameData for every saved game that describes it
     */
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

    /**
     * method saveGame saves the given game on file
     * @param game  the game to be saved
     * @throws IOException  if an error occurs during the saving process
     */
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

    /**
     * method restoreGame restores a game saved on file
     * @param fileName  the name of the file where the needed game is saved
     * @return          the needed game after it was restored
     * @throws IOException  if an error occurs during the saving process
     */
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
            if (in != null)
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

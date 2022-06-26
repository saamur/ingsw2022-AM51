package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.gamerecords.SavedGameData;
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
 * The SavedGameManager class contains all static methods needed to save and restore games
 *
 */
public class SavedGameManager {

    private static final String SAVED_GAMES_DIRECTORY = "SavedGames";
    private static final String SAVED_RUNNING_GAMES_DIRECTORY = "SavedRunningGames";
    private static final String SAVED_GAMES_INDEX = "index.txt";
    private static final String SAVED_GAME_BASE_NAME = "savedGame";
    private static final String SAVED_RUNNING_GAME_BASE_NAME = "savedRunningGame";
    private static final String SAVED_GAME_EXTENSION = ".game";

    static {

        File savedGameDirectory = new File(SAVED_GAMES_DIRECTORY);
        savedGameDirectory.mkdir();
        File index = new File(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX);
        try {
            index.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File savedRunningGamesDirectory = new File(SAVED_RUNNING_GAMES_DIRECTORY);
        savedRunningGamesDirectory.mkdir();

        File[] savedRunningGames = savedRunningGamesDirectory.listFiles();

        for (File f : savedRunningGames) {

            GameInterface restoredGame = null;

            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                restoredGame = (GameInterface) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            f.delete();

            restoredGame.removeListeners();

            saveGame(restoredGame);

        }

    }

    /**
     * The method getSavedGameList creates a list with all SavedGameData that represent the games currently saved
     * @return  a list with a SavedGameData for every saved game that describes it
     */
    public synchronized static List<SavedGameData> getSavedGameList () {

        List<SavedGameData> savedGameDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX))) {
            String line;
            while ((line = br.readLine()) != null)
                if (!"".equals(line))
                    savedGameDataList.add(SavedGameData.savedGameDataParser(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedGameDataList;

    }

    /**
     * The method saveGame saves the given game on a file
     * @param game  the game to be saved
     */
    public synchronized static void saveGame (GameInterface game) {

        List<String> alreadySavedGamesFileNames = getSavedGameList().stream().map(SavedGameData::fileName).toList();

        int i = 0;
        while (alreadySavedGamesFileNames.contains(SAVED_GAME_BASE_NAME + i + SAVED_GAME_EXTENSION))
            i++;

        try (ObjectOutputStream out = new ObjectOutputStream(new
                BufferedOutputStream(new FileOutputStream(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAME_BASE_NAME + i + SAVED_GAME_EXTENSION)))) {

            out.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX, true))) {

            bw.write(new SavedGameData(
                    SAVED_GAME_BASE_NAME + i + SAVED_GAME_EXTENSION,
                    game.getPlayersNicknames().size(),
                    game.isExpertModeEnabled(),
                    LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                    game.getPlayersNicknames()) + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * The method restoreGame restores a game saved on a file
     * @param fileName  the name of the file where the needed game is saved
     * @return          the needed game after it was restored
     * @throws FileNotFoundException  if there is no file with the given name
     */
    public synchronized static GameInterface restoreGame (String fileName) throws FileNotFoundException {

        GameInterface restoredGame = null;

        File fileGame = new File(SAVED_GAMES_DIRECTORY + "/" + fileName);
        if(!fileGame.exists())
            throw new FileNotFoundException("There is no saved game with this file name.");

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileGame)))) {
            restoredGame = (GameInterface) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        fileGame.delete();

        File index = new File(SAVED_GAMES_DIRECTORY + "/" + SAVED_GAMES_INDEX);
        try {
            List<String> out = Files.lines(index.toPath())
                    .filter(line -> !SavedGameData.savedGameDataParser(line).fileName().equals(fileName))
                    .collect(Collectors.toList());
            Files.write(index.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return restoredGame;

    }

    /**
     * The method saveRunningGame saves the given running game on a file
     * @param game          the game to be saved
     * @param controllerID  the univocal ID of the controller bound to the game
     */
    public static void saveRunningGame (GameInterface game, int controllerID) {

        try (ObjectOutputStream out = new ObjectOutputStream(new
                BufferedOutputStream(new FileOutputStream(SAVED_RUNNING_GAMES_DIRECTORY + "/" + SAVED_RUNNING_GAME_BASE_NAME + controllerID + SAVED_GAME_EXTENSION)))) {

            out.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * The method removeSavedRunningGame deletes, if it exists, the file containing the game bound to the controller with the given controllerID
     * @param controllerID  the univocal ID of the controller bound to the game
     */
    public static void removeSavedRunningGame (int controllerID) {
        File file = new File (SAVED_RUNNING_GAMES_DIRECTORY + "/" + SAVED_RUNNING_GAME_BASE_NAME + controllerID + SAVED_GAME_EXTENSION);
        if (file.exists())
            file.delete();
    }

}

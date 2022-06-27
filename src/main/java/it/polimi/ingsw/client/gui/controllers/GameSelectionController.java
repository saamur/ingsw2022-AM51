package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.controller.gamerecords.OpeningNewGameData;
import it.polimi.ingsw.controller.gamerecords.OpeningRestoredGameData;
import it.polimi.ingsw.controller.gamerecords.SavedGameData;
import it.polimi.ingsw.messages.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * GameSelectionController displays the scene where the client can choose which game to play on GUI
 */
public class GameSelectionController extends PageController implements Initializable {

    @FXML private Label noGameSelectedLabel;
    @FXML private TableView<OpeningNewGameData> newGames;
    @FXML private TableColumn<OpeningNewGameData, Integer> idNewGame;
    @FXML private TableColumn<OpeningNewGameData, Integer> numPlayersNewGame;
    @FXML private TableColumn<OpeningNewGameData, String> modeNewGame;
    @FXML private Label labelNewGames;

    @FXML private TableView<SavedGameData> savedGames;
    @FXML private TableColumn<SavedGameData, String> fileName;
    @FXML private TableColumn<SavedGameData, Integer> numPlayersSavedGame;
    @FXML private TableColumn<SavedGameData, String> modeSavedGame;
    @FXML private Label labelSavedGames;

    @FXML private TableView<OpeningRestoredGameData> restoredGames;
    @FXML private TableColumn<OpeningRestoredGameData, Integer> idRestoredGame;
    @FXML private TableColumn<OpeningRestoredGameData, Integer> numPlayersRestoredGame;
    @FXML private TableColumn<OpeningRestoredGameData, String> modeRestoredGame;
    @FXML private Label labelRestoredGames;

    @FXML private Button chooseNewGame;
    @FXML private Button openSavedGame;
    @FXML private Button chooseRestoredGame;

    @FXML private TextField nicknameTextField;
    @FXML private Label chosenNicknameLabel;
    @FXML private String nickname;

    @FXML private CheckBox expertMode;
    @FXML private Spinner<Integer> numPlayers;

    private AvailableGamesMessage availableGamesMessage = new AvailableGamesMessage(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        noGameSelectedLabel.setText("You have not selected a game");
        noGameSelectedLabel.setTextFill(Color.web("#a11515"));
        noGameSelectedLabel.setVisible(false);

        idNewGame.setCellValueFactory(
                game -> new SimpleIntegerProperty(game.getValue().id()).asObject()
        );
        numPlayersNewGame.setCellValueFactory(
                game -> new SimpleIntegerProperty(game.getValue().numOfPlayers()).asObject()
        );
        modeNewGame.setCellValueFactory(gameData -> {
                    ObservableBooleanValue expert = new SimpleBooleanProperty(gameData.getValue().expertMode());
                    return Bindings.when(expert)
                            .then("expert")
                            .otherwise("easy");
                }
        );

        newGames.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    String playersInfo = "";
                    if(newValue != null) {
                        playersInfo = "Players: " + newValue.nicknames().get(0);
                        for (int i = 1; i < newValue.nicknames().size(); i++) {
                            playersInfo = playersInfo.concat(", " + newValue.nicknames().get(i));
                        }
                    }
                    String finalPlayersInfo = playersInfo;
                    Platform.runLater(() -> labelNewGames.setText(finalPlayersInfo));
                });

        fileName.setCellValueFactory(
                game -> new SimpleStringProperty(game.getValue().fileName())
        );
        numPlayersSavedGame.setCellValueFactory(
                game -> new SimpleIntegerProperty(game.getValue().numOfPlayers()).asObject()
        );
        modeSavedGame.setCellValueFactory(gameData -> {
                    ObservableBooleanValue expert = new SimpleBooleanProperty(gameData.getValue().expertMode());
                    return Bindings.when(expert)
                            .then("expert")
                            .otherwise("easy");
                }
        );
        savedGames.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    String playersInfo = "";
                    if(newValue != null) {
                        playersInfo = "Players: " + newValue.nicknames().get(0);
                        for (int i = 1; i < newValue.nicknames().size(); i++) {
                            playersInfo = playersInfo.concat(", " + newValue.nicknames().get(i));
                        }
                    }

                    labelSavedGames.setText(playersInfo);
                });


        idRestoredGame.setCellValueFactory(
                game -> new SimpleIntegerProperty(game.getValue().id()).asObject()
        );
        numPlayersRestoredGame.setCellValueFactory(
                game -> new SimpleIntegerProperty(game.getValue().numOfPlayers()).asObject()
        );
        modeRestoredGame.setCellValueFactory(gameData -> {
                    ObservableBooleanValue expert = new SimpleBooleanProperty(gameData.getValue().expertMode());
                    return Bindings.when(expert)
                            .then("expert")
                            .otherwise("easy");
                }
        );
        restoredGames.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    String playersInfo = "";
                    if(newValue != null) {
                        playersInfo = "Players who have already joined: " + newValue.nicknamesAlreadyJoined().get(0);
                        for (int i = 1; i < newValue.nicknamesAlreadyJoined().size(); i++) {
                            playersInfo = playersInfo.concat(", " + newValue.nicknamesAlreadyJoined().get(i));
                        }
                        if (newValue.missingNicknames().size() > 0) {
                            playersInfo = playersInfo.concat("\nPlayers missing: " + newValue.missingNicknames().get(0));
                            for (int i = 1; i < newValue.missingNicknames().size(); i++) {
                                playersInfo = playersInfo.concat(", " + newValue.missingNicknames().get(i));
                            }
                        } else {
                            playersInfo = playersInfo.concat("\nThere are no missing players");
                        }
                    }
                    labelRestoredGames.setText(playersInfo);
                });
        getGames();

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 3);
        valueFactory.setValue(2);
        numPlayers.setValueFactory(valueFactory);
    }

    /**
     * Sets the game's list from the availableGamesMessage and displays them in the relative tables
     * @see AvailableGamesMessage
     */
    public void getGames(){
        List<OpeningNewGameData> newGamesList = availableGamesMessage.openingNewGameDataList();
        List<SavedGameData> savedGamesList = availableGamesMessage.savedGameData();
        List<OpeningRestoredGameData> restoredGamesList = availableGamesMessage.openingRestoredGameDataList();

        newGames.getItems().setAll(newGamesList);
        savedGames.getItems().setAll(savedGamesList);
        restoredGames.getItems().setAll(restoredGamesList);
    }

    /**
     * It's called when a game is selected and according to the type of game, it opens the game or restores an old one
     * @see ActionEvent
     */
    public void enterGame(ActionEvent event) {
        noGameSelectedLabel.setVisible(false);
        Object source = event.getSource();
        if(nickname != null) {
            if (chooseNewGame.equals(source)) { //potrei fare una switch ma solo facendo getSource.getId()

                OpeningNewGameData selectedGame = newGames.getSelectionModel().getSelectedItem();
                if(selectedGame == null){
                    noGameSelectedLabel.setVisible(true);
                } else {
                    sendMessage(new AddPlayerMessage(selectedGame.id()));
                }

            } else if (openSavedGame.equals(source)) {

                SavedGameData selectedGame = savedGames.getSelectionModel().getSelectedItem();
                if(selectedGame == null){
                    noGameSelectedLabel.setVisible(true);
                } else {
                    sendMessage(new RestoreGameMessage(selectedGame));
                }

            } else if (chooseRestoredGame.equals(source)) {

                OpeningRestoredGameData selectedGame = restoredGames.getSelectionModel().getSelectedItem();
                if(selectedGame == null){
                    noGameSelectedLabel.setVisible(true);
                } else {
                    sendMessage(new AddPlayerMessage(selectedGame.id()));
                }
            }

        }
    }

    /**
     * Accepts the input from the nicknameTextField if it is not empty and sends the chosen nickname to the ServerHandler
     * @see ActionEvent
     */
    public void createNickname(ActionEvent event){
        if(nickname == null) {
            if (nicknameTextField.getText().isEmpty()) {
                chosenNicknameLabel.setTextFill(Color.web("#a11515"));
                chosenNicknameLabel.setText("Please choose a username");
            } else {
                sendMessage(new NicknameMessage(nicknameTextField.getText()));
            }
        } else {
            errorNickname();
        }

    }

    /**
     * Method is called after the "create Game" button is clicked, it sends the message to the ServerHandler that a new game has been opened
     * @see NewGameMessage
     * @see ActionEvent
     */
    public void createNewGame(ActionEvent event){
        if(nickname != null){
            boolean expert = expertMode.isSelected();
            int players = numPlayers.getValue();
            sendMessage(new NewGameMessage(players, expert));
        } else {
            errorNickname();
        }

    }

    /**
     * This method is called when there is a problem regarding the nickname, it displays the error message next to the nicknameTextField
     * The problems can be: <ul>
     *     <li>"no nickname inserted": the user has tried opening a game or creating a new one without inserting a nickname first</li>
     *     <li>"The nickname has already been inserted": the user has tried to insert a username after having chosen one already</li>
     * </ul>
     */
    public void errorNickname(){
        if(nickname == null){
            chosenNicknameLabel.setTextFill(Color.web("#a11515"));
            chosenNicknameLabel.setText("No nickname inserted");
        } else {
            chosenNicknameLabel.setTextFill(Color.web("#a11515"));
            chosenNicknameLabel.setText("The nickname has already been inserted");
            nicknameTextField.setText(nickname);
        }
    }

    public void setAvailableGameMessage(AvailableGamesMessage availableGamesMessage){
        this.availableGamesMessage = availableGamesMessage;
        getGames();
    }

    /**
     * After the Server has confirmed that the nickname can be chosen, a message saying "Nickname chosen!" will appear
     * @param nickname nickname chosen by the user
     */
    public void setNickname(String nickname){
        if(nickname != null) {
            this.nickname = nickname;
            Platform.runLater(() -> {
                chosenNicknameLabel.setTextFill(Color.web("#31ac4c"));
                chosenNicknameLabel.setText("Nickname chosen!");
            });

        }
    }

    @Override
    public void handleErrorMessage(String error) {
        if(error.contains("Nickname")){
            Platform.runLater(() -> {
                chosenNicknameLabel.setTextFill(Color.web("#a11515"));
                chosenNicknameLabel.setText(error);
            });
        } else {
            super.handleErrorMessage(error);
        }
    }
}
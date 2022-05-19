package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.modeldata.ServerHandler;
import it.polimi.ingsw.controller.OpeningNewGameData;
import it.polimi.ingsw.controller.OpeningRestoredGameData;
import it.polimi.ingsw.controller.SavedGameData;
import it.polimi.ingsw.messages.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


//TODO delete sout
public class WaitingRoomController extends PageController implements Initializable {

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

    List<OpeningNewGameData> newGamesList;
    List<SavedGameData> savedGamesList;
    List<OpeningRestoredGameData> restoredGamesList;
    private AvailableGamesMessage availableGamesMessage = new AvailableGamesMessage(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

                    String playersInfo = "Players: " + newValue.nicknames().get(0);
                    for(int i=1; i<newValue.nicknames().size(); i++){
                        playersInfo = playersInfo.concat(", " + newValue.nicknames().get(i));
                    }

                    labelNewGames.setText(playersInfo);
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

                    String playersInfo = "Players: " + newValue.nicknames().get(0);
                    for(int i=1; i<newValue.nicknames().size(); i++){
                        playersInfo = playersInfo.concat(", " + newValue.nicknames().get(i));
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

                    String playersInfo = "Players who have already joined: " + newValue.nicknamesAlreadyJoined().get(0);
                    for(int i=1; i<newValue.nicknamesAlreadyJoined().size(); i++){
                        playersInfo = playersInfo.concat(", " + newValue.nicknamesAlreadyJoined().get(i));
                    }
                    if(newValue.missingNicknames().size() > 0){
                        playersInfo = playersInfo.concat("\nPlayers missing: " + newValue.missingNicknames().get(0));
                        for(int i=1; i<newValue.missingNicknames().size(); i++){
                            playersInfo = playersInfo.concat(", " + newValue.missingNicknames().get(i));
                        }
                    } else {
                        playersInfo = playersInfo.concat("\nThere are no missing players");
                    }
                    labelRestoredGames.setText(playersInfo);
                });
        getGames();

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 3);
        valueFactory.setValue(2);
        numPlayers.setValueFactory(valueFactory);
    }


    public void getGames(){
        newGamesList = availableGamesMessage.openingNewGameDataList();
        savedGamesList = availableGamesMessage.savedGameData();
        restoredGamesList = availableGamesMessage.openingRestoredGameDataList();

        newGames.getItems().setAll(newGamesList);
        savedGames.getItems().setAll(savedGamesList);
        restoredGames.getItems().setAll(restoredGamesList);
    }

    @FXML
    public void enterGame(ActionEvent event) {
        Object source = event.getSource();
        if(nickname != null) {
            if (chooseNewGame.equals(source)) { //potrei fare una switch ma solo facendo getSource.getId()

                OpeningNewGameData selectedGame = newGames.getSelectionModel().getSelectedItem();
                System.out.println(selectedGame); //TODO delete
                sendMessage(new AddPlayerMessage(selectedGame.id()));

            } else if (openSavedGame.equals(source)) {

                SavedGameData selectedGame = savedGames.getSelectionModel().getSelectedItem();
                System.out.println(selectedGame); //TODO delete
                sendMessage(new RestoreGameMessage(selectedGame));

            } else if (chooseRestoredGame.equals(source)) {

                OpeningRestoredGameData selectedGame = restoredGames.getSelectionModel().getSelectedItem();
                System.out.println(selectedGame); //TODO delete
                sendMessage(new AddPlayerMessage(selectedGame.id()));

            }
        }
    }

    public void createNickname(ActionEvent event){
        if(nickname == null) {
            if (nicknameTextField.getText().isEmpty()) {
                System.out.println("non c'era nickname");
                chosenNicknameLabel.setTextFill(Color.web("#a11515"));
                chosenNicknameLabel.setText("Please choose a username");
            } else {
                System.out.println(nicknameTextField.getText());
                sendMessage(new NicknameMessage(nicknameTextField.getText()));
                System.out.println(new NicknameMessage((nicknameTextField.getText())));
                System.out.println(gui);
            }
        } else {
            //TODO add change nickname?
            errorNickname();
        }

    }

    public void createNewGame(ActionEvent event){
        if(nickname != null){
            boolean expert = expertMode.isSelected();
            int players = numPlayers.getValue();
            sendMessage(new NewGameMessage(players, expert));
            System.out.println("Game created: " + new NewGameMessage(players, expert));
        } else {
            errorNickname();
        }

    }

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

        }
    }
}

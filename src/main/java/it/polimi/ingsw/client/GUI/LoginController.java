package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.controller.OpeningNewGameData;
import it.polimi.ingsw.controller.OpeningRestoredGameData;
import it.polimi.ingsw.controller.SavedGameData;
import it.polimi.ingsw.messages.*;
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
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
        private GUI gui;

        @FXML private TableView<OpeningNewGameData> newGames; //FIXME other class, maybe newGameData
        @FXML private TableColumn<OpeningNewGameData, Integer> idNewGame;
        @FXML private TableColumn<OpeningNewGameData, Integer> numPlayersNewGame;
        @FXML private TableColumn<OpeningNewGameData, String> modeNewGame;
        @FXML private TableColumn<OpeningNewGameData, Integer> activePlayersNewGame;
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
        @FXML private TableColumn<OpeningRestoredGameData, Integer> activePlayersRestoredGame;
        @FXML private Label labelRestoredGames;

        @FXML private Button chooseNewGame;
        @FXML private Button openSavedGame;
        @FXML private Button chooseRestoredGame;

        @FXML private TextField nickname;
        @FXML private Label chosenNicknameLabel;

        @FXML private CheckBox expertMode;
        @FXML private Spinner<Integer> numPlayers;

        List<OpeningNewGameData> newGamesList;
        List<SavedGameData> savedGamesList;
        List<OpeningRestoredGameData> restoredGamesList;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            getGames();
            //newGamesList = getNewGames();
            //Per i record non bastano i getters https://stackoverflow.com/questions/70175587/how-do-you-use-a-javafx-tableview-with-java-records
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
            //activePlayersNewGame.setCellValueFactory(new PropertyValueFactory<OpeningNewGameData, Integer>("numPlayersActive"));
            newGames.getItems().setAll(newGamesList);

            //TODO print nicknames maybe under the table
            newGames.getSelectionModel().selectedItemProperty()
                    .addListener(new ChangeListener<OpeningNewGameData>() {

                        @Override
                        public void changed(
                                ObservableValue<? extends OpeningNewGameData> observable,
                                OpeningNewGameData oldValue, OpeningNewGameData newValue ) {

                            String playersInfo = "Players: " + newValue.nicknames().get(0);
                            for(int i=1; i<newValue.nicknames().size(); i++){
                                playersInfo = playersInfo.concat(", " + newValue.nicknames().get(i));
                            }

                            labelNewGames.setText(playersInfo);
                        }
                    });

            fileName.setCellValueFactory(
                    game -> new SimpleStringProperty(game.getValue().fileName())
            ); //Qui devo mettere i nomi degli attributi di Game (devono avere i getter)
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
            savedGames.getItems().setAll(savedGamesList);

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
                    .addListener(new ChangeListener<OpeningRestoredGameData>() {

                        @Override
                        public void changed(
                                ObservableValue<? extends OpeningRestoredGameData> observable,
                                OpeningRestoredGameData oldValue, OpeningRestoredGameData newValue ) {

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
                        }
                    });
            restoredGames.getItems().setAll(restoredGamesList);

            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 3);
            valueFactory.setValue(2);
            numPlayers.setValueFactory(valueFactory);
        }

        /*
        Solution for how to convert boolean to String
        nameCol.setCellValueFactory(features -> {
  var firstName = features.getValue().firstNameProperty();
  var lastName  = features.getValue().lastNameProperty();
  var nickname  = features.getValue().nicknameProperty();

  return Bindings.when(nickname.isEmpty())
      .then(firstName.concat(" ").concat(lastName))
      .otherwise(nickname);
});
         */

        /*public List<Game> createList(){ //method is a placeholder
            //AvailableGamesMessage
            AvailableGameMessage message;



            //PlaceHolderCode
            List<GameData> list = new ArrayList<>();
            GameData game1 = new GameData("Samu", 3, "expert", 2);
            GameData game2 = new GameData("Fede", 2, "expert", 1);

            list.add(game1);
            list.add(game2);
            return list;
        }*/

        public void getGames(){
            /*newGamesList = availableGamesMessage.openingNewGameDataList();
            savedGamesList = availableGamesMessage.savedGameData();
            restoredGamesList = availableGamesMessage.openingRestoredGameDataList();*/
            List<String> nicknames = new ArrayList<>();
            nicknames.add("Fede");
            nicknames.add("Samu");

            newGamesList = new ArrayList<>();
            savedGamesList = new ArrayList<>();
            restoredGamesList = new ArrayList<>();

            OpeningNewGameData newGame = new OpeningNewGameData(1, 2, true, nicknames);
            newGamesList.add(newGame);
            savedGamesList.add(new SavedGameData("file1", 1, true, LocalDateTime.now(), nicknames));
            List<String> missingNicknames = new ArrayList<>();
            missingNicknames.add("Giulia");
            restoredGamesList.add(new OpeningRestoredGameData(1, 2, true, LocalDateTime.now(), nicknames, missingNicknames));

        }

        @FXML
        public void enterGame(ActionEvent event) {
            Object source = event.getSource();
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

            /*
            Magari si potrebbe creare una classe EnterGameData da cui tutti ereditano
            TableView<Record> tableView = null; //records non ereditano altrimenti si potrebbe fare così
            Object source = event.getSource();
            if (chooseNewGame.equals(source)) { //potrei fare una switch ma solo facendo getSource.getId()
                tableView = newGames;
            } else if (openSavedGame.equals(source)) {
                tableView = savedGames;
            } else if (chooseOpenedGame.equals(source)) {
                tableView = openedGames;
            }
            if (tableView != null) {
                GameData selectedGame = tableView.getSelectionModel().getSelectedItem(); //prende tutti i valori anche quelli non salvati nelle colonne
                System.out.println(selectedGame);
            }*/


        }

        public void createNickname(ActionEvent event){
            if(nickname== null){
                System.out.println("non c'era nickname");
            }
            System.out.println(nickname.getText());

            sendMessage(new NicknameMessage(nickname.getText()));

            chosenNicknameLabel.setText("Nickname chosen!");
        }

        public void createNewGame(ActionEvent event){
            boolean expert = false;
            if(expertMode.isSelected())
                expert = true;
            int players = numPlayers.getValue();
            sendMessage(new NewGameMessage(players, expert));
            System.out.println("Game created: " + new NewGameMessage(players, expert));
            //TODO needs testing, se ne creo uno qualcun altro lo vede?
        }

        public void setGui(GUI gui){
            this.gui = gui;
        }

        public void sendMessage(Message message){
            gui.getListeners().firePropertyChange("message", null, message);
        }

}

package it.polimi.ingsw.client.GUI.controllers;


import it.polimi.ingsw.client.modeldata.CharacterCardData;
import it.polimi.ingsw.client.modeldata.GameData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static it.polimi.ingsw.constants.ConstantsGUI.*;

//FIXME rapporto schermata
public class GameBoardController extends PageController implements Initializable {

    @FXML private Label info;
    @FXML
    AnchorPane characterCards;
    @FXML
    private ImageView character1;
    @FXML
    private ImageView character2;
    @FXML
    private ImageView character3;

    private List<ImageView> characters;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        characters = Arrays.asList(character1, character2, character3); //TODO spero che non lo visualizzi
    }


    //Fixme togliere una plancia se ci sono solo due giocatori
    //FIXME aggiungere charactercards
    //FIXME aggiungere nuvole

    public void selectIslands(MouseEvent click){
        gui.setCurrScene(ISLANDS);
    }

    public void selectDeck(MouseEvent event){
        gui.setCurrScene(DECK);
    }

    public void selectSchoolBoards(MouseEvent event){
        gui.setCurrScene(SCHOOLBOARDS);
    }

    public void selectCharacterCards(MouseEvent mouseEvent) {
        gui.setCurrScene(CHARACTERS);
    }

    public void viewClouds(MouseEvent mouseEvent) {
        gui.setCurrScene(CLOUDS);
    }


    public void setGameData(GameData gameData){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(gameData.isExpertModeEnabled()) {
                    CharacterCardData[] characterCards = gameData.getCharacterCardData();
                    for(int i=0; i<3; i++){
                        characters.get(i).setImage(new Image(getClass().getResource(CHARACTERS_IMAGES.get(characterCards[i].characterID())).toExternalForm()));
                    }
                } else {
                    characterCards.setVisible(false); //FIXME SAREBBE MEGLIO ELIMINARLO COMPLETAMENTE
                    characterCards.setDisable(true);
                }
            }
        });
    }


    public void setInfoLabel(String text){
        info.setText(text);
    }

}

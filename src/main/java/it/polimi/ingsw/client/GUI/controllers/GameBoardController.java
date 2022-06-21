package it.polimi.ingsw.client.GUI.controllers;


import it.polimi.ingsw.client.modeldata.CharacterCardData;
import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.model.charactercards.CharacterID;
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

    CharacterID activatedCharacter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        characters = Arrays.asList(character1, character2, character3); //TODO spero che non lo visualizzi
    }

    /**
     * This method is called when a Mouse click is done on the islands' anchor pane
     * @param click
     */
    public void selectIslands(MouseEvent click){
        gui.setCurrScene(ISLANDS);
    }

    /**
     * This method is called when a Mouse click is done on the cards' anchor pane
     * @param event
     */
    public void selectDeck(MouseEvent event){
        gui.setCurrScene(DECK);
    }

    /**
     * This method is called when a Mouse click is done on the school boards' anchor pane
     * @param event
     */
    public void selectSchoolBoards(MouseEvent event){
        gui.setCurrScene(SCHOOLBOARDS);
    }

    /**
     * This method is called when a Mouse click is done on the characters' anchor pane
     * @param mouseEvent
     */
    public void selectCharacterCards(MouseEvent mouseEvent) {
        if(activatedCharacter == CharacterID.THIEF || activatedCharacter == CharacterID.MUSHROOMPICKER)
            gui.setCurrScene(ACTIVATEEFFECT);
        else
            gui.setCurrScene(CHARACTERS);
    }

    /**
     * This method is called when a Mouse click is done on the clouds' anchor pane
     * @param mouseEvent
     */
    public void viewClouds(MouseEvent mouseEvent) {
        gui.setCurrScene(CLOUDS);
    }

    /**
     * This method creates the gameBoard scene according to the game data
     * @param gameData
     */
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

    /**
     * Sets the info Label's text to the text inserted as a parameter
     * @param text instructions describing what is happening in the game
     * @see Label
     */
    public void setInfoLabel(String text){
        info.setText(text); //FIXME do only if currPlayer.equals(nickname)
    }

    public void setActivatedCharacter(CharacterID character){
        this.activatedCharacter = character;
    }

}

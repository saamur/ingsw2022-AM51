package it.polimi.ingsw.client.GUI.controllers;

import it.polimi.ingsw.client.modeldata.CharacterCardData;
import it.polimi.ingsw.constants.ConstantsGUI;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.messages.gamemessages.ActivateCharacterCardMessage;
import it.polimi.ingsw.messages.gamemessages.ApplyCharacterCardEffectMessage2;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterID;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.ConstantsGUI.*;
//TODO JavaDocs

/**
 * This class is used to manage the character's Scene.
 */
public class CharactersController extends PageController implements Initializable {

    @FXML private Label instructionLabel;
    @FXML private AnchorPane anchorCharacter0;
    @FXML private ImageView character0;
    @FXML private ImageView piece1character0;
    @FXML private ImageView piece2character0;
    @FXML private ImageView piece3character0;
    @FXML private ImageView piece4character0;
    @FXML private ImageView piece5character0;
    @FXML private ImageView piece6character0;
    @FXML private Label descriptionLabel0;
    @FXML private Label costLabel0;
    List<ImageView> piecesCharacter0;
    @FXML private Button buttonCharacter0;
    @FXML Label activatedByLabel0;

    @FXML private AnchorPane anchorCharacter1;
    @FXML private ImageView character1;
    @FXML private ImageView piece1character1;
    @FXML private ImageView piece2character1;
    @FXML private ImageView piece3character1;
    @FXML private ImageView piece4character1;
    @FXML private ImageView piece5character1;
    @FXML private ImageView piece6character1;
    @FXML private Label descriptionLabel1;
    @FXML private Label costLabel1;
    List<ImageView> piecesCharacter1;
    @FXML private Button buttonCharacter1;
    @FXML Label activatedByLabel1;

    @FXML private AnchorPane anchorCharacter2;
    @FXML private ImageView character2;
    @FXML private ImageView piece1character2;
    @FXML private ImageView piece2character2;
    @FXML private ImageView piece3character2;
    @FXML private ImageView piece4character2;
    @FXML private ImageView piece5character2;
    @FXML private ImageView piece6character2;
    @FXML private Label descriptionLabel2;
    @FXML private Label costLabel2;
    List<ImageView> piecesCharacter2;
    @FXML private Button buttonCharacter2;
    @FXML Label activatedByLabel2;

    private List<ImageView> characters;
    private List<List<ImageView>> charactersPieces;
    private List<Button> buttonsCharacters;
    private List<AnchorPane> anchors;
    private List<Label> descriptionLabels;
    private List<Label> costLabels;
    private List<Label> activatedByLabels;

    private int selectedCharacterIndex;

    private CharacterCardData[] availableCharacterCards;
    private CharacterID activatedCharacter;

    private String nickname;

    private Clan singleClanSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchors = new ArrayList<>(Arrays.asList(anchorCharacter0, anchorCharacter1, anchorCharacter2));
        piecesCharacter0 = new ArrayList<>(Arrays.asList(piece1character0, piece2character0, piece3character0, piece4character0, piece5character0, piece6character0));
        piecesCharacter1 = new ArrayList<>(Arrays.asList(piece1character1, piece2character1, piece3character1, piece4character1, piece5character1, piece6character1));
        piecesCharacter2 = new ArrayList<>(Arrays.asList(piece1character2, piece2character2, piece3character2, piece4character2, piece5character2, piece6character2));
        charactersPieces = new ArrayList<>(Arrays.asList(piecesCharacter0, piecesCharacter1, piecesCharacter2));
        characters = Arrays.asList(character0, character1, character2);
        for(List<ImageView> list : charactersPieces) {
            for (ImageView piece : list) {
                piece.setVisible(false);
                piece.setDisable(true);
                //Pane pane = new Pane(piece);

                piece.setOnMouseClicked( e -> {
                            if(singleClanSelected == null) {
                                piece.getParent().getStylesheets().add("/style.css");
                                piece.getParent().getStyleClass().add("selected-character-pieces");
                                singleClanSelected = Clan.valueOf(piece.getId());
                                applyEffect();
                            }
                        }

                );
            }
        }
        buttonsCharacters = new ArrayList<>(Arrays.asList(buttonCharacter0, buttonCharacter1, buttonCharacter2));
        for(Button button : buttonsCharacters){
            button.setVisible(false);
        }
        costLabels = new ArrayList<>(Arrays.asList(costLabel0, costLabel1, costLabel2));
        descriptionLabels = new ArrayList<>(Arrays.asList(descriptionLabel0, descriptionLabel1, descriptionLabel2));
        for(Label label : descriptionLabels){
            label.setVisible(false);
            label.setWrapText(true);
        }
        activatedByLabels = new ArrayList<>(Arrays.asList(activatedByLabel0, activatedByLabel1, activatedByLabel2));
        for(Label label : activatedByLabels){
            label.setVisible(false);
        }
        instructionLabel.setText("You can activate a Character by clicking on it");
    }

    /**
     * This method is called when a game starts or is reopened. It sets the images of the character cards available and their description
     * @param characterCards the CharacterCardData of the 3 avaible characterCards
     */
    public void setCharacterCards(CharacterCardData[] characterCards){
        availableCharacterCards = characterCards;
        for(int i=0; i<characterCards.length; i++){
            characters.get(i).setImage(new Image(getClass().getResource(CHARACTERS_IMAGES.get(characterCards[i].characterID())).toExternalForm()));
            characters.get(i).setId(characterCards[i].characterID().name());
            if(characterCards[i].characterID() == CharacterID.GRANDMA){
                for(int j=0; j<4; j++) { //TODO test GRANDMA
                    charactersPieces.get(i).get(j).setImage(new Image(getClass().getResource("/png/deny_island_icon.png").toExternalForm()));
                }
            }
            descriptionLabels.get(i).setText(GameConstants.getDescriptionCharacter(characterCards[i].characterID()));
            updateCharacterCard(characterCards[i]);
        }


    }

    /**
     * This method is called when a character card is clicked on. It shows the description of the CharacterCard and the button to activate it. It disables and renders not visible all of the other CharacterCards
     * @param mouseEvent
     */
    public void selectCharacter(MouseEvent mouseEvent) {
        for(ImageView character : characters){
            System.out.println("Ho selezionato: " + mouseEvent.getSource());
            if(character == mouseEvent.getSource()){
                buttonsCharacters.get(characters.indexOf(character)).setVisible(true);
                buttonsCharacters.get(characters.indexOf(character)).setDisable(false);
                descriptionLabels.get(characters.indexOf(character)).setVisible(true);
            } else {
                buttonsCharacters.get(characters.indexOf(character)).setVisible(false);
                buttonsCharacters.get(characters.indexOf(character)).setDisable(true);
                descriptionLabels.get(characters.indexOf(character)).setVisible(false);
            }
        }
    }


    public void activate(ActionEvent actionEvent) {
        System.out.println("Ho \"selezionato\":" + actionEvent.getSource());
        for(Button button : buttonsCharacters) {
            if (button == actionEvent.getSource()) {
                selectedCharacterIndex = buttonsCharacters.indexOf(button);
                sendMessage(new ActivateCharacterCardMessage(CharacterID.valueOf(characters.get(selectedCharacterIndex).getId())));
            }
        }
    }

    public void back(ActionEvent actionEvent) {
        gui.setCurrScene(GAMEBOARD);
    }

    public void updateCharacterCard(CharacterCardData characterCardData){
        int characterIndex = 4;
        for(ImageView character : characters){
            if(character.getId().equals(characterCardData.characterID().name())){
                characterIndex = characters.indexOf(character);
            }
        }
        if (characterIndex >= 0 && characterIndex < 3) {
            if(characterCardData.students() != null){
                int numOfStudents = 0;
                Map<Clan, Integer> students = new EnumMap<>(characterCardData.students());
                for (Clan clan : Clan.values()) {
                    while (students.get(clan) > 0) {
                        charactersPieces.get(characterIndex).get(numOfStudents).setImage(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(clan)).toExternalForm()));
                        charactersPieces.get(characterIndex).get(numOfStudents).setVisible(true);
                        charactersPieces.get(characterIndex).get(numOfStudents).setId(clan.name());
                        students.put(clan, students.get(clan) - 1);
                        numOfStudents++;
                    }
                }
            }
            int prohibitionCards = characterCardData.numProhibitionCards();
            while(prohibitionCards > 0){
                charactersPieces.get(characterIndex).get(prohibitionCards-1).setDisable(false);
                charactersPieces.get(characterIndex).get(prohibitionCards-1).setVisible(true);
                prohibitionCards--;
            }
            costLabels.get(characterIndex).setText("Cost: " + characterCardData.cost());
        }

    }

    public void setActivatedCharacter(CharacterID activatedCharacter, String nicknameCurrPlayer, boolean puntualEffectApplied){
        this.activatedCharacter = activatedCharacter;

        for(AnchorPane anchor : anchors)
            anchor.getStyleClass().removeIf(style -> style.equals("activated-character-card"));
        for(Label label : activatedByLabels)
            label.setText("");
        for(ImageView character : characters)
            for(ImageView piece : charactersPieces.get(characters.indexOf(character))) {
                piece.setDisable(true);
                piece.getParent().getStyleClass().removeIf( style -> style.equals("selected-character-pieces"));
            }

        if(activatedCharacter != null) {
            for(ImageView character : characters){
                if(character.getId().equals(activatedCharacter.name())){
                    anchors.get(characters.indexOf(character)).getStyleClass().add("activated-character-card");
                    activatedByLabels.get(characters.indexOf(character)).setText("Activated by: " + (nicknameCurrPlayer.equals(this.nickname)? "you" : nicknameCurrPlayer));
                    activatedByLabels.get(characters.indexOf(character)).setVisible(true);

                    if(nicknameCurrPlayer.equals(nickname) && !puntualEffectApplied){
                        switch(activatedCharacter){
                            case PRINCESS, MONK -> {
                                instructionLabel.setText("Choose one of the students from the "+ activatedCharacter.name() + " card");
                                for(ImageView piece : charactersPieces.get(characters.indexOf(character))){
                                    if(piece.isVisible())
                                        piece.setDisable(false);
                                }
                            }
                        }
                    }
                }
            }

        } else {
            instructionLabel.setText("You can activate a Character by clicking on it");
        }

    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void applyEffect(){
        Map<Clan, Integer> students = new EnumMap<>(Clan.class);
        if(singleClanSelected != null){
            for(Clan clan : Clan.values()) {
                if (clan == singleClanSelected)
                    students.put(clan, 1);
                else
                    students.put(clan, 0);
            }
        }
        switch(activatedCharacter){
            case PRINCESS ->
                sendMessage(new ApplyCharacterCardEffectMessage2(-1, students, null));
            case MONK -> {
                ((IslandsPageController) gui.getControllers().get(ISLANDS)).setCharacterMap(students);
                ((IslandsPageController) gui.getControllers().get(ISLANDS)).setActivatedCharacter(CharacterID.MONK);
                Platform.runLater(() -> gui.setCurrScene(ISLANDS));
            }
        }
        singleClanSelected = null;
    }
}

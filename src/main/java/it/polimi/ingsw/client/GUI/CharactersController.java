package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.modeldata.CharacterCardData;
import it.polimi.ingsw.constants.ConstantsGUI;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.messages.gamemessages.ActivateCharacterCardMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.ConstantsGUI.CHARACTERS_IMAGES;
import static it.polimi.ingsw.constants.ConstantsGUI.GAMEBOARD;

public class CharactersController extends PageController implements Initializable {

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
    }

    public void setCharacterCards(CharacterCardData[] characterCards){
        availableCharacterCards = characterCards;
        for(int i=0; i<3; i++){
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

    public void selectCharacter(MouseEvent mouseEvent) {
        for(AnchorPane anchor : anchors){
            System.out.println("Ho selezionato: " + mouseEvent.getSource());
            if(anchor == mouseEvent.getSource()/*anchor.getChildren().contains((Node) mouseEvent.getSource())*/){
                buttonsCharacters.get(anchors.indexOf(anchor)).setVisible(true);
                buttonsCharacters.get(anchors.indexOf(anchor)).setDisable(false);
                descriptionLabels.get(anchors.indexOf(anchor)).setVisible(true);
            } else {
                buttonsCharacters.get(anchors.indexOf(anchor)).setVisible(false);
                buttonsCharacters.get(anchors.indexOf(anchor)).setDisable(true);
                descriptionLabels.get(anchors.indexOf(anchor)).setVisible(false);
            }
        }
    }

    public void selectPiece(MouseEvent mouseEvent) {
        //TODO delete?
    }

    public void activate(ActionEvent actionEvent) {
        System.out.println("Ho \"selezionato\":" + actionEvent.getSource());
        for(Button button : buttonsCharacters) {
            if (button == actionEvent.getSource()) {
                selectedCharacterIndex = buttonsCharacters.indexOf(button);
                sendMessage(new ActivateCharacterCardMessage(CharacterID.valueOf(characters.get(selectedCharacterIndex).getId())));
            }
        }
        //FIXME una volta attivato si aprirà una pagina per molti dei character
    }

    public void back(ActionEvent actionEvent) {
        selectedCharacterIndex = 4;
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
                Map<Clan, Integer> students = characterCardData.students();
                for (Clan clan : Clan.values()) {
                    while (students.get(clan) > 0) {
                        System.out.println("Posiziono uno studente: "+ clan);
                        charactersPieces.get(characterIndex).get(numOfStudents).setImage(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(clan)).toExternalForm()));
                        charactersPieces.get(characterIndex).get(numOfStudents).setVisible(true);
                        charactersPieces.get(characterIndex).get(numOfStudents).setDisable(false);
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

    public void setActivatedCharacter(CharacterID activatedCharacter, String nicknameCurrPlayer){
        this.activatedCharacter = activatedCharacter;
        System.out.println("Ho settato activatedCharacter: " + activatedCharacter);
        if(activatedCharacter != null){
            System.out.println("Old style: " + anchors.get(selectedCharacterIndex).getStyleClass());
            for(ImageView character : characters){
                if(character.getId().equals(activatedCharacter.name())){
                    anchors.get(characters.indexOf(character)).getStyleClass().add("activated-character-card");
                    activatedByLabels.get(characters.indexOf(character)).setText("Activated by: " + (nicknameCurrPlayer.equals(this.nickname)? "you" : nicknameCurrPlayer));
                    activatedByLabels.get(characters.indexOf(character)).setVisible(true);
                }
            }
        } else { //if the activated character is null -> restore the style
            for(AnchorPane anchor : anchors) {
                anchor.getStyleClass().removeIf(style -> style.equals("activated-character-card"));
            }
            for(Label label : activatedByLabels){
                label.setText("");
            }
        }

    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }
}

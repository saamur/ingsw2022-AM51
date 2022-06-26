package it.polimi.ingsw.client.GUI.controllers;

import it.polimi.ingsw.client.modeldata.IslandData;
import it.polimi.ingsw.client.modeldata.IslandManagerData;
import it.polimi.ingsw.constants.GuiConstants;
import it.polimi.ingsw.messages.gamemessages.ApplyCharacterCardEffectMessage1;
import it.polimi.ingsw.messages.gamemessages.ApplyCharacterCardEffectMessage2;
import it.polimi.ingsw.messages.gamemessages.MoveMotherNatureMessage;
import it.polimi.ingsw.messages.gamemessages.MoveStudentToIslandMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.TowerColor;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.GuiConstants.*;
import static it.polimi.ingsw.model.Clan.*;

/**
 * IslandsPageController displays all of the Islands from the Game Board on GUI
 */
public class IslandsPageController extends PageController implements Initializable {

    private final String CLICKED_BUTTON = "-fx-opacity: 0.5";
    private Button chooseIsland;

    @FXML private Pane window;
    @FXML private Button moveMotherNature;
    private boolean enabledMoveMotherNature;
    private boolean enableCharacter;

    private List<AnchorPane> anchorIslands;
    private List<AnchorPane> tempAnchor;

    private final Map<AnchorPane, ImageView> prohibitionCards = new HashMap<>();
    private final Map<Clan, Map<AnchorPane, ImageView>> clanColors = new EnumMap<>(Clan.class);

    @FXML private AnchorPane anchorIsland0;
    @FXML private AnchorPane anchorIsland1;
    @FXML private AnchorPane anchorIsland2;
    @FXML private AnchorPane anchorIsland3;
    @FXML private AnchorPane anchorIsland4;
    @FXML private AnchorPane anchorIsland5;
    @FXML private AnchorPane anchorIsland6;
    @FXML private AnchorPane anchorIsland7;
    @FXML private AnchorPane anchorIsland8;
    @FXML private AnchorPane anchorIsland9;
    @FXML private AnchorPane anchorIsland10;
    @FXML private AnchorPane anchorIsland11;

    @FXML private ImageView prohibitionCard0;
    @FXML private ImageView prohibitionCard1;
    @FXML private ImageView prohibitionCard2;
    @FXML private ImageView prohibitionCard3;
    @FXML private ImageView prohibitionCard4;
    @FXML private ImageView prohibitionCard5;
    @FXML private ImageView prohibitionCard6;
    @FXML private ImageView prohibitionCard7;
    @FXML private ImageView prohibitionCard8;
    @FXML private ImageView prohibitionCard9;
    @FXML private ImageView prohibitionCard10;
    @FXML private ImageView prohibitionCard11;

    @FXML private ImageView pink0;
    @FXML private ImageView pink1;
    @FXML private ImageView pink2;
    @FXML private ImageView pink3;
    @FXML private ImageView pink4;
    @FXML private ImageView pink5;
    @FXML private ImageView pink6;
    @FXML private ImageView pink7;
    @FXML private ImageView pink8;
    @FXML private ImageView pink9;
    @FXML private ImageView pink10;
    @FXML private ImageView pink11;
    private final Map<AnchorPane, ImageView> pink = new HashMap<>();

    @FXML private ImageView green0;
    @FXML private ImageView green1;
    @FXML private ImageView green2;
    @FXML private ImageView green3;
    @FXML private ImageView green4;
    @FXML private ImageView green5;
    @FXML private ImageView green6;
    @FXML private ImageView green7;
    @FXML private ImageView green8;
    @FXML private ImageView green9;
    @FXML private ImageView green10;
    @FXML private ImageView green11;
    private final Map<AnchorPane, ImageView> green = new HashMap<>();

    @FXML private ImageView blue0;
    @FXML private ImageView blue1;
    @FXML private ImageView blue2;
    @FXML private ImageView blue3;
    @FXML private ImageView blue4;
    @FXML private ImageView blue5;
    @FXML private ImageView blue6;
    @FXML private ImageView blue7;
    @FXML private ImageView blue8;
    @FXML private ImageView blue9;
    @FXML private ImageView blue10;
    @FXML private ImageView blue11;
    private final Map<AnchorPane, ImageView> blue = new HashMap<>();

    @FXML private ImageView red0;
    @FXML private ImageView red1;
    @FXML private ImageView red2;
    @FXML private ImageView red3;
    @FXML private ImageView red4;
    @FXML private ImageView red5;
    @FXML private ImageView red6;
    @FXML private ImageView red7;
    @FXML private ImageView red8;
    @FXML private ImageView red9;
    @FXML private ImageView red10;
    @FXML private ImageView red11;
    private final Map<AnchorPane, ImageView> red = new HashMap<>();

    @FXML private ImageView yellow0;
    @FXML private ImageView yellow1;
    @FXML private ImageView yellow2;
    @FXML private ImageView yellow3;
    @FXML private ImageView yellow4;
    @FXML private ImageView yellow5;
    @FXML private ImageView yellow6;
    @FXML private ImageView yellow7;
    @FXML private ImageView yellow8;
    @FXML private ImageView yellow9;
    @FXML private ImageView yellow10;
    @FXML private ImageView yellow11;
    private final Map<AnchorPane, ImageView> yellow = new HashMap<>();

    @FXML private ImageView tower0;
    @FXML private ImageView tower1;
    @FXML private ImageView tower2;
    @FXML private ImageView tower3;
    @FXML private ImageView tower4;
    @FXML private ImageView tower5;
    @FXML private ImageView tower6;
    @FXML private ImageView tower7;
    @FXML private ImageView tower8;
    @FXML private ImageView tower9;
    @FXML private ImageView tower10;
    @FXML private ImageView tower11;
    private final Map< AnchorPane, ImageView> towers = new HashMap<>();

    @FXML private ImageView motherNature0;
    @FXML private ImageView motherNature1;
    @FXML private ImageView motherNature2;
    @FXML private ImageView motherNature3;
    @FXML private ImageView motherNature4;
    @FXML private ImageView motherNature5;
    @FXML private ImageView motherNature6;
    @FXML private ImageView motherNature7;
    @FXML private ImageView motherNature8;
    @FXML private ImageView motherNature9;
    @FXML private ImageView motherNature10;
    @FXML private ImageView motherNature11;
    private final Map<AnchorPane, ImageView> motherNature = new HashMap<>();

    @FXML private Button back;

    private int motherNaturePosition;
    private List<IslandData> modelIslands;

    private final ObjectProperty<Clan> droppedStudent = new SimpleObjectProperty<>(null);
    @FXML private ImageView droppedStudentImage;
    @FXML private Label droppedStudentLabel;
    @FXML private AnchorPane droppedStudentAnchor;
    @FXML private Label instructions;

    private String previousScene;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //islands = new ArrayList<>(Arrays.asList(island0, island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11));
        anchorIslands = new ArrayList<>(Arrays.asList(anchorIsland0, anchorIsland1, anchorIsland2, anchorIsland3, anchorIsland4, anchorIsland5, anchorIsland6, anchorIsland7, anchorIsland8, anchorIsland9, anchorIsland10, anchorIsland11));

        List<ImageView> tempProhibitionCards = new ArrayList<>(Arrays.asList(prohibitionCard0, prohibitionCard1, prohibitionCard2, prohibitionCard3, prohibitionCard4, prohibitionCard5, prohibitionCard6, prohibitionCard7, prohibitionCard8, prohibitionCard9, prohibitionCard10, prohibitionCard11));
        List<ImageView> tempTowers = new ArrayList<>(Arrays.asList(tower0, tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8, tower9, tower10, tower11));
        List<ImageView> tempPink = new ArrayList<>(Arrays.asList(pink0, pink1, pink2, pink3, pink4, pink5, pink6, pink7, pink8, pink9, pink10, pink11));
        List<ImageView> tempGreen = new ArrayList<>(Arrays.asList(green0, green1, green2, green3, green4, green5, green6, green7, green8, green9, green10, green11));
        List<ImageView> tempBlue = new ArrayList<>(Arrays.asList(blue0, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10, blue11));
        List<ImageView> tempRed = new ArrayList<>(Arrays.asList(red0, red1, red2, red3, red4, red5, red6, red7, red8, red9, red10, red11));
        List<ImageView> tempYellow = new ArrayList<>(Arrays.asList(yellow0, yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10, yellow11));
        List<ImageView> tempMotherNature = new ArrayList<>(Arrays.asList(motherNature0, motherNature1, motherNature2, motherNature3, motherNature4, motherNature5, motherNature6, motherNature7, motherNature8, motherNature9, motherNature10, motherNature11));

        for(int i=0; i<anchorIslands.size(); i++){
            prohibitionCards.put(anchorIslands.get(i), tempProhibitionCards.get(i));
            towers.put(anchorIslands.get(i), tempTowers.get(i));
            pink.put(anchorIslands.get(i), tempPink.get(i));
            green.put(anchorIslands.get(i), tempGreen.get(i));
            blue.put(anchorIslands.get(i), tempBlue.get(i));
            red.put(anchorIslands.get(i), tempRed.get(i));
            yellow.put(anchorIslands.get(i), tempYellow.get(i));
            motherNature.put(anchorIslands.get(i), tempMotherNature.get(i));
        }
        chooseIsland = new Button("Choose island");
        window.getChildren().add(chooseIsland);
        chooseIsland.setLayoutX(406);
        chooseIsland.setLayoutY(329);
        chooseIsland.setVisible(false);
        chooseIsland.setDisable(true);
        chooseIsland.setOnAction(e -> {
            enableCharacter = true;
            chooseIsland.setStyle(CLICKED_BUTTON);
        });

        clanColors.put(PIXIES, yellow);
        clanColors.put(UNICORNS, blue);
        clanColors.put(TOADS, green);
        clanColors.put(DRAGONS, red);
        clanColors.put(FAIRIES, pink);


        for(AnchorPane anchor : anchorIslands) {
            motherNature.get(anchor).setVisible(false);
            towers.get(anchor).setVisible(false);
            prohibitionCards.get(anchor).setVisible(false);
            anchor.getStylesheets().add("/style.css");
            anchor.getStyleClass().add("image-view-selection");
        }
        tempAnchor = new ArrayList<>(anchorIslands);

        makeDraggable(droppedStudentImage);

        droppedStudentAnchor.getStyleClass().removeIf(style -> style.equals("image-view-selection"));
        droppedStudentAnchor.setVisible(false);
        droppedStudentAnchor.setDisable(true);
        droppedStudentImage.setVisible(false);
        droppedStudentLabel.setVisible(false);
        instructions.setVisible(false);

        droppedStudent.addListener((observable, oldValue, newValue) -> {
                    if (observable.getValue() != null) {
                        System.out.println("Dovrebbe essere null: " + observable.getValue());
                        droppedStudentAnchor.getStyleClass().add("image-view-selection");
                        droppedStudentAnchor.setVisible(true);
                        droppedStudentAnchor.setDisable(false);
                        droppedStudentImage.setImage(new Image(getClass().getResource(GuiConstants.getImagePathStudent(newValue)).toExternalForm()));
                        droppedStudentImage.setVisible(true);
                        droppedStudentLabel.setVisible(true);
                        instructions.setVisible(true);
                        instructions.setText("Drag and drop the student on an Island");
                    } else {
                        droppedStudentAnchor.getStyleClass().removeIf(style -> style.equals("image-view-selection"));
                        droppedStudentAnchor.setVisible(false);
                        droppedStudentAnchor.setDisable(true);
                        droppedStudentImage.setVisible(false);
                        droppedStudentLabel.setVisible(false);
                    }
                }
        );

        moveMotherNature.setVisible(false);
        moveMotherNature.setDisable(true);
    }

    /**
     * This method changes the scene to the precedent one, usually the game board
     * @param event
     */
    public void back(ActionEvent event) {
        if(previousScene != null && !(gui.getTurnState() == TurnState.MOTHER_MOVING))
            gui.setCurrScene(previousScene);
        else
            gui.setCurrScene(GAMEBOARD);
        previousScene = null;
    }

    /**
     * This method is called when a mouse click is detected on an island. It has different effects.
     * Usually if an island is clicked it will open the corresponding singleIslandPage that describes all of the items contained on the island.
     * If the TurnState is MotherMoving and the "Move Mother Nature" button has been clicked, it will send a message to the ServerHandler saying the chosen Island to move Mother Nature to.
     * If a character has been activated, it sends different informations to the ServerHandler according to the information that is needed to apply the character effect
     * @param mouseEvent
     */
    public void selectIsland(MouseEvent mouseEvent) {
        for (int i = 0; i < tempAnchor.size(); i++) {
            if (tempAnchor.get(i).equals(mouseEvent.getSource())) {
                if(enabledMoveMotherNature) {
                    sendMessage(new MoveMotherNatureMessage(i));
                } else if (enableCharacter){
                    switch(activatedCharacter){
                        case HERALD, GRANDMA ->
                            sendMessage(new ApplyCharacterCardEffectMessage1(i));
                        case MONK -> {
                            sendMessage(new ApplyCharacterCardEffectMessage2(i, characterMap, null));
                            characterMap = null;
                        }
                    }
                    enableCharacter = false;
                    activatedCharacter = null;
                    chooseIsland.setVisible(false);
                    chooseIsland.setDisable(true);
                    boolean motherMoving = gui.getTurnState()==TurnState.MOTHER_MOVING;
                    String text = "";
                    if(motherMoving) {
                        text = "By clicking the button and then clicking on an Island\nyou will move Mother Nature there\nYou have " + currCard.getMaxStepsMotherNature() + " step" + (currCard.getMaxStepsMotherNature() > 1 ? "s" : "");
                        moveMotherNature.setVisible(true);
                    }
                    instructions.setVisible(motherMoving);
                    instructions.setText(text);
                }else{
                    String imagePath = null;
                    for (Node child : tempAnchor.get(i).getChildren()) {
                        if (child instanceof ImageView) {
                            if (child.getId().contains("island")) {
                                imagePath = ((ImageView) child).getImage().getUrl();
                            }
                        }
                    }
                    ((SingleIslandController) gui.getControllers().get(SINGLEISLAND)).setIsland(modelIslands.get(i), imagePath, i == motherNaturePosition);
                    gui.setCurrScene(SINGLEISLAND);
                }
            }
        }
    }

    /**
     * UpdateMotherPosition receives the current position of Mother Nature in the model and sets as visible only the corresponing image of mother nature, and as not visible all of the other ones
     * @param motherNaturePositionUpdate the position of mother nature in the model
     */
    public void updateMotherNaturePosition(int motherNaturePositionUpdate){
        if(motherNaturePositionUpdate < modelIslands.size()) {
            if(motherNaturePosition < tempAnchor.size())
                motherNature.get(tempAnchor.get(motherNaturePosition)).setVisible(false);
            this.motherNaturePosition = motherNaturePositionUpdate;
            motherNature.get(tempAnchor.get(motherNaturePosition)).setVisible(true);
        }
    }

    /**
     * This method browses all the modelIslands, if the number of islands does not correspond to the one saved it disables the following island..
     * Then procedes to call {@link #prepareIsland(AnchorPane, IslandData)}  prepareIsland}
     * @param islandManager the IslandManagerData sent via an UpdateIslands message
     */
    public synchronized void updateIslands(IslandManagerData islandManager){
        for(AnchorPane anchor : anchorIslands) {
            anchor.setLayoutX(GuiConstants.getIslandX(anchor));
            anchor.setLayoutY(GuiConstants.getIslandY(anchor));
            motherNature.get(anchor).setVisible(false);
            towers.get(anchor).setVisible(false);
            prohibitionCards.get(anchor).setVisible(false);
            anchor.setVisible(true);
            anchor.setDisable(false);
            for(Node child : anchor.getChildren()) {
                child.setDisable(false);
                if(!child.getId().contains("motherNature"))
                    child.setVisible(true);
            }
        }
        tempAnchor = new ArrayList<>(anchorIslands);

        modelIslands = islandManager.getIslands();
        for(int i=0; i<modelIslands.size(); i++){
            if(modelIslands.get(i).numberOfIslands() != 1){
                int j = 1;
                while(j<modelIslands.get(i).numberOfIslands()){
                    double removedIslandX = tempAnchor.get(i + 1).getLayoutX();
                    double removedIslandY = tempAnchor.get(i + 1).getLayoutY();
                    tempAnchor.get(i + 1).setDisable(true);
                    tempAnchor.get(i + 1).setVisible(false);
                    for(Node child : tempAnchor.get(i + 1).getChildren()){
                        child.setVisible(false);
                        child.setDisable(true);
                    }
                    tempAnchor.remove(i + 1);
                    moveMergedIsland(tempAnchor.get(i), j, removedIslandX, removedIslandY);
                    j++;
                }
            }
            prepareIsland(tempAnchor.get(i), modelIslands.get(i));
        }
        updateMotherNaturePosition(islandManager.getMotherNaturePosition());
    }

    /**
     * This method displays the students, the prohibition cards and the towers according to the IslandData
     * @param anchor anchor that contains the island that has to be modified
     * @param modelIsland the data of the corresponding island from the model
     */
    private void prepareIsland(AnchorPane anchor, IslandData modelIsland){
        prohibitionCards.get(anchor).setVisible(modelIsland.numProhibitionCards() > 0); //should work
        Map<Clan, Integer> studentsPresent = modelIsland.students();
        for(Clan clan : Clan.values())
            clanColors.get(clan).get(anchor).setVisible(studentsPresent.get(clan) > 0);

        TowerColor towerColor = modelIsland.towerColor();
        if(towerColor != null){
            towers.get(anchor).setImage(new Image(getClass().getResource(GuiConstants.getImagePathTower(towerColor)).toExternalForm()));
            towers.get(anchor).setVisible(true);
        } else {
            towers.get(anchor).setVisible(false);
        }
        ImageView island = null;
        for(Node child : anchor.getChildren())
            if(child.getId().contains("island"))
                island = (ImageView) child;

        island.setFitWidth(GuiConstants.getIslandWidth(modelIsland.numberOfIslands()));
        island.setFitHeight(GuiConstants.getIslandHeight(modelIsland.numberOfIslands()));

        int numOfIslands = modelIsland.numberOfIslands();
        if(numOfIslands > 1){
            if (anchor.getLayoutX() < 0)
                anchor.setLayoutX(0);
            else if (anchor.getLayoutX() + island.getFitWidth() > window.getLayoutX() + window.getPrefWidth())
                anchor.setLayoutX(window.getLayoutX() + window.getPrefWidth() - island.getFitWidth());

            if (anchor.getLayoutY() < 0)
                anchor.setLayoutY(0);
            else if (anchor.getLayoutY() + island.getFitHeight() > window.getLayoutY() + window.getPrefHeight())
                anchor.setLayoutY(window.getLayoutY() + window.getPrefHeight() - island.getFitHeight());
        }

        for(Node child : anchor.getChildren()) {
            if (child.getId().contains("students")) {
                child.setLayoutX(GuiConstants.getStudentsX(numOfIslands));
                child.setLayoutY(GuiConstants.getStudentsY(numOfIslands));
            } else if (child.getId().contains("prohibition")) {
                child.setLayoutX(GuiConstants.getProhibitionX(numOfIslands));
                child.setLayoutY(GuiConstants.getProhibitionY(numOfIslands));
            } else if (child.getId().contains("motherNature")) {
                child.setLayoutX(GuiConstants.getMotherNatureX(numOfIslands));
                child.setLayoutY(GuiConstants.getMotherNatureY(numOfIslands));
            } else if (child.getId().contains("tower")) {
                child.setLayoutX(GuiConstants.getTowerX(numOfIslands));
                child.setLayoutY(GuiConstants.getTowerY(numOfIslands));
            }
        }

    }

    /**
     * Method sets the attribute droppedStudent that is used to drag and drop students during the STUDENT_MOVING TurnState
     * @param clan the student that will be dragged and dropped
     * @see TurnState
     */
    public void setDroppedStudent(Clan clan){
        droppedStudent.set(clan);
        previousScene = SCHOOLBOARDS;
    }

    /**
     * This method enables certain properties on the droppedStudentImage so that it can be dragged on an Island
     * @param node @see Node
     */
    private void makeDraggable(Node node) {
        node.setOnMouseDragged(e -> e.setDragDetect(true));

        node.setOnDragDetected( e -> {
            Dragboard db = node.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(node.getId());
            db.setContent(content);

            e.consume();

        });

        for(AnchorPane island : anchorIslands){
            island.setOnDragDropped( e -> {
                        boolean success = false;
                        if(!droppedStudentImage.getId().isEmpty()){
                            droppedStudentImage.setVisible(false);
                            sendMessage(new MoveStudentToIslandMessage(droppedStudent.getValue(), tempAnchor.indexOf(island)));
                            droppedStudent.setValue(null);
                            success = true;
                        }
                        e.setDropCompleted(success);
                        e.consume();
                    }
            );
            island.setOnDragOver( e -> {
                if(e.getGestureSource() != island)
                    e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                e.consume();
            });
        }

        node.setOnMouseReleased(Event::consume);

    }


    /**
     * The method moveMergedIsland moves the islands closer to the space where the removes island should have been
     * @param anchor index of the merged island
     * @param removedIslandX X coordinates of the removed island
     * @param removedIslandY Y coordinates of the removes island
     */
    public void moveMergedIsland(AnchorPane anchor, int numSubIslands, double removedIslandX, double removedIslandY){ //FIXM PROBLEM with more than 2 islands
        double oldX = anchor.getLayoutX();
        double oldY = anchor.getLayoutY();
        double newX = ( (oldX + GuiConstants.getIslandWidth(numSubIslands)/2) * numSubIslands
                + removedIslandX + GuiConstants.getIslandWidth(1)/2 ) / (numSubIslands + 1)
                - GuiConstants.getIslandWidth(numSubIslands+1) / 2;
        double newY = ( (oldY + GuiConstants.getIslandHeight(numSubIslands)/2) * numSubIslands
                + removedIslandY + GuiConstants.getIslandHeight(1)/2 ) / (numSubIslands + 1)
                - GuiConstants.getIslandHeight(numSubIslands+1) / 2;
        anchor.setLayoutX(newX);
        anchor.setLayoutY(newY);
    }

    private Card currCard;

    /**
     * Method makes visible the labels and the Buttons used to describe how to move Mother Nature
     * @param currCard
     */
    public void setMotherMovingLabels(Card currCard){
        this.currCard = currCard;
        moveMotherNature.setVisible(true);
        moveMotherNature.setDisable(false);
        instructions.setVisible(true);
        int maxSteps = currCard.getMaxStepsMotherNature();
        if(postmanActivated)
            maxSteps += 2;
        instructions.setText("By clicking the button and then clicking on an Island\nyou will move Mother Nature there\nYou have " + maxSteps + " step" + (maxSteps > 1 ? "s" : ""));
    }

    /**
     * Method is called when the "Move Mother Nature" is clicked
     * @param actionEvent @see ActionEvent
     */
    public void moveMotherNature(ActionEvent actionEvent) {
        moveMotherNature.setStyle(CLICKED_BUTTON);
        enabledMoveMotherNature = true;
    }

    /**
     * Method is called when the Server sends a successful or unsuccessful attempt to move mother nature
     * @param successfulMove
     */
    public void movedMotherNature(boolean successfulMove){
        if(successfulMove){
            moveMotherNature.setVisible(false);
            moveMotherNature.setDisable(true);
            instructions.setText("");
            currCard = null;
        } else {
            int steps = currCard.getMaxStepsMotherNature();
            if(postmanActivated)
                steps += 2;
            instructions.setText("You are only allowed " + steps + " steps");
        }
        moveMotherNature.setStyle(null);
        enabledMoveMotherNature = false;
    }

    @Override
    public void handleErrorMessage(String message){
        super.handleErrorMessage(message);
        if(message.contains("The selected island is too far from Mother Nature")){
            Platform.runLater(() -> movedMotherNature(false));
        }
    }

    private CharacterID activatedCharacter;

    private boolean postmanActivated;

    private Map<Clan, Integer> characterMap;

    /**
     * This method is used to set the current activated Character. It makes visible all of the commands associated with the action to apply the effect of the character.
     * If the TurnState is MotherMoving it disables the "move MotherNature" button until the character action is done
     * @param character the character that has been activated
     */
    public void setActivatedCharacter(CharacterID character){
        this.activatedCharacter = character;
        postmanActivated = false;
        if(character != null) {
            if (character == CharacterID.HERALD || character == CharacterID.GRANDMA || (character == CharacterID.MONK && characterMap != null)) {
                chooseIsland.setStyle(null);
                chooseIsland.setVisible(true);
                chooseIsland.setDisable(false);
                instructions.setVisible(true);
                instructions.setText("By clicking on \"Choose Island\" and then clicking on an Island,\n you will use the character effect on this island\n");
                moveMotherNature.setVisible(false);
            } else if (character == CharacterID.POSTMAN) {
                postmanActivated = true;
            }
        }
    }

    /**
     * This method sets characterMap which is a map containing the students chosen because of a character card that has been activated, specifically the MONK card
     * @param map the map containing the students
     * @see CharacterID
     */
    public void setCharacterMap(Map<Clan, Integer> map){
        this.characterMap = new EnumMap<>(map);
    }

}

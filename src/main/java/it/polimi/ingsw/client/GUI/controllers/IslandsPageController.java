package it.polimi.ingsw.client.GUI.controllers;

import it.polimi.ingsw.client.modeldata.IslandData;
import it.polimi.ingsw.client.modeldata.IslandManagerData;
import it.polimi.ingsw.constants.ConstantsGUI;
import it.polimi.ingsw.messages.gamemessages.ApplyCharacterCardEffectMessage1;
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
import javafx.event.EventHandler;
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

import static it.polimi.ingsw.constants.ConstantsGUI.*;
import static it.polimi.ingsw.model.Clan.*;

//TODO add droppedStudent to FXML
//TODO add labels to island for the numbers
//FIXME fix merge proportions
public class IslandsPageController extends PageController implements Initializable {

    private final String CLICKED_BUTTON = "-fx-opacity: 0.5";
    Button chooseIsland;

    @FXML private Pane window;
    @FXML private Button moveMotherNature;
    private boolean enabledMoveMotherNature;
    private boolean enableCharacter;

    List<AnchorPane> anchorIslands;
    List<AnchorPane> tempAnchor;

    Map<AnchorPane, ImageView> prohibitionCards = new HashMap<>();
    Map<Clan, Map<AnchorPane, ImageView>> clanColors = new EnumMap<>(Clan.class);

    @FXML AnchorPane anchorIsland0;
    @FXML AnchorPane anchorIsland1;
    @FXML AnchorPane anchorIsland2;
    @FXML AnchorPane anchorIsland3;
    @FXML AnchorPane anchorIsland4;
    @FXML AnchorPane anchorIsland5;
    @FXML AnchorPane anchorIsland6;
    @FXML AnchorPane anchorIsland7;
    @FXML AnchorPane anchorIsland8;
    @FXML AnchorPane anchorIsland9;
    @FXML AnchorPane anchorIsland10;
    @FXML AnchorPane anchorIsland11;

    @FXML ImageView prohibitionCard0;
    @FXML ImageView prohibitionCard1;
    @FXML ImageView prohibitionCard2;
    @FXML ImageView prohibitionCard3;
    @FXML ImageView prohibitionCard4;
    @FXML ImageView prohibitionCard5;
    @FXML ImageView prohibitionCard6;
    @FXML ImageView prohibitionCard7;
    @FXML ImageView prohibitionCard8;
    @FXML ImageView prohibitionCard9;
    @FXML ImageView prohibitionCard10;
    @FXML ImageView prohibitionCard11;

    @FXML ImageView pink0;
    @FXML ImageView pink1;
    @FXML ImageView pink2;
    @FXML ImageView pink3;
    @FXML ImageView pink4;
    @FXML ImageView pink5;
    @FXML ImageView pink6;
    @FXML ImageView pink7;
    @FXML ImageView pink8;
    @FXML ImageView pink9;
    @FXML ImageView pink10;
    @FXML ImageView pink11;
    Map<AnchorPane, ImageView> pink = new HashMap<>();

    @FXML ImageView green0;
    @FXML ImageView green1;
    @FXML ImageView green2;
    @FXML ImageView green3;
    @FXML ImageView green4;
    @FXML ImageView green5;
    @FXML ImageView green6;
    @FXML ImageView green7;
    @FXML ImageView green8;
    @FXML ImageView green9;
    @FXML ImageView green10;
    @FXML ImageView green11;
    Map<AnchorPane, ImageView> green = new HashMap<>();

    @FXML ImageView blue0;
    @FXML ImageView blue1;
    @FXML ImageView blue2;
    @FXML ImageView blue3;
    @FXML ImageView blue4;
    @FXML ImageView blue5;
    @FXML ImageView blue6;
    @FXML ImageView blue7;
    @FXML ImageView blue8;
    @FXML ImageView blue9;
    @FXML ImageView blue10;
    @FXML ImageView blue11;
    Map<AnchorPane, ImageView> blue = new HashMap<>();

    @FXML ImageView red0;
    @FXML ImageView red1;
    @FXML ImageView red2;
    @FXML ImageView red3;
    @FXML ImageView red4;
    @FXML ImageView red5;
    @FXML ImageView red6;
    @FXML ImageView red7;
    @FXML ImageView red8;
    @FXML ImageView red9;
    @FXML ImageView red10;
    @FXML ImageView red11;
    Map<AnchorPane, ImageView> red = new HashMap<>();

    @FXML ImageView yellow0;
    @FXML ImageView yellow1;
    @FXML ImageView yellow2;
    @FXML ImageView yellow3;
    @FXML ImageView yellow4;
    @FXML ImageView yellow5;
    @FXML ImageView yellow6;
    @FXML ImageView yellow7;
    @FXML ImageView yellow8;
    @FXML ImageView yellow9;
    @FXML ImageView yellow10;
    @FXML ImageView yellow11;
    Map<AnchorPane, ImageView> yellow = new HashMap<>();

    @FXML ImageView tower0;
    @FXML ImageView tower1;
    @FXML ImageView tower2;
    @FXML ImageView tower3;
    @FXML ImageView tower4;
    @FXML ImageView tower5;
    @FXML ImageView tower6;
    @FXML ImageView tower7;
    @FXML ImageView tower8;
    @FXML ImageView tower9;
    @FXML ImageView tower10;
    @FXML ImageView tower11;
    Map< AnchorPane, ImageView> towers = new HashMap<>();

    @FXML ImageView motherNature0;
    @FXML ImageView motherNature1;
    @FXML ImageView motherNature2;
    @FXML ImageView motherNature3;
    @FXML ImageView motherNature4;
    @FXML ImageView motherNature5;
    @FXML ImageView motherNature6;
    @FXML ImageView motherNature7;
    @FXML ImageView motherNature8;
    @FXML ImageView motherNature9;
    @FXML ImageView motherNature10;
    @FXML ImageView motherNature11;
    Map<AnchorPane, ImageView> motherNature = new HashMap<>();

    @FXML Button back;

    int motherNaturePosition;
    List<IslandData> modelIslands;

    private ObjectProperty<Clan> droppedStudent = new SimpleObjectProperty<>(null);
    @FXML ImageView droppedStudentImage;
    @FXML Label droppedStudentLabel;
    @FXML AnchorPane droppedStudentAnchor;
    @FXML Label instructions;

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

        droppedStudent.addListener((observable, oldValue, newValue) -> { //TODO test if it works
                    if (observable.getValue() != null) {
                        System.out.println("Dovrebbe essere null: " + observable.getValue());
                        droppedStudentAnchor.getStyleClass().add("image-view-selection");
                        droppedStudentAnchor.setVisible(true);
                        droppedStudentAnchor.setDisable(false);
                        droppedStudentImage.setImage(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(newValue)).toExternalForm()));
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

    public void back(ActionEvent event) {
        if(previousScene != null && gui.getTurnState()!=null && !gui.getTurnState().equals(TurnState.MOTHER_MOVING))
            gui.setCurrScene(previousScene);
        else
            gui.setCurrScene(GAMEBOARD);
        previousScene = null;
    }


    public void selectIsland(MouseEvent mouseEvent) {
        System.out.println("selected island" + mouseEvent.getSource());
        System.out.println("tempAnchor size: " + tempAnchor.size());
        for (int i = 0; i < tempAnchor.size(); i++) {
            System.out.println("elemento di TempAnchor: " + tempAnchor.get(i));
            if (/*tempAnchor.get(i).getChildren().contains((Node) mouseEvent.getSource()) ||*/ tempAnchor.get(i).equals(mouseEvent.getSource())) { //Trova l'anchorpane padre dell'oggetto su cui viene fatto click
                System.out.println("è stata selezionata isola numero " + tempAnchor.get(i).getId() + " che corrisponde all'isola di indice " + modelIslands.get(i).islandIndex() + " nel model e: " + i + " nel controller"); //TODO delete
                if(enabledMoveMotherNature) {
                    sendMessage(new MoveMotherNatureMessage(i));
                    System.out.println("inviato messaggio, spostare MN su: " + i);
                } else if (enableCharacter){
                    sendMessage(new ApplyCharacterCardEffectMessage1(i));
                    enableCharacter = false;
                    activatedCharacter = null;
                    chooseIsland.setVisible(false);
                    chooseIsland.setDisable(true);
                    instructions.setVisible(false);
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
                System.out.println("MoveMotherNatureButton: " + (enabledMoveMotherNature? "clicked" : "not clicked"));
            }
        }
    }


    public void updateMotherNaturePosition(int motherNaturePositionUpdate){
        if(motherNaturePositionUpdate < modelIslands.size()) { //FIXME arriva update della posizione di MN prima del merge, e poi madre natura viene persa
            if(motherNaturePosition < tempAnchor.size())
                motherNature.get(tempAnchor.get(motherNaturePosition)).setVisible(false);
            this.motherNaturePosition = motherNaturePositionUpdate;
            motherNature.get(tempAnchor.get(motherNaturePosition)).setVisible(true);
        }
    }

    /**
     * This method browses all of the modelIslands, if the number of islands does not correspond to the one saved it disables the following island, deleting the corresponding images and anchors from the various lists.
     * Then procedes to call {@link #prepareIsland(AnchorPane, IslandData)}  prepareIsland}
     * @param islandManager the IslandManagerData sent via an UpdateIslands message
     */
    public synchronized void updateIslands(IslandManagerData islandManager){ //FIXME opening game with 10 islands, 11th island is visible, it should't be
        for(AnchorPane anchor : anchorIslands) {
            anchor.setLayoutX(ConstantsGUI.getIslandX(anchor));
            anchor.setLayoutY(ConstantsGUI.getIslandY(anchor));
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
                    System.out.println("L'isola che sta facendo merging è: " + i);
                    double removedIslandX = tempAnchor.get(i + 1).getLayoutX();
                    double removedIslandY = tempAnchor.get(i + 1).getLayoutY();
                    tempAnchor.get(i + 1).setDisable(true);
                    tempAnchor.get(i + 1).setVisible(false);
                    for(Node child : tempAnchor.get(i + 1).getChildren()){
                        child.setVisible(false);
                        child.setDisable(true);
                    }
                    //remove(i + 1);
                    tempAnchor.remove(i + 1);
                    System.out.println("Ho rimosso l'isola: " + (i+1));
                    moveMergedIsland(tempAnchor.get(i), j, removedIslandX, removedIslandY);
                    j++; /*numOfIslands.get(i+1)*/ //This way j comprehends the num of islands corresponding to the removed island
                    System.out.println("il numero di isole ora è a: " + j);
                    //FIXME non fa vedere la pedina dell'ultima isola, fixed?
                }
            }
            prepareIsland(tempAnchor.get(i), modelIslands.get(i));
        }
        updateMotherNaturePosition(islandManager.getMotherNaturePosition());
    }

    private void prepareIsland(AnchorPane anchor, IslandData modelIsland){
        System.out.println("Sto preparando l'isola: " + anchor + " con indice nel model: " + modelIsland.islandIndex());
        prohibitionCards.get(anchor).setVisible(modelIsland.numProhibitionCards() > 0); //should work
        Map<Clan, Integer> studentsPresent = modelIsland.students();
        for(Clan clan : Clan.values())
            clanColors.get(clan).get(anchor).setVisible(studentsPresent.get(clan) > 0);

        TowerColor towerColor = modelIsland.towerColor();
        if(towerColor != null){
            towers.get(anchor).setImage(new Image(getClass().getResource(ConstantsGUI.getImagePathTower(towerColor)).toExternalForm()));
            towers.get(anchor).setVisible(true);
        } else {
            towers.get(anchor).setVisible(false);
        }
        ImageView island = null;
        for(Node child : anchor.getChildren())
            if(child.getId().contains("island"))
                island = (ImageView) child;

        island.setFitWidth(ConstantsGUI.getIslandWidth(modelIsland.numberOfIslands()));
        island.setFitHeight(ConstantsGUI.getIslandHeight(modelIsland.numberOfIslands()));

        if(modelIsland.numberOfIslands() > 1){ //FIXME le isole si muovono anche quando vengono spostati gli studenti
            if (anchor.getLayoutX() < 0)
                anchor.setLayoutX(0);
            else if (anchor.getLayoutX() + island.getFitWidth() > window.getLayoutX() + window.getPrefWidth())
                anchor.setLayoutX(window.getLayoutX() + window.getPrefWidth() - island.getFitWidth());

            if (anchor.getLayoutY() < 0)
                anchor.setLayoutY(0);
            else if (anchor.getLayoutY() + island.getFitHeight() > window.getLayoutY() + window.getPrefHeight())
                anchor.setLayoutY(window.getLayoutY() + window.getPrefHeight() - island.getFitHeight());
        }

    }

    public void setDroppedStudent(Clan clan){
        droppedStudent.set(clan);
        previousScene = SCHOOLBOARDS;
    }


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

        node.setOnMouseReleased( e -> e.consume()); //TODO delete?

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
        double newX = ( (oldX + ConstantsGUI.getIslandWidth(numSubIslands)/2) * numSubIslands
                + removedIslandX + ConstantsGUI.getIslandWidth(1)/2 ) / (numSubIslands + 1)
                - ConstantsGUI.getIslandWidth(numSubIslands+1) / 2;
        double newY = ( (oldY + ConstantsGUI.getIslandHeight(numSubIslands)/2) * numSubIslands
                + removedIslandY + ConstantsGUI.getIslandHeight(1)/2 ) / (numSubIslands + 1)
                - ConstantsGUI.getIslandHeight(numSubIslands+1) / 2;
        anchor.setLayoutX(newX);
        anchor.setLayoutY(newY);
        //FIXME muovere studenti
        System.out.println("Vecchia posizione isola "+ anchor + " che ha fatto merge: X: " + oldX + " Y: " + oldY + "(XremovedIsland: " + removedIslandX + " YremovedIsland: " + removedIslandY);
        System.out.println("Posizione dell'isola che ha fatto merge: X: " + newX + " Y: " + newY);
    }

    private Card currCard;

    public void setMotherMovingLabels(Card currCard){
        this.currCard = currCard;
        moveMotherNature.setVisible(true);
        moveMotherNature.setDisable(false);
        instructions.setVisible(true);
        int maxSteps = currCard.getMaxStepsMotherNature(); //FIXME modificare per characters
        if(postmanActivated)
            maxSteps += 2;
        instructions.setText("By clicking the button and then clicking on an Island\nyou will move Mother Nature there\nYou have " + maxSteps + " step" + (maxSteps > 1 ? "s" : ""));
    }

    public void moveMotherNature(ActionEvent actionEvent) {
        moveMotherNature.setStyle(CLICKED_BUTTON);
        enabledMoveMotherNature = true;
    }

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
        enabledMoveMotherNature = false; //FIXME deve cliccare di nuovo sul bottone per muovere madre natura
    }

    @Override
    public void handleErrorMessage(String message){
        super.handleErrorMessage(message);
        if(message.contains("The selected island is too far from Mother Nature")){
            Platform.runLater(() -> movedMotherNature(false));
        }
    }

    CharacterID activatedCharacter;

    boolean postmanActivated;

    public void setActivatedCharacter(CharacterID character){
        this.activatedCharacter = character;
        if(character != null) {
            if (character == CharacterID.HERALD || character == CharacterID.GRANDMA) {
                chooseIsland.setStyle(null);
                chooseIsland.setVisible(true);
                chooseIsland.setDisable(false);
                instructions.setVisible(true);
                instructions.setText("By clicking on \"Choose Island\" and then clicking on an Island,\n you will use the character effect on this island");
                //TODO magari non dare la possibilità di fare back
            } else if (character == CharacterID.POSTMAN) {
                postmanActivated = true;
            } else {
                postmanActivated = false;
            }
        } else {
            postmanActivated = false;
        }

    }

}

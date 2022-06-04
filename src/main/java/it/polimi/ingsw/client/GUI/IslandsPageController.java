package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.modeldata.IslandData;
import it.polimi.ingsw.client.modeldata.IslandManagerData;
import it.polimi.ingsw.constants.ConstantsGUI;
import it.polimi.ingsw.messages.gamemessages.MoveMotherNatureMessage;
import it.polimi.ingsw.messages.gamemessages.MoveStudentToIslandMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.TowerColor;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
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
public class IslandsPageController extends PageController implements Initializable {

    private String CLICKED_BUTTON = "-fx-opacity: 0.5";

    @FXML private Pane window;
    @FXML private Button moveMotherNature;
    private boolean enabledMoveMotherNature;

    List<AnchorPane> anchorIslands;
    List<ImageView> prohibitionCards;
    Map<Clan, List<ImageView>> clanColors = new EnumMap<>(Clan.class);

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
    List<ImageView> pink;

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
    List<ImageView> green;

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
    List<ImageView> blue;

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
    List<ImageView> red;

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
    List<ImageView> yellow;

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
    List<ImageView> towers;

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
    List<ImageView> motherNature;

    @FXML Button back;

    int motherNaturePosition;
    List<IslandData> modelIslands;

    private ObjectProperty<Clan> droppedStudent = new SimpleObjectProperty<>(null);
    @FXML ImageView droppedStudentImage;
    @FXML Label droppedStudentLabel;
    @FXML AnchorPane droppedStudentAnchor;
    @FXML Label instructions;

    private List<Integer> numOfIslands = new ArrayList<>();

    private String previousScene;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //islands = new ArrayList<>(Arrays.asList(island0, island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11));
        anchorIslands = new ArrayList<>(Arrays.asList(anchorIsland0, anchorIsland1, anchorIsland2, anchorIsland3, anchorIsland4, anchorIsland5, anchorIsland6, anchorIsland7, anchorIsland8, anchorIsland9, anchorIsland10, anchorIsland11));
        prohibitionCards = new ArrayList<>(Arrays.asList(prohibitionCard0, prohibitionCard1, prohibitionCard2, prohibitionCard3, prohibitionCard4, prohibitionCard5, prohibitionCard6, prohibitionCard7, prohibitionCard8, prohibitionCard9, prohibitionCard10, prohibitionCard11));
        pink = new ArrayList<>(Arrays.asList(pink0, pink1, pink2, pink3, pink4, pink5, pink6, pink7, pink8, pink9, pink10, pink11));
        green = new ArrayList<>(Arrays.asList(green0, green1, green2, green3, green4, green5, green6, green7, green8, green9, green10, green11));
        blue = new ArrayList<>(Arrays.asList(blue0, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10, blue11));
        red = new ArrayList<>(Arrays.asList(red0, red1, red2, red3, red4, red5, red6, red7, red8, red9, red10, red11));
        yellow = new ArrayList<>(Arrays.asList(yellow0, yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10, yellow11));

        clanColors.put(PIXIES, yellow);
        clanColors.put(UNICORNS, blue);
        clanColors.put(TOADS, green);
        clanColors.put(DRAGONS, red);
        clanColors.put(FAIRIES, pink);

        towers = new ArrayList<>(Arrays.asList(tower0, tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8, tower9, tower10, tower11));
        motherNature = new ArrayList<>(Arrays.asList(motherNature0, motherNature1, motherNature2, motherNature3, motherNature4, motherNature5, motherNature6, motherNature7, motherNature8, motherNature9, motherNature10, motherNature11));

        for(int i = 0; i< anchorIslands.size(); i++) {
            motherNature.get(i).setVisible(false);
            towers.get(i).setVisible(false);
            prohibitionCards.get(i).setVisible(false);
            anchorIslands.get(i).getStylesheets().add("/style.css");
            anchorIslands.get(i).getStyleClass().add("image-view-selection");
            numOfIslands.add(i, 1);
        }

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
        if(previousScene != null && !gui.getTurnState().equals(TurnState.MOTHER_MOVING))
            gui.setCurrScene(previousScene);
        else
            gui.setCurrScene(GAMEBOARD);
        previousScene = null;
    }


    public void selectIsland(MouseEvent mouseEvent) {
        System.out.println("selected island" + mouseEvent.getSource());

            for (int i = 0; i < anchorIslands.size(); i++) {
                if (anchorIslands.get(i).getChildren().contains((Node) mouseEvent.getSource())) { //Trova l'anchorpane padre dell'oggetto su cui viene fatto click
                    System.out.println("è stata selezionata isola numero " + anchorIslands.get(i).getId() + " che corrisponde all'isola di indice " + modelIslands.get(i).islandIndex() + " nel model"); //TODO delete
                    if(!enabledMoveMotherNature) {
                        String imagePath = null;
                        for (Node child : anchorIslands.get(i).getChildren()) {
                            if (child instanceof ImageView) {
                                if (child.getId().contains("island")) {
                                    imagePath = ((ImageView) child).getImage().getUrl();
                                }
                            }
                        }
                        ((SingleIslandController) gui.getControllers().get(SINGLEISLAND)).setIsland(modelIslands.get(i), imagePath, i == motherNaturePosition);
                        gui.setCurrScene(SINGLEISLAND);
                    } else {
                        sendMessage(new MoveMotherNatureMessage(i));
                    }
                }
            }
    }


    private void updateMotherNaturePosition(int motherNaturePositionUpdate){
        if(motherNaturePositionUpdate != this.motherNaturePosition) {
            motherNature.get(motherNaturePosition).setVisible(false);
            this.motherNaturePosition = motherNaturePositionUpdate;
            motherNature.get(motherNaturePosition).setVisible(true);
        }
    }

    /**
     * This method browses all of the modelIslands, if the number of islands does not correspond to the one saved it disables the following island, deleting the corresponding images and anchors from the various lists.
     * Then procedes to call {@link #prepareIsland(int, IslandData) prepareIsland}
     * @param islandManager the IslandManagerData sent via an UpdateIslands message
     */
    public void updateIslands(IslandManagerData islandManager){
        //TODO non ci sono merge -> si toglie solo un'isola?
        modelIslands = islandManager.getIslands();
        for(int i=0; i<modelIslands.size(); i++){
            if(modelIslands.get(i).numberOfIslands() != numOfIslands.get(i)){
                for(int j=1; j<modelIslands.get(i).numberOfIslands(); j++){
                    double removedIslandX = anchorIslands.get(i + 1).getLayoutX();
                    double removedIslandY = anchorIslands.get(i + 1).getLayoutY();
                    anchorIslands.get(i + 1).setDisable(true);
                    anchorIslands.get(i + 1).setVisible(false);
                    for(Node child : anchorIslands.get(i + 1).getChildren()){
                        child.setVisible(false);
                        child.setDisable(true);
                    }
                    remove(i + 1);
                    moveMergedIsland(i, removedIslandX, removedIslandY);
                }
                numOfIslands.set(i, modelIslands.get(i).numberOfIslands());
            }
            prepareIsland(i, modelIslands.get(i));
        }
        updateMotherNaturePosition(islandManager.getMotherNaturePosition());
    }

    private void prepareIsland(int anchorIndex, IslandData modelIsland){
        if(modelIsland.numProhibitionCards() != 0){
            prohibitionCards.get(anchorIndex).setVisible(true); //should work
        }
        Map<Clan, Integer> studentsPresent = modelIsland.students();
        for(Clan clan : Clan.values()){
            clanColors.get(clan).get(anchorIndex).setVisible(studentsPresent.get(clan) != 0);
        }
        TowerColor towerColor = modelIsland.towerColor();
        if(towerColor != null){
            towers.get(anchorIndex).setImage(new Image(getClass().getResource(ConstantsGUI.getImagePathTower(towerColor)).toExternalForm()));
            towers.get(anchorIndex).setVisible(true);
        }
        if(modelIsland.numberOfIslands() > 1){
            double coefficient = modelIsland.numberOfIslands() - 1; //per due isole aggiungo la metà per 3 isole aggiungo
            int childIslandIndex = 100;
            for(Node child : anchorIslands.get(anchorIndex).getChildren()){
                if(child.getId().contains("island")){
                    childIslandIndex = anchorIslands.get(anchorIndex).getChildren().indexOf(child);
                }
            }
            if (childIslandIndex != 100) {

                ((ImageView) anchorIslands.get(anchorIndex).getChildren().get(childIslandIndex)).setFitWidth(ConstantsGUI.getIslandWidth() + coefficient * ConstantsGUI.getIslandWidth() / 2);
                ((ImageView) anchorIslands.get(anchorIndex).getChildren().get(childIslandIndex)).setFitHeight(ConstantsGUI.getIslandHeight() + coefficient * ConstantsGUI.getIslandHeight() / 2);

                double x = anchorIslands.get(anchorIndex).getLayoutX();
                double width = ConstantsGUI.getIslandWidth() + coefficient * ConstantsGUI.getIslandWidth() / 2;
                double height = ConstantsGUI.getIslandHeight() + coefficient * ConstantsGUI.getIslandHeight() / 2;
                double y = anchorIslands.get(anchorIndex).getLayoutY();
                System.out.println("Posizione isola " + anchorIndex + " che era fuori window prima del move: X: " + x + " Y: " + y);
                System.out.println("Dimensioni isola: width: " + width + " height: " +height);

                double windowRightX = window.getLayoutX() + window.getPrefWidth();
                double windowBottomY = window.getLayoutY() + window.getPrefHeight();

                if (x + width > windowRightX) {
                    anchorIslands.get(anchorIndex).setLayoutX(windowRightX - width);
                }
                if (y + height > windowBottomY) {
                    anchorIslands.get(anchorIndex).setLayoutY(windowBottomY - height);
                }
                System.out.println("Vecchia posizione isola che era fuori window: X: " + x + " Y: " + y);
                System.out.println("Posizione dell'isola che era fuori dalla finestra: X: " + anchorIslands.get(childIslandIndex).getLayoutX() + " Y: " + anchorIslands.get(childIslandIndex).getLayoutY());
            }

        }
    }

    public void setDroppedStudent(Clan clan){
        droppedStudent.set(clan);
        previousScene = SCHOOLBOARDS;
    }


    private void makeDraggable(Node node) {
        node.setOnMouseDragged(e -> {
            System.out.println("onMousePressed");
            e.setDragDetect(true);
        });

        node.setOnDragDetected( e -> { //Dragging not working properly
            System.out.println("onDragDetected"); //FIXME viene stampato

            Dragboard db = node.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(node.getId());
            db.setContent(content);

            e.consume();

        });

        for(AnchorPane island : anchorIslands){
            island.setOnDragDropped( e -> {
                System.out.println("onDragDropped");
                boolean success = false;
                if(!droppedStudentImage.getId().isEmpty()){
                    System.out.println("è stato dropped un clan di tipo: " + droppedStudentImage.getId());
                    droppedStudentImage.setVisible(false);
                    instructions.setText("The student has been dropped on island " +  anchorIslands.indexOf(island));
                    sendMessage(new MoveStudentToIslandMessage(droppedStudent.getValue(), anchorIslands.indexOf(island)));
                    droppedStudent.setValue(null);
                    success = true;
                }
                System.out.println("Variabile success: " + success);
                e.setDropCompleted(success);
                e.consume();
                    }
            );
            island.setOnDragOver( e -> {
                System.out.println("Qualcosa sta passando sopra all'isola, onMouseDragOver");
                if(e.getGestureSource() != island)
                    e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                e.consume();
            });
        }

        node.setOnMouseReleased( e -> {
            System.out.println("onDragDone");
            System.out.println("è stato dropped?");

            e.consume();


        });

    }

    public void remove(int index){
        anchorIslands.remove(index);
        prohibitionCards.remove(index);
        pink.remove(index);
        blue.remove(index);
        green.remove(index);
        yellow.remove(index);
        red.remove(index);
        towers.remove(index);
        numOfIslands.remove(index);
    }

    /**
     * The method moveMergedIsland moves the islands closer to the space where the removes island should have been
     * @param indexIsland index of the merged island
     * @param removedIslandX X coordinates of the removed island
     * @param removedIslandY Y coordinates of the removes island
     */
    public void moveMergedIsland(int indexIsland, double removedIslandX, double removedIslandY){ //FIXM PROBLEM with more than 2 islands
        double oldX = anchorIslands.get(indexIsland).getLayoutX();
        double oldY = anchorIslands.get(indexIsland).getLayoutY();
        double newX = (oldX + removedIslandX)/2;
        double newY = (oldY + removedIslandY)/2;
        anchorIslands.get(indexIsland).setLayoutX(newX);
        anchorIslands.get(indexIsland).setLayoutY(newY);
        System.out.println("Vecchia posizione isola "+ indexIsland + " che ha fatto merge: X: " + oldX + " Y: " + oldY + "(XremovedIsland: " + removedIslandX + " YremovedIsland: " + removedIslandY);
        System.out.println("Posizione dell'isola che ha fatto merge: X: " + (oldX + removedIslandX)/2 + " Y: " + (oldY + removedIslandY)/2);
    }

    private Card currCard;

    public void setMotherMovingLabels(Card currCard){
        //TODO così se qualcuno ha mosso studente su un'isola e diventa mother moving lo capisce dalle labels
        this.currCard = currCard;
        moveMotherNature.setVisible(true);
        moveMotherNature.setDisable(false);
        int maxSteps = currCard.getMaxStepsMotherNature(); //FIXME modificare per characters
        instructions.setText("By clicking the button and then clicking on an Island\nyou will move Mother Nature there\nYou have " + maxSteps + " step" + (maxSteps > 1 ? "s" : ""));
    }

    public void moveMotherNature(ActionEvent actionEvent) {
        moveMotherNature.setStyle(CLICKED_BUTTON);
        enabledMoveMotherNature = true;
    }

    public void movedMotherNature(boolean successfulMove, int updatedPosition){
        if(successfulMove){
            updateMotherNaturePosition(updatedPosition);
            moveMotherNature.setVisible(false);
            moveMotherNature.setDisable(true);
            instructions.setText("");
            currCard = null;
        } else {
            instructions.setText("You are only allowed " + currCard.getPriority() + " steps");
        }
        moveMotherNature.setStyle(null);
        enabledMoveMotherNature = false; //FIXME deve cliccare di nuovo sul bottone per muovere madre natura
    }

    @Override
    public void handleErrorMessage(String message){
        super.handleErrorMessage(message);
        if(message.contains("The selected island is too far from Mother Nature")){
            Platform.runLater(() -> {
                movedMotherNature(false, 13);
            });
        }
    }
}

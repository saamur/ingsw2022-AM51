package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.modeldata.CloudData;
import it.polimi.ingsw.client.modeldata.CloudManagerData;
import it.polimi.ingsw.constants.GuiConstants;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.messages.gamemessages.ChosenCloudMessage;
import it.polimi.ingsw.messages.gamemessages.EndTurnMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.TurnState;
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

import static it.polimi.ingsw.constants.GuiConstants.CHARACTERS;
import static it.polimi.ingsw.constants.GuiConstants.GAMEBOARD;

/**
 * CloudController displays the Clouds on GUI
 */
public class CloudController extends PageController implements Initializable {
    @FXML
    private AnchorPane anchorCloud0;
    @FXML
    private AnchorPane anchorCloud1;
    @FXML
    private AnchorPane anchorCloud2;

    @FXML
    private ImageView student1cloud0;
    @FXML
    private ImageView student2cloud0;
    @FXML
    private ImageView student3cloud0;
    @FXML
    private ImageView student4cloud0;


    @FXML
    private ImageView student1cloud1;
    @FXML
    private ImageView student2cloud1;
    @FXML
    private ImageView student3cloud1;
    @FXML
    private ImageView student4cloud1;

    @FXML
    private ImageView student1cloud2;
    @FXML
    private ImageView student2cloud2;
    @FXML
    private ImageView student3cloud2;
    @FXML
    private ImageView student4cloud2;

    private boolean isClientTurn;

    private int numOfClouds;
    private CloudData[] modelClouds;


    private List<AnchorPane> clouds = new ArrayList<>();
    private final Map<Integer, List<ImageView>> students = new HashMap<>();

    @FXML private Button endTurnButton;
    @FXML private Button activateCharacterButton;
    @FXML private Label chooseCloudLabel;

    private boolean expertModeEnabled;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clouds = new ArrayList<>(Arrays.asList(anchorCloud0, anchorCloud1, anchorCloud2));
        endTurnButton.setVisible(false);
        activateCharacterButton.setVisible(false);
        endTurnButton.setDisable(true);
        activateCharacterButton.setDisable(true);
        chooseCloudLabel.setVisible(false);
    }

    /**
     * Method is connected to the "Go Back" Button in the fxml file, it opens the GameBoard scene
     * @see ActionEvent
     */
    public void back(ActionEvent event) {
        gui.setCurrScene(GAMEBOARD);
    }

    /**
     * Displays the clouds according to the information received by CloudManagerData
     * @param cloudManagerData the CloudManagerData received from the ServerHandler
     * @param expertModeEnabled true if expert mode is enabled
     */
    public void setClouds(CloudManagerData cloudManagerData, boolean expertModeEnabled) {
        this.modelClouds = cloudManagerData.clouds();
        numOfClouds = modelClouds.length;
        int numOfStudentsPerCloud = GameConstants.getNumStudentsPerCloud(numOfClouds);
        List<ImageView> studentsCloud0 = new ArrayList<>(Arrays.asList(student1cloud0, student2cloud0, student3cloud0));
        List<ImageView> studentsCloud1 = new ArrayList<>(Arrays.asList(student1cloud1, student2cloud1, student3cloud1));

        if (numOfClouds < 3) {
            clouds.remove(2);
            anchorCloud2.setDisable(true);
            for(Node child : anchorCloud2.getChildren()){
                child.setVisible(false);
            }
            if (numOfStudentsPerCloud == 3) {
                student4cloud0.setVisible(false);
                student4cloud1.setVisible(false);
                student4cloud2.setVisible(false);
            }
        } else {
            List<ImageView> studentsCloud2 = new ArrayList<>(Arrays.asList(student1cloud2, student2cloud2, student3cloud2, student4cloud2));
            studentsCloud0.add(student4cloud0);
            studentsCloud1.add(student4cloud1);
            students.put(2, studentsCloud2);
        }

        students.put(0, studentsCloud0);
        students.put(1, studentsCloud1);

        for (int i = 0; i < modelClouds.length; i++) {
            if(modelClouds[i].picked()){
                clouds.get(i).setOpacity(0.5);
                for(int j = 0; j< numOfStudentsPerCloud; j++)
                    students.get(i).get(j).setVisible(false);
            } else
                modifyCloud(i, modelClouds[i]);
        }

        this.expertModeEnabled = expertModeEnabled;
    }

    /**
     * Method is called when the client clicks on a cloud.
     * @param event @see MouseEvent
     */
    public void selectCloud(MouseEvent event) {
        for (int i = 0; i < numOfClouds; i++) {
            if (clouds.get(i).getChildren().contains((Node) event.getSource())) {
                sendMessage(new ChosenCloudMessage(i));
            }
        }
    }

    /**
     * Method is called when the client receives an UpdateCloudManager. It calls the method {@link #modifyCloud(int, CloudData) modifyCloud(int, CloudData)} to display the Clouds correctly.
     * @param cloudManager the data that needs to be updated
     * @see it.polimi.ingsw.messages.updatemessages.UpdateCloudManager
     */
    public void updateClouds(CloudManagerData cloudManager) {
        CloudData[] newClouds = cloudManager.clouds();
        for (int i = 0; i < newClouds.length; i++) {
            if (!newClouds[i].equals(modelClouds[i])) {
                modifyCloud(i, newClouds[i]);
            }
            clouds.get(i).setOpacity(1);
        }
    }

    /**
     * This method is called when the client receives an UpdateCloud message, after a cloud has been picked
     * @param cloud the cloud that has been chosen
     * @see it.polimi.ingsw.messages.updatemessages.UpdateCloud
     */
    public void updateCloud(CloudData cloud){
        if(cloud.picked()){
            clouds.get(cloud.cloudIndex()).setOpacity(0.5);
            if(isClientTurn) {
                endTurnButton.setVisible(true);
                endTurnButton.setDisable(false);
            }
        }
    }

    /**
     * This method is called every time the TurnState changes, this way the scene can display the "Activate Character Card" button or "End Turn" if the TurnState is END_TURN
     * @param turnState the current TurnState
     * @param isTheirTurn true if it's the turn of the client
     * @see TurnState
     */
    public void updateTurnState(TurnState turnState, boolean isTheirTurn){
        this.isClientTurn = isTheirTurn;
        if(turnState == TurnState.CLOUD_CHOOSING || turnState == TurnState.END_TURN){
            if(isClientTurn) {
                if (expertModeEnabled) {
                    activateCharacterButton.setVisible(true);
                    activateCharacterButton.setDisable(false);
                }
                chooseCloudLabel.setVisible(true);
            }
        } else {
            endTurnButton.setVisible(false);
            endTurnButton.setDisable(true);
            activateCharacterButton.setVisible(false);
            activateCharacterButton.setDisable(true);
            endTurnButton.setDisable(true);
            chooseCloudLabel.setVisible(false);
        }
    }

    /**
     * This method manages the students on the chosen Clouds and displays the students according to the CloudData
     * @param cloudIndex index of the Cloud that has to be modified
     * @param cloudData data of the Cloud from the model
     */
    public void modifyCloud(int cloudIndex, CloudData cloudData) {
        int i = 0; //index of student that has to be set
        Map<Clan, Integer> studentsData = cloudData.students();
        for (Clan clan : Clan.values()) {
            while (studentsData.get(clan) > 0) {
                students.get(cloudIndex).get(i).setImage(new Image(getClass().getResource(GuiConstants.getImagePathStudent(clan)).toExternalForm()));
                students.get(cloudIndex).get(i).setVisible(true);
                studentsData.put(clan, studentsData.get(clan) - 1);
                i++;
            }
        }
    }

    /**
     * Fires a change to the ServerHandler containing an EndTurnMessage()
     * @param actionEvent @see ActionEvent
     * @see EndTurnMessage
     */
    public void endTurn(ActionEvent actionEvent) {
        sendMessage(new EndTurnMessage());
    }

    /**
     * If the "Activate Character Card" Button is clicked, this method is called. It opens the Characters' scene.
     * @param actionEvent @see ActionEvent
     */
    public void activateCharacterCard(ActionEvent actionEvent) {
        gui.setCurrScene(CHARACTERS);
    }

    /**
     * This method is used for the last round. It makes it possible to end the turn without choosing a cloud.
     */
    public void lastRound(){
        for(AnchorPane cloud : clouds){
            for(Node child : cloud.getChildren()) {
                child.setVisible(false);
                child.setDisable(true);
            }
            cloud.setVisible(false);
            cloud.setDisable(true);
        }
        if(isClientTurn) {
            endTurnButton.setVisible(true);
            endTurnButton.setDisable(false);
            chooseCloudLabel.setVisible(false);
        }
    }
}

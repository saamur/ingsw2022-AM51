package it.polimi.ingsw.client.GUI.controllers;

import it.polimi.ingsw.client.modeldata.CloudData;
import it.polimi.ingsw.client.modeldata.CloudManagerData;
import it.polimi.ingsw.constants.ConstantsGUI;
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

import static it.polimi.ingsw.constants.ConstantsGUI.CHARACTERS;
import static it.polimi.ingsw.constants.ConstantsGUI.GAMEBOARD;

public class CloudController extends PageController implements Initializable {
    @FXML
    AnchorPane anchorCloud0;
    @FXML
    AnchorPane anchorCloud1;
    @FXML
    AnchorPane anchorCloud2;

    @FXML
    ImageView student1cloud0;
    @FXML
    ImageView student2cloud0;
    @FXML
    ImageView student3cloud0;
    @FXML
    ImageView student4cloud0;
    List<ImageView> studentsCloud0;


    @FXML
    ImageView student1cloud1;
    @FXML
    ImageView student2cloud1;
    @FXML
    ImageView student3cloud1;
    @FXML
    ImageView student4cloud1;
    List<ImageView> studentsCloud1;

    @FXML
    ImageView student1cloud2;
    @FXML
    ImageView student2cloud2;
    @FXML
    ImageView student3cloud2;
    @FXML
    ImageView student4cloud2;
    List<ImageView> studentsCloud2;

    @FXML
    Label errorLabel; //TODO create

    int numOfClouds;
    int numOfStudentsPerCloud;
    CloudData[] modelClouds;


    List<AnchorPane> clouds = new ArrayList<>();
    Map<Integer, List<ImageView>> students = new HashMap<>();
    Boolean[] availableClouds = new Boolean[3];

    @FXML Button endTurnButton;
    @FXML Button activateCharacterButton; //FIXME potrebbe non servire se gli faccio vedere SEMPRE la GameBoard
    @FXML Label chooseCloudLabel;

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


    public void back(ActionEvent event) {
        gui.setCurrScene(GAMEBOARD);
    }

    public void setClouds(CloudManagerData cloudManagerData, boolean expertModeEnabled) { //FIXME potrebbe esserci un problema dopo disconnetto e riconnetto
        this.modelClouds = cloudManagerData.clouds();
        numOfClouds = modelClouds.length;
        for (int i = 0; i < numOfClouds; i++) {
            availableClouds[i] = true;
        }
        numOfStudentsPerCloud = GameConstants.getNumStudentsPerCloud(numOfClouds);
        studentsCloud0 = new ArrayList<>(Arrays.asList(student1cloud0, student2cloud0, student3cloud0));
        studentsCloud1 = new ArrayList<>(Arrays.asList(student1cloud1, student2cloud1, student3cloud1));

        //FIXME better way??
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
            studentsCloud2 = new ArrayList<>(Arrays.asList(student1cloud2, student2cloud2, student3cloud2, student4cloud2));
            studentsCloud0.add(student4cloud0);
            studentsCloud1.add(student4cloud1);
            students.put(2, studentsCloud2);
        }

        students.put(0, studentsCloud0);
        students.put(1, studentsCloud1);

        for (int i = 0; i < modelClouds.length; i++) {
            if(modelClouds[i].picked()){
                clouds.get(i).setOpacity(0.5);
                availableClouds[i] = false;
                for(int j=0; j<numOfStudentsPerCloud; j++)
                    students.get(i).get(j).setVisible(false);
            } else
                modifyCloud(i, modelClouds[i]);
        }

        this.expertModeEnabled = expertModeEnabled;
    }

    public void selectCloud(MouseEvent event) {
        //FIXME if CLOUD CHOOSING
        for (int i = 0; i < numOfClouds; i++) {
            if (clouds.get(i).getChildren().contains((Node) event.getSource())) {
                if (availableClouds[i]) {
                    sendMessage(new ChosenCloudMessage(i));
                } else {
                    errorLabel.setText("This cloud has already been chosen, choose another cloud");
                }
                break;
            }
        }
    }

    public void updateClouds(CloudManagerData cloudManager) {
        CloudData[] newClouds = cloudManager.clouds();
        for (int i = 0; i < newClouds.length; i++) {
            if (!newClouds[i].equals(modelClouds[i])) {
                modifyCloud(i, newClouds[i]);
            }
            availableClouds[i] = true;
            clouds.get(i).setOpacity(1);
        }
    }

    public void updateCloud(CloudData cloud){
        if(cloud.picked()){
            clouds.get(cloud.cloudIndex()).setOpacity(0.5);
            availableClouds[cloud.cloudIndex()] = false;
            endTurnButton.setVisible(true);
            endTurnButton.setDisable(false);
        }
    }

    public void updateTurnState(TurnState turnState){
        if(turnState == TurnState.CLOUD_CHOOSING || turnState == TurnState.END_TURN){
            if(expertModeEnabled) {
                activateCharacterButton.setVisible(true);
                activateCharacterButton.setDisable(false);
            }
            chooseCloudLabel.setVisible(true);
        } else {
            endTurnButton.setVisible(false);
            endTurnButton.setDisable(true);
            activateCharacterButton.setVisible(false);
            activateCharacterButton.setDisable(true);
            endTurnButton.setDisable(true);
            chooseCloudLabel.setVisible(false);
        }
    }

    public void modifyCloud(int cloudIndex, CloudData cloudData) {
        int i = 0; //index of student that has to be set
        Map<Clan, Integer> studentsData = cloudData.students();
        for (Clan clan : Clan.values()) {
            while (studentsData.get(clan) > 0) {
                students.get(cloudIndex).get(i).setImage(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(clan)).toExternalForm()));
                students.get(cloudIndex).get(i).setVisible(true);
                studentsData.put(clan, studentsData.get(clan) - 1);
                i++;
            }
        }
    }

    //FIXME togliere endturn per i non currPlayers
    public void endTurn(ActionEvent actionEvent) {
        sendMessage(new EndTurnMessage());
    }

    public void activateCharacterCard(ActionEvent actionEvent) {
        //((CharactersController) gui.getControllers().get(CHARACTERS)).setPreviousScene(CLOUDS);
        gui.setCurrScene(CHARACTERS);
        //TODO
    }

    public void lastRound(){
        for(AnchorPane cloud : clouds){
            for(Node child : cloud.getChildren()) {
                child.setVisible(false);
                child.setDisable(true);
            }
            cloud.setVisible(false);
            cloud.setDisable(true);
        }
        //FIXME show only if currPlayer
        endTurnButton.setVisible(true);
        endTurnButton.setDisable(false);
        chooseCloudLabel.setVisible(false);
    }
}

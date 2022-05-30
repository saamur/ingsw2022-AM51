package it.polimi.ingsw.client.GUI;


import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.constants.ConstantsGUI;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.model.Clan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.ConstantsGUI.*;

//FIXME USE computed_size
public class SchoolBoardController extends PageController implements Initializable {

    @FXML TabPane tabPane;

    private String nickname;
    private SchoolBoard[] schoolBoards;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setNickname (String nickname) {
        this.nickname = nickname;
    }

    public void initialSetUp (PlayerData[] playersData) {

        schoolBoards = new SchoolBoard[playersData.length];

        for (int i = 0; i < playersData.length; i++) {

            schoolBoards[i] = new SchoolBoard(playersData.length);
            schoolBoards[i].nickname = playersData[i].getNickname();

            ImageView school = new ImageView(new Image(getClass().getResource("/png/Plancia_DEF.png").toExternalForm()));
            school.setX(SCHOOL_COORDINATE_X);
            school.setY(SCHOOL_COORDINATE_Y);
            school.setFitHeight(SCHOOL_HEIGHT);
            school.setFitWidth(SCHOOL_WIDTH);

            AnchorPane anchorPane = new AnchorPane();
            //qualcosa di strano
            anchorPane.getChildren().add(school);

            for (Clan clan : Clan.values()) {
                for (int j = 0; j < GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER; j++) {
                    ImageView student = new ImageView(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(clan)).toExternalForm()));
                    student.setFitWidth(ConstantsGUI.getStudentSize());
                    student.setFitHeight(ConstantsGUI.getStudentSize());
                    student.setX(ConstantsGUI.getFirstStudentX() + j*ConstantsGUI.getHorizontalDistanceStudents());
                    student.setY(ConstantsGUI.getFirstStudentY(clan));
                    schoolBoards[i].studentsChamber.get(clan)[j] = student;
                }
                ImageView professor = new ImageView(new Image(getClass().getResource(ConstantsGUI.getImagePathProfessor(clan)).toExternalForm()));
                professor.setFitWidth(ConstantsGUI.getProfessorSize());
                professor.setFitHeight(ConstantsGUI.getProfessorSize());
                professor.setX(ConstantsGUI.getProfessorX());
                professor.setY(ConstantsGUI.getProfessorY(clan));
                professor.setRotate(270);
                schoolBoards[i].professors.put(clan, professor);
                anchorPane.getChildren().addAll(schoolBoards[i].studentsChamber.get(clan));
                anchorPane.getChildren().add(professor);

                if (playersData[i].getNickname().equals(nickname)) {
                    Pane table = new Pane();
                    table.setId(clan.name());
                    table.setMaxWidth(ConstantsGUI.getTableWidth());
                    table.setMinWidth(ConstantsGUI.getTableWidth());
                    table.setMaxHeight(ConstantsGUI.getTableHeight());
                    table.setMinHeight(ConstantsGUI.getTableHeight());
                    table.setLayoutX(ConstantsGUI.getTableX());
                    table.setLayoutY(ConstantsGUI.getTableY(clan));
                    schoolBoards[i].tables.put(clan, table);
                    anchorPane.getChildren().add(table);
                }

            }

            for (int j = 0; j < GameConstants.getNumInitialStudentsHall(playersData.length); j++) {
                ImageView student = new ImageView(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(Clan.DRAGONS)).toExternalForm()));
                student.setFitWidth(ConstantsGUI.getStudentSize());
                student.setFitHeight(ConstantsGUI.getStudentSize());
                student.setX(ConstantsGUI.getHallFirstStudentX() - (j%2)*ConstantsGUI.getHallStudentHorizontalDistance());
                student.setY(ConstantsGUI.getHallFirstStudentY() - (j/2)*ConstantsGUI.getHallStudentVerticalDistance());
                schoolBoards[i].studentsHall[j] = student;
            }

            anchorPane.getChildren().addAll(schoolBoards[i].studentsHall);

            for (int j = 0; j < GameConstants.getNumInitialTowers(playersData.length); j++) {
                ImageView tower = new ImageView(new Image(getClass().getResource(ConstantsGUI.getImagePathTower(playersData[i].getColorOfTowers())).toExternalForm()));
                tower.setFitWidth(ConstantsGUI.getTowerWidth());
                tower.setFitHeight(ConstantsGUI.getTowerHeight());
                tower.setX(ConstantsGUI.getFirstTowerX() + (j%2)*ConstantsGUI.getTowerHorizontalDistance());
                tower.setY(ConstantsGUI.getFirstTowerY() + (j/2)*ConstantsGUI.getTowerVerticalDistance());
                schoolBoards[i].towers[j] = tower;
            }

            anchorPane.getChildren().addAll(schoolBoards[i].towers);

            ImageView coin = new ImageView(new Image(getClass().getResource(COIN_IMAGE).toExternalForm()));
            coin.setFitWidth(ConstantsGUI.getCoinWidth());
            coin.setFitHeight(getCoinHeight());
            coin.setX(ConstantsGUI.getCoinX());
            coin.setY(ConstantsGUI.getCoinY());
            anchorPane.getChildren().add(coin);
            Label label = new Label();
            label.setLayoutX(130);
            label.setLayoutY(439);
            schoolBoards[i].coins = label;
            anchorPane.getChildren().add(label);

            Tab tab = new Tab(nickname.equals(playersData[i].getNickname()) ? "YOU" : playersData[i].getNickname(), anchorPane);
            tabPane.getTabs().add(tab);

            //todo monete

        }

    }

    //mettere nomi giocatori sulle tab
    //mettere giocatori nelle plance?
    public void back(ActionEvent event){
        gui.setCurrScene(GAMEBOARD);
    }

    public void setSchoolBoards(PlayerData[] playersData) {

        initialSetUp(playersData);

        for (PlayerData p : playersData)
            setSchoolBoard(p);

    }

    public void setSchoolBoard (PlayerData playerData) {

        for (SchoolBoard schoolBoard : schoolBoards) {
            if (playerData.getNickname().equals(schoolBoard.nickname)) {
                for (Clan c : Clan.values()) {
                    schoolBoard.professors.get(c).setVisible(!playerData.getChamberData().professors().get(c));
                    for (int i = 0; i < 10; i++)
                        schoolBoard.studentsChamber.get(c)[i].setVisible(playerData.getChamberData().students().get(c) > i);
                }

                int hallStudentIndex = 0;
                for (Clan clan : Clan.values()) {
                    for (int numStudentsClan = playerData.getHallData().students().get(clan); numStudentsClan > 0; numStudentsClan--) {
                        schoolBoard.studentsHall[hallStudentIndex].setImage(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(clan)).toExternalForm()));
                        schoolBoard.studentsHall[hallStudentIndex].setVisible(true);
                        hallStudentIndex++;
                    }
                }
                for (; hallStudentIndex < schoolBoard.studentsHall.length; hallStudentIndex++)
                    schoolBoard.studentsHall[hallStudentIndex].setVisible(false);

                for (int i = 0; i < schoolBoard.towers.length; i++)
                    schoolBoard.towers[i].setVisible(playerData.getNumberOfTowers() > i);
                schoolBoard.coins.setText(playerData.getChamberData().coins() == 0 ? "No coins" : (playerData.getChamberData().coins() + " coin" + (playerData.getChamberData().coins()==1?"":"s")));
                break;
            }
        }

    }

    private class SchoolBoard {

        String nickname;
        ImageView[] studentsHall;
        Map<Clan, ImageView[]> studentsChamber;
        Map<Clan, Pane> tables;
        Map<Clan, ImageView> professors;
        ImageView[] towers;
        Label coins;

        SchoolBoard(int numPlayers) {
            professors = new EnumMap<>(Clan.class);
            studentsHall = new ImageView[GameConstants.getNumInitialStudentsHall(numPlayers)];
            studentsChamber = new EnumMap<>(Clan.class);
            tables = new EnumMap<>(Clan.class);
            for (Clan clan : Clan.values())
                studentsChamber.put(clan, new ImageView[GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER]);
            towers = new ImageView[GameConstants.getNumInitialTowers(numPlayers)];

        }

    }

}

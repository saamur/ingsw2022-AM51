package it.polimi.ingsw.client.GUI;


import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.constants.ConstantsGUI;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.model.Clan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

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

    //mettere nomi giocatori sulle tab
    //mettere giocatori nelle plance?
    public void back(ActionEvent event){
        gui.setCurrScene(GAMEBOARD);
    }

    public void setSchoolBoards(PlayerData[] playersData) {

        schoolBoards = new SchoolBoard[playersData.length];

        for (int i = 0; i < playersData.length; i++) {

            schoolBoards[i] = new SchoolBoard(playersData.length);

            ImageView school = new ImageView(new Image(getClass().getResource("/png/Plancia_DEF.png").toExternalForm()));
            school.setX(SCHOOL_COORDINATE_X);
            school.setY(SCHOOL_COORDINATE_Y);
            school.setFitHeight(SCHOOL_HEIGHT);
            school.setFitWidth(SCHOOL_WIDTH);

            AnchorPane anchorPane = new AnchorPane();
            //qualcosa di strano
            anchorPane.getChildren().add(school);

            System.out.println(getClass());

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
                schoolBoards[i].professors.put(clan, professor);
                anchorPane.getChildren().addAll(schoolBoards[i].studentsChamber.get(clan));
                anchorPane.getChildren().add(professor);
                //todo aggiungere il rettangolo sul tavolo
            }

            //todo hall e torri

            //Tab tab = new Tab(nickname.equals(playersData[i].getNickname()) ? "YOU" : playersData[i].getNickname(), anchorPane);
            Tab tab = new Tab("gg", anchorPane);
            tabPane.getTabs().add(tab);


        }






//        schoolBoards[0] = new SchoolBoard(playersData.length);
//        schoolBoards[1] = new SchoolBoard(playersData.length);
//        schoolBoards[2] = new SchoolBoard(playersData.length);
//
//
//        for (int i = 0; i < playersData.length; i++) {
//            schoolBoards[i].nickname = playersData[i].getNickname();
//            schoolBoards[i].tab.setText(nickname.equals(playersData[i].getNickname()) ? "YOU" : playersData[i].getNickname());
//        }
//
//        for (PlayerData p : playersData)
//            setSchoolBoard(p);

    }

    public void setSchoolBoard (PlayerData playerData) {

        for (SchoolBoard schoolBoard : schoolBoards) {
            if (playerData.getNickname().equals(schoolBoard.nickname)) {
                for (Clan c : Clan.values()) {
                    schoolBoard.professors.get(c).setVisible(playerData.getChamberData().professors().get(c));
                    for (int i = 0; i < 10; i++)
                        schoolBoard.studentsChamber.get(c)[i].setVisible(playerData.getChamberData().students().get(c) > i);
                }
                for (int i = 0; i < 8; i++)
                    schoolBoard.towers[i].setVisible(playerData.getNumberOfTowers() > i);
                break;
            }
        }

    }


    private class SchoolBoard {

        String nickname;
        ImageView[] studentsHall;
        Map<Clan, ImageView[]> studentsChamber;
        Map<Clan, ImageView> professors;
        ImageView[] towers;

        SchoolBoard(int numPlayers) {
            professors = new EnumMap<>(Clan.class);
            studentsHall = new ImageView[GameConstants.getNumInitialStudentsHall(numPlayers)];
            studentsChamber = new EnumMap<>(Clan.class);
            for (Clan clan : Clan.values())
                studentsChamber.put(clan, new ImageView[GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER]);
            towers = new ImageView[GameConstants.getNumInitialTowers(numPlayers)];

        }

    }

}

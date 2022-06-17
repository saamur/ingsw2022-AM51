package it.polimi.ingsw.client.GUI.controllers;


import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.constants.ConstantsGUI;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.messages.gamemessages.ApplyCharacterCardEffectMessage2;
import it.polimi.ingsw.messages.gamemessages.MoveStudentToChamberMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.ConstantsGUI.*;

//FIXME USE computed_size
//FIXME add label for instructions over tabPane, and currPlayer (who's turn is it?)
//FIXME togliere coins se non è expert
public class SchoolBoardController extends PageController implements Initializable {

    @FXML
    private AnchorPane baseAnchor;
    @FXML
    private TabPane tabPane;

    private String nickname;
    private SchoolBoard[] schoolBoards;

    private ImageView island;
    private Pane islandPane;
    Label instructions = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void initialSetUp(PlayerData[] playersData) {

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

            anchorPane.getChildren().add(school);

            for (Clan clan : Clan.values()) {
                for (int j = 0; j < GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER; j++) {
                    ImageView student = new ImageView(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(clan)).toExternalForm()));
                    student.setFitWidth(ConstantsGUI.getStudentSize());
                    student.setFitHeight(ConstantsGUI.getStudentSize());
                    student.setX(ConstantsGUI.getFirstStudentX() + j * ConstantsGUI.getHorizontalDistanceStudents());
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
                    table.setId(clan.name() + "' table");
                    table.setMaxWidth(ConstantsGUI.getTableWidth());
                    table.setMinWidth(ConstantsGUI.getTableWidth());
                    table.setMaxHeight(ConstantsGUI.getTableHeight());
                    table.setMinHeight(ConstantsGUI.getTableHeight());
                    table.setLayoutX(ConstantsGUI.getTableX());
                    table.setLayoutY(ConstantsGUI.getTableY(clan));
                    table.getStylesheets().add("/style.css");
                    table.getStyleClass().add("tables");
                    table.setStyle("-my-background: " + ConstantsGUI.getColorClan(clan));
                    schoolBoards[i].tables.put(clan, table);
                    anchorPane.getChildren().add(table);


                }

            }

            if (playersData[i].getNickname().equals(nickname)) {
                islandPane = new Pane();
                island = new ImageView(new Image(getClass().getResource("/png/islands/island1.png").toExternalForm()));
                island.setPreserveRatio(true);
                island.setFitHeight(ConstantsGUI.getIslandHeightSchoolBoards());
                island.setFitWidth(ConstantsGUI.getIslandHeightSchoolBoards());
                islandPane.getChildren().add(island);
                island.fitHeightProperty().bind(islandPane.heightProperty());
                islandPane.setLayoutX(ConstantsGUI.getIslandXSchoolBoards());
                islandPane.setLayoutY(ConstantsGUI.getIslandYSchoolBoards());
                islandPane.setPrefSize(ConstantsGUI.getIslandHeightSchoolBoards(), ConstantsGUI.getIslandHeightSchoolBoards());
                island.setVisible(false);
                islandPane.setId("islandPane");
                islandPane.getStylesheets().add("/style.css");
                anchorPane.getChildren().add(islandPane);

                instructions.setPrefWidth(baseAnchor.getPrefWidth());
                instructions.setWrapText(true);
                instructions.setAlignment(Pos.CENTER);
                instructions.setCenterShape(true);
                anchorPane.getChildren().add(instructions);
                instructions.setLayoutX(0);
                instructions.setLayoutY(5);
                instructions.setVisible(false);


            }

            for (int j = 0; j < GameConstants.getNumInitialStudentsHall(playersData.length); j++) {
                ImageView student = new ImageView(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(Clan.DRAGONS)).toExternalForm()));
                student.setFitWidth(ConstantsGUI.getStudentSize());
                student.setFitHeight(ConstantsGUI.getStudentSize());
                student.setX(ConstantsGUI.getHallFirstStudentX() - (j % 2) * ConstantsGUI.getHallStudentHorizontalDistance());
                student.setY(ConstantsGUI.getHallFirstStudentY() - (j / 2) * ConstantsGUI.getHallStudentVerticalDistance());
                schoolBoards[i].studentsHall[j] = student;
            }

            anchorPane.getChildren().addAll(schoolBoards[i].studentsHall);

            for (int j = 0; j < GameConstants.getNumInitialTowers(playersData.length); j++) {
                ImageView tower = new ImageView(new Image(getClass().getResource(ConstantsGUI.getImagePathTower(playersData[i].getColorOfTowers())).toExternalForm()));
                tower.setFitWidth(ConstantsGUI.getTowerWidth());
                tower.setFitHeight(ConstantsGUI.getTowerHeight());
                tower.setX(ConstantsGUI.getFirstTowerX() + (j % 2) * ConstantsGUI.getTowerHorizontalDistance());
                tower.setY(ConstantsGUI.getFirstTowerY() + (j / 2) * ConstantsGUI.getTowerVerticalDistance());
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
    public void back(ActionEvent event) {
        gui.setCurrScene(GAMEBOARD);
    }

    public void setSchoolBoards(PlayerData[] playersData) {

        initialSetUp(playersData);

        for (PlayerData p : playersData)
            setSchoolBoard(p);

    }

    public void setSchoolBoard(PlayerData playerData) {

        for (SchoolBoard schoolBoard : schoolBoards) {
            if (playerData.getNickname().equals(schoolBoard.nickname)) {
                for (Clan c : Clan.values()) {
                    schoolBoard.professors.get(c).setVisible(playerData.getChamberData().professors().get(c));
                    for (int i = 0; i < 10; i++)
                        schoolBoard.studentsChamber.get(c)[i].setVisible(playerData.getChamberData().students().get(c) > i);
                }

                int hallStudentIndex = 0;
                for (Clan clan : Clan.values()) {
                    for (int numStudentsClan = playerData.getHallData().students().get(clan); numStudentsClan > 0; numStudentsClan--) {
                        schoolBoard.studentsHall[hallStudentIndex].setImage(new Image(getClass().getResource(ConstantsGUI.getImagePathStudent(clan)).toExternalForm()));
                        schoolBoard.studentsHall[hallStudentIndex].setId(clan.name());
                        schoolBoard.studentsHall[hallStudentIndex].setVisible(true);
                        hallStudentIndex++;
                    }
                }
                for (; hallStudentIndex < schoolBoard.studentsHall.length; hallStudentIndex++)
                    schoolBoard.studentsHall[hallStudentIndex].setVisible(false);

                for (int i = 0; i < schoolBoard.towers.length; i++)
                    schoolBoard.towers[i].setVisible(playerData.getNumberOfTowers() > i);
                schoolBoard.coins.setText(playerData.getChamberData().coins() == 0 ? "No coins" : (playerData.getChamberData().coins() + " coin" + (playerData.getChamberData().coins() == 1 ? "" : "s")));
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

    //FIXME devo mettere l'attivazione della azioni prima, disabilitarle subito ed abilitarle solo se in STUDENT_MOVING
    public void setCurrPlayer(String nicknameCurrPlayer) {
        if (nicknameCurrPlayer.equals(this.nickname) && gui.getTurnState() == TurnState.STUDENT_MOVING) {
            for (SchoolBoard schoolBoard : schoolBoards) {
                if (this.nickname.equals(schoolBoard.nickname)) {
                    islandPane.setVisible(true);
                    island.setVisible(true);
                    island.setDisable(false);
                    islandPane.setDisable(false);
                    instructions.setText("It is now your turn to move the students\nYou can drag and drop the students on the hall or on the island image");
                    instructions.setVisible(true);
                    islandPane.getStylesheets().add("/style.css");
                    for (Clan clan : Clan.values())
                        enableDropOnNode(schoolBoard.tables.get(clan));
                    for (ImageView student : schoolBoard.studentsHall) {
                        student.setDisable(false);
                        makeDraggable(student);
                    }
                    enableDropOnNode(islandPane);
                }
            }
        }
    }

    /* This method is for the Chambers and for the Island */
    private void enableDropOnNode(Node target) {
        target.setOnDragDropped(e -> {
                    boolean success = false;
                    Dragboard db = e.getDragboard();
                    if (db.hasString()) {
                        Clan clan = Clan.valueOf(db.getString());
                        if (target.getId().contains("table"))
                            sendMessage(new MoveStudentToChamberMessage(clan)); //FIXME it adds student to right chamber even if chamber is the wrong color
                        else {
                            ((IslandsPageController) gui.getControllers().get(ISLANDS)).setDroppedStudent(clan);
                            target.getStyleClass().removeIf(style -> style.equals("image-drag-over"));
                            Platform.runLater(() -> gui.setCurrScene(ISLANDS));
                        }
                        success = true;
                    }
                    e.setDropCompleted(success);
                    e.consume();
                }
        );
        target.setOnDragOver(e -> {
            if (e.getGestureSource() != island)
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);

            e.consume();
        });

        target.setOnDragEntered(e -> {
            System.out.println("onDragEntered");
            /* show to the user that it is an actual gesture target */
            if (e.getGestureSource() != target &&
                    e.getDragboard().hasString()) {
                target.getStyleClass().add(target.getId().contains("table") ? "tables-drag-over" : "image-drag-over");
            }
            e.consume();
        });

        target.setOnDragExited(e -> {
            target.getStyleClass().removeIf(style -> style.equals("tables-drag-over") || style.equals("image-drag-over"));
            e.consume();
        });
    }

    private void makeDraggable(Node source) {
        source.setOnMouseDragged(e -> {
            System.out.println("onMouseDragged");
            e.setDragDetect(true);
        });

        source.setOnDragDetected(e -> {
            System.out.println("onDragDetected");

            source.setStyle("-fx-opacity: 0.7");
            System.out.println("Da DragDetected: " + source.getStyle());

            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(source.getId());
            db.setContent(content);

            e.consume();

        });

        source.setOnDragDone(e -> {
            source.setStyle(null);
            e.consume();
        });

    }

    public void disableStudentMoving() {
        //TODO disable all of the dragging
        //penso basti disabilitare gli studenti
        for (SchoolBoard schoolBoard : schoolBoards) {
            if (schoolBoard.nickname.equals(this.nickname)) {
                for (ImageView student : schoolBoard.studentsHall)
                    student.setDisable(true);
                for (Clan clan : Clan.values()) {
                    schoolBoard.tables.get(clan).getStyleClass().removeIf(style -> style.equals("tables-drag-over"));
                }
            }
        }
        island.setVisible(false);
        island.setDisable(true);
        islandPane.getStyleClass().removeIf(style -> style.equals("image-drag-over"));
        instructions.setVisible(false);
    }

    Button doneButton = new Button("Done");
    public void setCharacter(CharacterID character){
        switch(character){
            case MINSTREL -> {

            }
            case JESTER -> {

            }

        }
    }
}
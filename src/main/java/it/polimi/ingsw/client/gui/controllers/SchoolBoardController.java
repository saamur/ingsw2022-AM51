package it.polimi.ingsw.client.gui.controllers;


import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.constants.GuiConstants;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.messages.gamemessages.ApplyCharacterCardEffectMessage2;
import it.polimi.ingsw.messages.gamemessages.MoveStudentToChamberMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.GuiConstants.*;


/**
 * SchoolBoardController displays the players's School Boards on GUI
 */
public class SchoolBoardController extends PageController implements Initializable {

    @FXML
    private AnchorPane baseAnchor;
    @FXML
    private TabPane tabPane;

    private String nickname;
    private SchoolBoard[] schoolBoards;

    private ImageView island;
    private Pane islandPane;
    private Pane jesterStudentsPane;
    private List<ImageView> jesterStudentsImages;
    private final Label instructions = new Label();
    @FXML private Label turnLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * This method is called by {@link #setSchoolBoard(PlayerData)}:
     * <ul>
     *     <li>It creates the schoolboards calling {@link #initialSchoolBoardSetting(AnchorPane, PlayerData, int) initialSchoolBoardSetting(AnchorPane, PlayerData, int)}.</li>
     *     <li>It creates the necessary components for the STUDENT_MOVING phase with {@link #studentMovingSetUp(AnchorPane)}</li>
     *     <li>It sets up the components for the characters if expert mode is enabled via {@link #characterSetUp(AnchorPane)}</li>
     * </ul>
     * @param playersData the playersData to populate the schoolboards with.
     * @param expertMode true if expertmode is enabled
     */
    public void initialSetUp(PlayerData[] playersData, boolean expertMode) {

        schoolBoards = new SchoolBoard[playersData.length];

        for (int i = 0; i < playersData.length; i++) {

            AnchorPane anchorPane = new AnchorPane();

            schoolBoards[i] = initialSchoolBoardSetting(anchorPane, playersData[i], playersData.length);

            if (playersData[i].getNickname().equals(nickname)) {

                studentMovingSetUp(anchorPane);

                instructions.setPrefWidth(baseAnchor.getPrefWidth());
                instructions.setWrapText(true);
                instructions.setAlignment(Pos.CENTER);
                instructions.setCenterShape(true);
                anchorPane.getChildren().add(instructions);
                instructions.setLayoutX(0);
                instructions.setLayoutY(5);
                instructions.setVisible(false);

                if(expertMode)
                    characterSetUp(anchorPane);
            }



            Label label = new Label();
            label.setLayoutX(130);
            label.setLayoutY(439);
            schoolBoards[i].coins = label;
            anchorPane.getChildren().add(label);
            if (expertMode) {
                ImageView coin = new ImageView(new Image(getClass().getResource(COIN_IMAGE).toExternalForm()));
                coin.setFitWidth(GuiConstants.getCoinWidth());
                coin.setFitHeight(getCoinHeight());
                coin.setX(GuiConstants.getCoinX());
                coin.setY(GuiConstants.getCoinY());
                anchorPane.getChildren().add(coin);
            }
            else {
                label.setVisible(false);
                label.setDisable(true);
            }

            Tab tab = new Tab(nickname.equals(playersData[i].getNickname()) ? "YOU" : playersData[i].getNickname(), anchorPane);
            tabPane.getTabs().add(tab);

        }

    }

    /**
     * Creates a SchoolBoard according to the data that has been received by the GUI from the Server
     * @param anchorPane the anchorPane which is the parent of every other graphical component
     * @param playerData the player data from the model
     * @param numOfPlayers number of players playing
     * @return the SchoolBoard that has been created
     * @see PlayerData
     */
    public SchoolBoard initialSchoolBoardSetting(AnchorPane anchorPane, PlayerData playerData, int numOfPlayers) {
        SchoolBoard schoolBoard = new SchoolBoard(playerData.getNickname(), numOfPlayers);

        ImageView school = new ImageView(new Image(getClass().getResource("/png/Plancia_DEF.png").toExternalForm()));
        school.setX(SCHOOL_COORDINATE_X);
        school.setY(SCHOOL_COORDINATE_Y);
        school.setFitHeight(SCHOOL_HEIGHT);
        school.setFitWidth(SCHOOL_WIDTH);


        anchorPane.getChildren().add(school);

        for (Clan clan : Clan.values()) {
            for (int j = 0; j < GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER; j++) {
                Pane pane = new Pane();
                ImageView student = new ImageView(new Image(getClass().getResource(GuiConstants.getImagePathStudent(clan)).toExternalForm()));
                student.setFitWidth(GuiConstants.getStudentSize());
                student.setFitHeight(GuiConstants.getStudentSize());

                pane.setLayoutX(GuiConstants.getFirstStudentX() + j * GuiConstants.getHorizontalDistanceStudents());
                pane.setLayoutY(GuiConstants.getFirstStudentY(clan));
                pane.setPrefSize(GuiConstants.getStudentSize(), GuiConstants.getStudentSize());

                schoolBoard.studentsChamber.get(clan)[j] = student;

                student.setDisable(true);
                pane.getChildren().add(student);
                anchorPane.getChildren().add(pane);
            }
            ImageView professor = new ImageView(new Image(getClass().getResource(GuiConstants.getImagePathProfessor(clan)).toExternalForm()));
            professor.setFitWidth(GuiConstants.getProfessorSize());
            professor.setFitHeight(GuiConstants.getProfessorSize());
            professor.setX(GuiConstants.getProfessorX());
            professor.setY(GuiConstants.getProfessorY(clan));
            professor.setRotate(270);
            schoolBoard.professors.put(clan, professor);
            //anchorPane.getChildren().addAll(schoolBoards[i].studentsChamber.get(clan));
            anchorPane.getChildren().add(professor);

            if (playerData.getNickname().equals(nickname)) {
                Pane table = new Pane();
                table.setId(clan.name() + "' table");
                table.setMaxWidth(GuiConstants.getTableWidth());
                table.setMinWidth(GuiConstants.getTableWidth());
                table.setMaxHeight(GuiConstants.getTableHeight());
                table.setMinHeight(GuiConstants.getTableHeight());
                table.setLayoutX(GuiConstants.getTableX());
                table.setLayoutY(GuiConstants.getTableY(clan));
                table.getStylesheets().add("/style.css");
                table.getStyleClass().add("tables");
                table.setStyle("-my-background: " + GuiConstants.getColorClan(clan));
                schoolBoard.tables.put(clan, table);
                anchorPane.getChildren().add(table);


            }

        }

        for (int j = 0; j < GameConstants.getNumInitialStudentsHall(numOfPlayers); j++) {
            Pane pane = new Pane();
            ImageView student = new ImageView(new Image(getClass().getResource(GuiConstants.getImagePathStudent(Clan.DRAGONS)).toExternalForm()));
            student.setFitWidth(GuiConstants.getStudentSize());
            student.setFitHeight(GuiConstants.getStudentSize());
            pane.setLayoutX(GuiConstants.getHallFirstStudentX() - (j % 2) * GuiConstants.getHallStudentHorizontalDistance());
            pane.setLayoutY(GuiConstants.getHallFirstStudentY() - (j / 2) * GuiConstants.getHallStudentVerticalDistance());
            pane.setPrefSize(GuiConstants.getStudentSize(), GuiConstants.getStudentSize());

            schoolBoard.studentsHall[j] = student;
            student.setDisable(true);
            pane.getChildren().add(student);
            anchorPane.getChildren().add(pane);
        }

        for (int j = 0; j < GameConstants.getNumInitialTowers(numOfPlayers); j++) {
            ImageView tower = new ImageView(new Image(getClass().getResource(GuiConstants.getImagePathTower(playerData.getColorOfTowers())).toExternalForm()));
            tower.setFitWidth(GuiConstants.getTowerWidth());
            tower.setFitHeight(GuiConstants.getTowerHeight());
            tower.setX(GuiConstants.getFirstTowerX() + (j % 2) * GuiConstants.getTowerHorizontalDistance());
            tower.setY(GuiConstants.getFirstTowerY() + (j / 2) * GuiConstants.getTowerVerticalDistance());
            schoolBoard.towers[j] = tower;
        }

        anchorPane.getChildren().addAll(schoolBoard.towers);
        return schoolBoard;
    }

    /**
     * It creates the island Image on which the students can be dropped on
     * @param anchorPane the AnchorPane parent to all the components of the scene
     */
    public void studentMovingSetUp(AnchorPane anchorPane){
        islandPane = new Pane();
        island = new ImageView(new Image(getClass().getResource("/png/islands/island1.png").toExternalForm()));
        island.setPreserveRatio(true);
        island.setFitHeight(GuiConstants.getIslandHeightSchoolBoards());
        island.setFitWidth(GuiConstants.getIslandHeightSchoolBoards());
        islandPane.getChildren().add(island);
        island.fitHeightProperty().bind(islandPane.heightProperty());
        islandPane.setLayoutX(GuiConstants.getIslandXSchoolBoards());
        islandPane.setLayoutY(GuiConstants.getIslandYSchoolBoards());
        islandPane.setPrefSize(GuiConstants.getIslandHeightSchoolBoards(), GuiConstants.getIslandHeightSchoolBoards());
        island.setVisible(false);
        islandPane.setId("islandPane");
        islandPane.getStylesheets().add("/style.css");
        anchorPane.getChildren().add(islandPane);
    }

    /**
     * It creates the jesterStudentsPane on which the students from the Jester CharacterCard are displayed.
     * @param anchorPane parent to which the jesterStudentPane is added
     */
    public void characterSetUp(AnchorPane anchorPane){
        jesterStudentsPane = new Pane();
        jesterStudentsPane.setLayoutX(400);
        jesterStudentsPane.setLayoutY(407);
        jesterStudentsPane.setPrefSize(200, 100);
        jesterStudentsPane.setStyle("-fx-border-color: WHITE; -fx-border-width: 5");
        jesterStudentsPane.setVisible(false);
        jesterStudentsPane.setDisable(true);
        anchorPane.getChildren().add(jesterStudentsPane);
        Label jesterStudentsLabel = new Label("Jester students:");
        jesterStudentsLabel.setLayoutX(6);
        jesterStudentsLabel.setLayoutY(2);
        jesterStudentsPane.getChildren().add(jesterStudentsLabel);

        jesterStudentsImages = new ArrayList<>();
        for(int j=0; j<6; j++){
            ImageView image = new ImageView();
            Pane pane = new Pane();
            image.setFitWidth(35);
            image.setFitHeight(35);
            pane.setPrefSize(35, 35);
            pane.setLayoutX(14 + j/2 * 66);
            pane.setLayoutY((j%2 ==0 ? 16 : 56));
            pane.getChildren().add(image);
            jesterStudentsPane.getChildren().add(pane);
            jesterStudentsImages.add(image);
            image.setDisable(true);
        }

        anchorPane.getChildren().add(doneButton);
        doneButton.setLayoutX(750);
        doneButton.setLayoutY(10);

        doneButton.setOnAction( e -> {
            int numChosenStudents1 = 0;
            int numChosenStudents2 = 0;

            for(Clan c : Clan.values()){
                numChosenStudents1 += students1.get(c);
                numChosenStudents2 += students2.get(c);
            }
            if(numChosenStudents1 == numChosenStudents2) {
                sendMessage(new ApplyCharacterCardEffectMessage2(-1, students1, students2));
                doneButton.setOpacity(1);
            } else{
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Wrong parameters");
                    alert.setHeaderText("There are not enough students chosen");
                    alert.getDialogPane().setContent(new Label("The number of students chosen from the source does not correspond to the number chosen at the destination"));
                    alert.showAndWait();
                });
            }
            students1 = new EnumMap<>(Clan.class);
            students2 = new EnumMap<>(Clan.class);
            for(Clan c : Clan.values()){
                students1.put(c, 0);
                students2.put(c, 0);
            }
            for(ImageView image : jesterStudentsImages)
                image.getParent().getStyleClass().removeIf(style -> style.equals("selected-character-pieces"));
            for (SchoolBoard schoolBoard : schoolBoards) { //this way the style is reset after done is clicked
                if (schoolBoard.nickname.equals(this.nickname)) {
                    for (ImageView student : schoolBoard.studentsHall)
                        student.getParent().getStyleClass().removeIf(style -> style.equals("selected-character-pieces"));
                    for(Clan c: Clan.values()) {
                        for(ImageView student : schoolBoard.studentsChamber.get(c))
                            student.getParent().getStyleClass().removeIf(style -> style.equals("selected-character-pieces"));
                    }
                }
            }
            for(Clan c: Clan.values())
                numClicks.put(c, 0);
        });
        doneButton.setVisible(false);
        doneButton.setDisable(true);

        numClicks = new EnumMap<>(Clan.class);
        for(Clan c: Clan.values())
            numClicks.put(c, 0);
    }

    /**
     * Takes the user back to the GameBoard when the "Go to GameBoard" button is clicked
     * @see ActionEvent
     */
    public void back(ActionEvent event) {
        gui.setCurrScene(GAMEBOARD);
    }

    /**
     * It does the setting up of the school boards according to the playersData received as a parameter. It calls {@link #initialSetUp(PlayerData[], boolean)} and {@link #setSchoolBoard(PlayerData)}
     * @param playersData array containing the playersData from the model
     * @param expertMode true if expert mode is enabled
     */
    public void setSchoolBoards(PlayerData[] playersData, boolean expertMode) {

        initialSetUp(playersData, expertMode);

        for (PlayerData p : playersData)
            setSchoolBoard(p);

    }

    /**
     * Method displays a schoolboard according to the playerData
     * @param playerData the data of the player from the model
     */
    public void setSchoolBoard(PlayerData playerData) {

        for (SchoolBoard schoolBoard : schoolBoards) {
            if (playerData.getNickname().equals(schoolBoard.nickname)) {
                for (Clan c : Clan.values()) {
                    schoolBoard.professors.get(c).setVisible(playerData.getChamberData().professors().get(c));
                    for (int i = 0; i < 10; i++) {
                        schoolBoard.studentsChamber.get(c)[i].setVisible(playerData.getChamberData().students().get(c) > i);
                        schoolBoard.studentsChamber.get(c)[i].setId(c.name());
                    }
                }

                int hallStudentIndex = 0;
                for (Clan clan : Clan.values()) {
                    for (int numStudentsClan = playerData.getHallData().students().get(clan); numStudentsClan > 0; numStudentsClan--) {
                        schoolBoard.studentsHall[hallStudentIndex].setImage(new Image(getClass().getResource(GuiConstants.getImagePathStudent(clan)).toExternalForm()));
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

    /**
     * This class represents the SchoolBoards' rendering in the GUI
     */
    private class SchoolBoard {

        final String nickname;
        final ImageView[] studentsHall;
        final Map<Clan, ImageView[]> studentsChamber;
        final Map<Clan, Pane> tables;
        final Map<Clan, ImageView> professors;
        final ImageView[] towers;
        Label coins;

        SchoolBoard(String name, int numPlayers) {
            nickname = name;
            professors = new EnumMap<>(Clan.class);
            studentsHall = new ImageView[GameConstants.getNumInitialStudentsHall(numPlayers)];
            studentsChamber = new EnumMap<>(Clan.class);
            tables = new EnumMap<>(Clan.class);
            for (Clan clan : Clan.values())
                studentsChamber.put(clan, new ImageView[GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER]);
            towers = new ImageView[GameConstants.getNumInitialTowers(numPlayers)];

        }

    }

    public void setCurrPlayer(String nicknameCurrPlayer) {
        if (nicknameCurrPlayer.equals(this.nickname) && gui.getTurnState() == TurnState.STUDENT_MOVING) {
            for (SchoolBoard schoolBoard : schoolBoards) {
                if (this.nickname.equals(schoolBoard.nickname)) {
                    islandPane.setVisible(true);
                    island.setVisible(true);
                    island.setDisable(false);
                    islandPane.setDisable(false);
                    instructions.setText("It is now your turn to move the students\nYou can drag and drop the students on the chamber or on the island image");
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

    /**
     * Enables the node to have an object dropped on it, it is used on the Chamber and on the Island to drop students during the StudentMoving phase
     * @param target the node onto which the dropping can happen
     */
    private void enableDropOnNode(Node target) {
        target.setOnDragDropped(e -> {
                    boolean success = false;
                    Dragboard db = e.getDragboard();
                    if (db.hasString()) {
                        Clan clan = Clan.valueOf(db.getString());
                        if (target.getId().contains("table"))
                            sendMessage(new MoveStudentToChamberMessage(clan));
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

    /**
     * This method enables dragging on a node, and disables clicking on it
     * @param source @see Node
     */
    private void makeDraggable(Node source) {
        source.setOnMouseDragged(e -> e.setDragDetect(true));

        source.setOnDragDetected(e -> {
            source.setStyle("-fx-opacity: 0.7");

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

        source.setOnMouseClicked(Event::consume);

    }

    private Map<Clan, Integer> numClicks;

    /**
     * This method disables the ability to drag the node and enables the ability to show a border when a click is detected on the node
     * @see Node
     */
    private void makeClickable(Node node){
        node.setOnMouseClicked( e -> {
                    boolean isSource;
                    int chosenStudents1 = 0;
                    int chosenStudents2 = 0;
                    for(Clan c : Clan.values()) {
                        chosenStudents1 += students1.get(c);
                        chosenStudents2 += students2.get(c);
                    }

                    if(node instanceof ImageView)
                        isSource = jesterStudentsImages.contains(node);
                    else
                        isSource = true;


                    if((isSource && chosenStudents1 < numMaxStudentsExchanged) || ((!isSource) && chosenStudents2 < numMaxStudentsExchanged)){
                        Clan clan = null;
                        if(node instanceof ImageView) {
                            if(!jesterStudentsImages.contains(node)){
                                isSource = false;
                            }
                            if( ! node.getParent().getStyleClass().contains("selected-character-pieces")) {
                                node.getParent().getStylesheets().add("/style.css");
                                node.getParent().getStyleClass().add("selected-character-pieces");
                                clan = (Clan.valueOf(node.getId()));
                            }

                        } else if (node instanceof Pane && node.getId().contains("table")){
                            for(Clan c: Clan.values()){
                                if(node.getId().contains(c.name())){
                                    numClicks.put(c, numClicks.get(c)+1);
                                    for(SchoolBoard schoolBoard: schoolBoards) {
                                        if(schoolBoard.nickname.equals(this.nickname)) {
                                            if(schoolBoard.studentsChamber.get(c)[numClicks.get(c)-1].isVisible()){
                                                schoolBoard.studentsChamber.get(c)[numClicks.get(c)-1].setDisable(false);
                                                if(! schoolBoard.studentsChamber.get(c)[numClicks.get(c)-1].getParent().getStylesheets().contains("selected-character-pieces")) {
                                                    schoolBoard.studentsChamber.get(c)[numClicks.get(c) - 1].getParent().getStylesheets().add("/style.css");
                                                    schoolBoard.studentsChamber.get(c)[numClicks.get(c) - 1].getParent().getStyleClass().add("selected-character-pieces");
                                                    clan = c;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(clan != null){
                            if(isSource){
                                students1.put(clan, students1.get(clan) + 1);
                            } else {
                                students2.put(clan, students2.get(clan) + 1);
                            }
                        }
                    } else {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Capacity reached");
                            alert.setHeaderText("Student cannot be chosen");
                            alert.setContentText("No more students can be exchanged from this location");
                            alert.showAndWait();
                        });
                    }


                }
        );

        node.setOnMouseDragged(Event::consume);

        node.setOnDragDetected(Event::consume);

        node.setOnDragDone(Event::consume);
    }

    /**
     * Disables the ability to drag and drop students to the chamber and to the islands
     */
    public void disableStudentMoving() {
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

    private final Button doneButton = new Button("Done");
    private Map<Clan, Integer> students1;
    private Map<Clan, Integer> students2;
    private int numMaxStudentsExchanged;

    /**
     * Method is used to set the SchoolBoards when MINSTREL or JESTER is activated.
     * It enables the ability to click on the students to choose them for the character effect.
     * @param character activated character
     * @param player player who has activated it
     * @param students the students on the card if the character is JESTER
     */
    public void setCharacter(CharacterID character, String player, Map<Clan, Integer> students){
        students1 = new EnumMap<>(Clan.class);
        students2 = new EnumMap<>(Clan.class);
        for(Clan c : Clan.values()){
            students1.put(c, 0);
            students2.put(c, 0);
        }
        if(player.equals(nickname)) {
            for (SchoolBoard schoolBoard : schoolBoards) {
                if (schoolBoard.nickname.equals(this.nickname)) {
                    for (ImageView student : schoolBoard.studentsHall) {
                        student.setDisable(false);
                        makeClickable(student);
                    }
                    if(character == CharacterID.MINSTREL){
                        for(Clan c: Clan.values())
                            makeClickable(schoolBoard.tables.get(c));
                    }
                }
            }
            doneButton.setVisible(true);
            doneButton.setDisable(false);
            switch (character) {
                case MINSTREL -> numMaxStudentsExchanged = 2;
                case JESTER -> {
                    int i = 0;
                    jesterStudentsPane.setVisible(true);
                    jesterStudentsPane.setDisable(false);
                    while (i < students.size()) {
                        for (Clan c : Clan.values()) {
                            int numStudents = students.get(c);
                            while (numStudents > 0) {
                                jesterStudentsImages.get(i).setImage(new Image(getClass().getResource(GuiConstants.getImagePathStudent(c)).toExternalForm()));
                                jesterStudentsImages.get(i).setVisible(true);
                                jesterStudentsImages.get(i).setId(c.name());
                                makeClickable(jesterStudentsImages.get(i));
                                jesterStudentsImages.get(i).setDisable(false);
                                numStudents--;
                                i++;
                            }
                        }
                    }
                    numMaxStudentsExchanged = 3;
                }

            }
        }
        instructions.setText("You have activated " + character.name() + ". Select the student and then the button \"Done\" when you have finished\n" + "You have up to " + numMaxStudentsExchanged + " students to exchange between the Hall and the " + (character == CharacterID.MINSTREL? "Chamber" : "Character Card"));
        instructions.setVisible(true);
    }

    /**
     * Disables all the visual effects related to the characters
     */
    public void disableCharactersActions(){
        doneButton.setDisable(true);
        doneButton.setVisible(false);

        students1 = null;
        students2 = null;

        instructions.setText("");
        for(ImageView image : jesterStudentsImages){
            image.setDisable(true);
            image.setVisible(false);
            image.getParent().getStyleClass().removeIf(style -> style.equals("selected-character-pieces"));

        }
        jesterStudentsPane.setVisible(false);
        jesterStudentsPane.setDisable(true);

        for (SchoolBoard schoolBoard : schoolBoards) {
            if (schoolBoard.nickname.equals(this.nickname)) {
                for (ImageView student : schoolBoard.studentsHall) {
                    student.setDisable(true);
                    student.getParent().getStyleClass().removeIf(style -> style.equals("selected-character-pieces"));
                }
                for(Clan c: Clan.values()) {
                    schoolBoard.tables.get(c).setOnMouseClicked(Event::consume);
                    for(ImageView student : schoolBoard.studentsChamber.get(c))
                        student.getParent().getStyleClass().removeIf(style -> style.equals("selected-character-pieces"));
                }
            }
        }
    }

    /**
     * Method sets the text in the turnLabel Label so that the client knows who's turn it is during the STUDENT_MOVING phase
     * @param message the message to assign to the turnLabel
     */
    public void changeTurnLabel(String message){
        turnLabel.setText(message);
    }
}
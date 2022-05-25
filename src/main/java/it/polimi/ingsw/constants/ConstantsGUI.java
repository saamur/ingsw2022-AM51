package it.polimi.ingsw.constants;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.TowerColor;

import java.lang.ref.PhantomReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;

import static it.polimi.ingsw.model.Clan.*;
import static it.polimi.ingsw.model.charactercards.CharacterID.*;
import static it.polimi.ingsw.model.player.TowerColor.*;

public class ConstantsGUI {
    public static final String CHARACTERS = "characterCards.fxml";
    public static final String CONNECTION = "connection.fxml";
    public static final String CLOUDS = "clouds.fxml";
    public static final String DECK = "deck.fxml";
    public static final String DISCONNECTION = "disconnection.fxml";
    public static final String GAMEBOARD = "gameBoard.fxml";
    public static final String GAMESELECTION = "gameSelection.fxml";
    public static final String ISLANDS = "islands.fxml";
    public static final String SCHOOLBOARDS = "schoolBoards.fxml";
    public static final String WAITINGROOM = "waitingRoom.fxml";






    public static final Map<CharacterID, String> CHARACTERS_IMAGES = new EnumMap<>(CharacterID.class);
    static{
        CHARACTERS_IMAGES.put(MONK, "/png/characters/CarteTOT_front.jpg");
        CHARACTERS_IMAGES.put(FARMER, "/png/characters/CarteTOT_front12.jpg");
        CHARACTERS_IMAGES.put(HERALD, "/png/characters/CarteTOT_front2.jpg");
        CHARACTERS_IMAGES.put(POSTMAN, "/png/characters/CarteTOT_front3.jpg");
        CHARACTERS_IMAGES.put(GRANDMA, "/png/characters/CarteTOT_front4.jpg");
        CHARACTERS_IMAGES.put(CENTAUR, "/png/characters/CarteTOT_front5.jpg");
        CHARACTERS_IMAGES.put(JESTER, "/png/characters/CarteTOT_front6.jpg");
        CHARACTERS_IMAGES.put(KNIGHT, "/png/characters/CarteTOT_front7.jpg");
        CHARACTERS_IMAGES.put(MUSHROOMPICKER, "/png/characters/CarteTOT_front8.jpg");
        CHARACTERS_IMAGES.put(MINSTREL, "/png/characters/CarteTOT_front9.jpg");
        CHARACTERS_IMAGES.put(PRINCESS, "/png/characters/CarteTOT_front10.jpg");
        CHARACTERS_IMAGES.put(THIEF, "/png/characters/CarteTOT_front11.jpg");
    }

    private static final Map<Clan, String> STUDENT_IMAGES = new EnumMap<>(Clan.class); //FIXME studentS?
    static{
        STUDENT_IMAGES.put(PIXIES, "/png/clan/students/student_yellow.png");
        STUDENT_IMAGES.put(UNICORNS, "/png/clan/students/student_blue.png");
        STUDENT_IMAGES.put(TOADS, "/png/clan/students/student_green.png");
        STUDENT_IMAGES.put(DRAGONS, "/png/clan/students/student_red.png");
        STUDENT_IMAGES.put(FAIRIES, "/png/clan/students/student_pink.png");
    }

    private static final Map<Clan, String> PROFESSOR_IMAGES = new EnumMap<>(Clan.class); //FIXME professorS?
    static{
        PROFESSOR_IMAGES.put(PIXIES, "/png/clan/professors/teacher_yellow.png");
        PROFESSOR_IMAGES.put(UNICORNS, "/png/clan/professors/teacher_blue.png");
        PROFESSOR_IMAGES.put(TOADS, "/png/clan/professors/teacher_green.png");
        PROFESSOR_IMAGES.put(DRAGONS, "/png/clan/professors/teacher_red.png");
        PROFESSOR_IMAGES.put(FAIRIES, "/png/clan/professors/teacher_pink.png");
    }

    private static final Map<TowerColor, String> TOWERS_IMAGES = new EnumMap<>(TowerColor.class);
    static {
        TOWERS_IMAGES.put(WHITE, "/png/towers/white_tower.png");
        TOWERS_IMAGES.put(BLACK, "/png/towers/black_tower.png");
        TOWERS_IMAGES.put(GRAY, "/png/towers/gray_tower.png");
    }

    public static String getImagePathStudent (Clan clan) {
        return STUDENT_IMAGES.get(clan);
    }

    public static String getImagePathTower (TowerColor towerColor) {
        return TOWERS_IMAGES.get(towerColor);
    }

    public static String getImagePathProfessor (Clan clan) {
        return PROFESSOR_IMAGES.get(clan);
    }




    public static final double SCHOOL_COORDINATE_X = 0;
    public static final double SCHOOL_COORDINATE_Y = 45;
    public static final double SCHOOL_HEIGHT = 362;
    public static final double SCHOOL_WIDTH = 844;
    private static final double STUDENT_SCHOOL_HEIGHT_RATIO = (double) 37/362;
    private static final double FIRST_STUDENT_X_SCHOOL_HEIGHT_RATIO = (double) 157/362;
    private static final double FIRST_TOP_STUDENT_Y_SCHOOL_HEIGHT_RATIO = (double) 89/724;
    private static final double VERTICAL_DISTANCE_STUDENTS_SCHOOL_HEIGHT_RATIO = (double) 59/362;
    private static final Map<Clan, Double> FIRST_STUDENT_Y;
    private static final double HORIZONTAL_DISTANCE_STUDENTS_SCHOOL_HEIGHT_RATIO = (double) 1/9;

    static {
        FIRST_STUDENT_Y = new EnumMap<>(Clan.class);
        FIRST_STUDENT_Y.put(TOADS, SCHOOL_COORDINATE_Y + FIRST_TOP_STUDENT_Y_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT);
        FIRST_STUDENT_Y.put(DRAGONS, SCHOOL_COORDINATE_Y + (FIRST_TOP_STUDENT_Y_SCHOOL_HEIGHT_RATIO + VERTICAL_DISTANCE_STUDENTS_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
        FIRST_STUDENT_Y.put(PIXIES, SCHOOL_COORDINATE_Y + (FIRST_TOP_STUDENT_Y_SCHOOL_HEIGHT_RATIO + 2*VERTICAL_DISTANCE_STUDENTS_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
        FIRST_STUDENT_Y.put(FAIRIES, SCHOOL_COORDINATE_Y + (FIRST_TOP_STUDENT_Y_SCHOOL_HEIGHT_RATIO + 3*VERTICAL_DISTANCE_STUDENTS_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
        FIRST_STUDENT_Y.put(UNICORNS, SCHOOL_COORDINATE_Y + (FIRST_TOP_STUDENT_Y_SCHOOL_HEIGHT_RATIO + 4*VERTICAL_DISTANCE_STUDENTS_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
    }

    public static double getStudentSize() {
        return STUDENT_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getFirstStudentX () {
        return SCHOOL_COORDINATE_X + FIRST_STUDENT_X_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getFirstStudentY (Clan clan) {
        return FIRST_STUDENT_Y.get(clan);
    }

    public static double getHorizontalDistanceStudents () {
        return HORIZONTAL_DISTANCE_STUDENTS_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    private static final double TABLE_WIDTH_SCHOOL_HEIGHT_RATIO = (double) 205/181;
    private static final double TABLE_HEIGHT_SCHOOL_HEIGHT_RATIO = (double) 20/181;
    private static final double TABLE_X_SCHOOL_HEIGHT_RATIO = (double) 75/181;
    private static final double TOP_TABLE_Y_SCHOOL_HEIGHT_RATIO = (double) 21/181;
    private static final double VERTICAL_DISTANCE_TABLE_SCHOOL_HEIGHT_RATIO = (double) 24/181;
    private static final Map<Clan, Double> TABLE_Y;

    static {
        TABLE_Y = new EnumMap<>(Clan.class);
        TABLE_Y.put(TOADS, SCHOOL_COORDINATE_Y + TOP_TABLE_Y_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT);
        TABLE_Y.put(DRAGONS, SCHOOL_COORDINATE_Y + (TOP_TABLE_Y_SCHOOL_HEIGHT_RATIO + VERTICAL_DISTANCE_TABLE_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
        TABLE_Y.put(PIXIES, SCHOOL_COORDINATE_Y + (TOP_TABLE_Y_SCHOOL_HEIGHT_RATIO + 2*VERTICAL_DISTANCE_TABLE_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
        TABLE_Y.put(FAIRIES, SCHOOL_COORDINATE_Y + (TOP_TABLE_Y_SCHOOL_HEIGHT_RATIO + 3*VERTICAL_DISTANCE_TABLE_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
        TABLE_Y.put(UNICORNS, SCHOOL_COORDINATE_Y + (TOP_TABLE_Y_SCHOOL_HEIGHT_RATIO + 4*VERTICAL_DISTANCE_TABLE_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
    }

    public static double getTableWidth() {
        return TABLE_WIDTH_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getTableHeight() {
        return TABLE_HEIGHT_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getTableX() {
        return SCHOOL_COORDINATE_X + TABLE_X_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getTableY(Clan clan) {
        return TABLE_Y.get(clan);
    }



    private static final double PROFESSOR_SCHOOL_HEIGHT_RATIO = (double) 21/181;
    private static final double PROFESSOR_X_SCHOOL_HEIGHT_RATIO = (double) 595/362;
    private static final double TOP_PROFESSOR_Y_SCHOOL_HEIGHT_RATIO = (double) 83/724;
    private static final double VERTICAL_DISTANCE_PROFESSOR_SCHOOL_HEIGHT_RATIO = (double) 59/362;
    private static final Map<Clan, Double> PROFESSOR_Y;

    static {
        PROFESSOR_Y = new EnumMap<>(Clan.class);
        PROFESSOR_Y.put(TOADS, SCHOOL_COORDINATE_Y + TOP_PROFESSOR_Y_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT);
        PROFESSOR_Y.put(DRAGONS, SCHOOL_COORDINATE_Y + (TOP_PROFESSOR_Y_SCHOOL_HEIGHT_RATIO + VERTICAL_DISTANCE_PROFESSOR_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
        PROFESSOR_Y.put(PIXIES, SCHOOL_COORDINATE_Y + (TOP_PROFESSOR_Y_SCHOOL_HEIGHT_RATIO + 2*VERTICAL_DISTANCE_PROFESSOR_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
        PROFESSOR_Y.put(FAIRIES, SCHOOL_COORDINATE_Y + (TOP_PROFESSOR_Y_SCHOOL_HEIGHT_RATIO + 3*VERTICAL_DISTANCE_PROFESSOR_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
        PROFESSOR_Y.put(UNICORNS, SCHOOL_COORDINATE_Y + (TOP_PROFESSOR_Y_SCHOOL_HEIGHT_RATIO + 4*VERTICAL_DISTANCE_PROFESSOR_SCHOOL_HEIGHT_RATIO) * SCHOOL_HEIGHT);
    }

    public static double getProfessorSize() {
        return PROFESSOR_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getProfessorX () {
        return SCHOOL_COORDINATE_X + PROFESSOR_X_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }
    public static double getProfessorY (Clan clan) {
        return PROFESSOR_Y.get(clan);
    }

    private static final double HALL_FIRST_STUDENT_X_SCHOOL_HEIGHT_RATIO = (double) 39/181;
    private static final double HALL_FIRST_STUDENT_Y_SCHOOL_HEIGHT_RATIO = (double) 327/362;
    private static final double HALL_STUDENTS_HORIZONTAL_DISTANCE_SCHOOL_HEIGHT_RATIO = (double) 25/181;
    private static final double HALL_STUDENTS_VERTICAL_DISTANCE_SCHOOL_HEIGHT_RATIO = (double) 59/362;

    public static double getHallFirstStudentX () {
        return SCHOOL_COORDINATE_X + HALL_FIRST_STUDENT_X_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getHallFirstStudentY () {
        return SCHOOL_COORDINATE_X + HALL_FIRST_STUDENT_Y_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getHallStudentHorizontalDistance () {
        return HALL_STUDENTS_HORIZONTAL_DISTANCE_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getHallStudentVerticalDistance () {
        return HALL_STUDENTS_VERTICAL_DISTANCE_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    private static final double TOWER_HEIGHT_SCHOOL_HEIGHT_RATIO = (double) 41/181;
    private static final double TOWER_WIDTH_SCHOOL_HEIGHT_RATIO = (double) 21/181;
    private static final double FIRST_TOWER_X_SCHOOL_HEIGHT_RATIO = (double) 687/362;
    private static final double FIRST_TOWER_Y_SCHOOL_HEIGHT_RATIO = (double) 10/181;
    private static final double TOWER_VERTICAL_DISTANCE_SCHOOL_HEIGHT_RATIO = (double) 30/181;
    private static final double TOWER_HORIZONTAL_DISTANCE_SCHOOL_HEIGHT_RATIO = (double) 32/181;

    public static double getTowerHeight () {
        return TOWER_HEIGHT_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getTowerWidth () {
        return TOWER_WIDTH_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getFirstTowerX () {
        return SCHOOL_COORDINATE_X + FIRST_TOWER_X_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getFirstTowerY () {
        return SCHOOL_COORDINATE_Y + FIRST_TOWER_Y_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getTowerVerticalDistance () {
        return TOWER_VERTICAL_DISTANCE_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getTowerHorizontalDistance () {
        return TOWER_HORIZONTAL_DISTANCE_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    private static final double COIN_WIDTH_SCHOOL_HEIGHT_RATIO = (double) 143/905;
    private static final double COIN_HEIGHT_SCHOOL_HEIGHT_RATIO = (double) 131/724;
    private static final double COIN_X_SCHOOL_HEIGHT_RATIO = (double) 45/362;
    private static final double COIN_Y_SCHOOL_HEIGHT_RATIO = (double) 185/181;
    public static final String COIN_IMAGE = "/png/Moneta_base.png";

    public static double getCoinWidth () {
        return COIN_WIDTH_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getCoinHeight () {
        return COIN_HEIGHT_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getCoinX () {
        return SCHOOL_COORDINATE_X + COIN_X_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

    public static double getCoinY () {
        return SCHOOL_COORDINATE_Y + COIN_Y_SCHOOL_HEIGHT_RATIO * SCHOOL_HEIGHT;
    }

}

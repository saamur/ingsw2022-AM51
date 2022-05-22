package it.polimi.ingsw.constants;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.TowerColor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

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

    public static final Map<Clan, String> STUDENT_IMAGES = new EnumMap<>(Clan.class); //FIXME studentS?
    static{
        STUDENT_IMAGES.put(PIXIES, "/png/clan/students/student_yellow.png");
        STUDENT_IMAGES.put(UNICORNS, "/png/clan/students/student_blue.png");
        STUDENT_IMAGES.put(TOADS, "/png/clan/students/student_green.png");
        STUDENT_IMAGES.put(DRAGONS, "/png/clan/students/student_red.png");
        STUDENT_IMAGES.put(FAIRIES, "/png/clan/students/student_pink.png");
    }

    public static final Map<TowerColor, String> TOWERS_IMAGES = new EnumMap<>(TowerColor.class);
    static {
        TOWERS_IMAGES.put(WHITE, "/png/towers/white_tower.png");
        TOWERS_IMAGES.put(BLACK, "/png/towers/black_tower.png");
        TOWERS_IMAGES.put(GRAY, "/png/towers/gray_tower.png");
    }
}

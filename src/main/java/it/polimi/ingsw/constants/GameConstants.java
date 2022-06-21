package it.polimi.ingsw.constants;

import it.polimi.ingsw.model.charactercards.CharacterID;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;

import static it.polimi.ingsw.model.charactercards.CharacterID.*;
import static it.polimi.ingsw.model.charactercards.CharacterID.THIEF;

/**
 * GameConstants class contains the main constants used in the Erianys game
 *
 */
public class GameConstants {

    public static final int NUM_INITIAL_STUDENTS_PER_CLAN_BAG = 24;
    public static final int NUM_INITIAL_ISLANDS = 12;
    public static final int MIN_NUM_ISLANDS = 3;
    public static final int MAX_NUM_STUDENTS_PER_CLAN_CHAMBER = 10;
    public static final int NUM_AVAILABLE_CHARACTER_CARDS = 3;

    private static final Set<Integer> NUM_OF_SUPPORTED_PLAYERS;

    static {
        NUM_OF_SUPPORTED_PLAYERS = new HashSet<>();
        NUM_OF_SUPPORTED_PLAYERS.add(2);
        NUM_OF_SUPPORTED_PLAYERS.add(3);
    }

    private static final Map<Integer, Integer> NUM_STUDENTS_PER_CLOUD;

    static {
        NUM_STUDENTS_PER_CLOUD = new HashMap<>();
        NUM_STUDENTS_PER_CLOUD.put(2, 3);
        NUM_STUDENTS_PER_CLOUD.put(3, 4);
    }

    private static final Map<Integer, Integer> NUM_INITIAL_STUDENTS_HALL;
    public static final int MAX_STUDENTS_HALL;

    static {
        NUM_INITIAL_STUDENTS_HALL = new HashMap<>();
        NUM_INITIAL_STUDENTS_HALL.put(2, 7);
        NUM_INITIAL_STUDENTS_HALL.put(3, 9);
        MAX_STUDENTS_HALL = Collections.max(NUM_INITIAL_STUDENTS_HALL.values());
    }

    private static final Map<Integer, Integer> NUM_INITIAL_TOWERS;

    static {
        NUM_INITIAL_TOWERS = new HashMap<>();
        NUM_INITIAL_TOWERS.put(2, 8);
        NUM_INITIAL_TOWERS.put(3, 6);
    }

    private static final Map<CharacterID, Integer> NUM_INITIAL_PROHIBITION_CARDS_CHARACTER_CARD;

    static {
        NUM_INITIAL_PROHIBITION_CARDS_CHARACTER_CARD = new EnumMap<>(CharacterID.class);
        NUM_INITIAL_PROHIBITION_CARDS_CHARACTER_CARD.put(CharacterID.GRANDMA, 4);
    }

    private final static Map<CharacterID, Integer> NUM_ADDITIONAL_STEPS_CHARACTER_CARD;

    static {
        NUM_ADDITIONAL_STEPS_CHARACTER_CARD = new EnumMap<>(CharacterID.class);
        NUM_ADDITIONAL_STEPS_CHARACTER_CARD.put(CharacterID.POSTMAN, 2);
    }

    private static final Map<CharacterID, Integer> NUM_INITIAL_STUDENTS_STUDENT_MOVER_CHARACTER_CARD;

    static {
        NUM_INITIAL_STUDENTS_STUDENT_MOVER_CHARACTER_CARD = new EnumMap<>(CharacterID.class);
        NUM_INITIAL_STUDENTS_STUDENT_MOVER_CHARACTER_CARD.put(CharacterID.MONK, 4);
        NUM_INITIAL_STUDENTS_STUDENT_MOVER_CHARACTER_CARD.put(CharacterID.JESTER, 6);
        NUM_INITIAL_STUDENTS_STUDENT_MOVER_CHARACTER_CARD.put(CharacterID.MINSTREL, 0);
        NUM_INITIAL_STUDENTS_STUDENT_MOVER_CHARACTER_CARD.put(CharacterID.PRINCESS, 4);
        NUM_INITIAL_STUDENTS_STUDENT_MOVER_CHARACTER_CARD.put(CharacterID.THIEF, 0);
    }

    public static boolean supportsNumberOfPlayers (int numberOfPlayers) {
        return NUM_OF_SUPPORTED_PLAYERS.contains(numberOfPlayers);
    }

    public static int getNumStudentsPerCloud (int numPlayers) {
        return NUM_STUDENTS_PER_CLOUD.get(numPlayers);
    }

    public static int getNumInitialStudentsHall (int numPlayers) {
        return NUM_INITIAL_STUDENTS_HALL.get(numPlayers);
    }

    public static int getNumInitialTowers (int numPlayers) {
        return NUM_INITIAL_TOWERS.get(numPlayers);
    }

    public static int getNumInitialProhibitionCardsCharacterCard(CharacterID characterID) {
        return NUM_INITIAL_PROHIBITION_CARDS_CHARACTER_CARD.get(characterID);
    }

    public static int getNumAdditionalStepsCharacterCard (CharacterID characterID) {
        return NUM_ADDITIONAL_STEPS_CHARACTER_CARD.get(characterID);
    }

    public static int getNumInitialStudentsStudentMoverCharacterCard (CharacterID characterID) {
        return NUM_INITIAL_STUDENTS_STUDENT_MOVER_CHARACTER_CARD.get(characterID);
    }

    private static final Map<CharacterID, String> DESCRIPTION_CHARACTERS;

    static{
        DESCRIPTION_CHARACTERS = new EnumMap<>(CharacterID.class);
        DESCRIPTION_CHARACTERS.put(MONK, "Take 1 student from this card and place it on an Island of your choice.\n");
        DESCRIPTION_CHARACTERS.put(FARMER, "During this turn, you take control of any number of Professor even if you have the same number of Students as the player who currently controls them\n");
        DESCRIPTION_CHARACTERS.put(HERALD, "Choose an Island and resolve the Island as if Mother Nature had ended her movement there.\nMother Nature will still move and the Island where she ends her movement will also be resolved\n");
        DESCRIPTION_CHARACTERS.put(POSTMAN, "You may move Mother Nature up to 2 additional Island than is indicated by the Assistant card you've played\n");
        DESCRIPTION_CHARACTERS.put(GRANDMA, "Place a No Entry tile on an Island of your choice.\nThe first time that Mother Nature ends her movement there, put the Prohibition Card back onto this card and DO NOT calculate influence on that Island, or place any Towers \n");
        DESCRIPTION_CHARACTERS.put(CENTAUR, "When resolving a Conquering on an Island the towers do not count towards influence\n");
        DESCRIPTION_CHARACTERS.put(JESTER, "You may take up to 3 Students from this card and replace them with the same number of Students from your Hall\n");
        DESCRIPTION_CHARACTERS.put(KNIGHT, "During the influence calculation this turn, you count as having 2 more influence\n");
        DESCRIPTION_CHARACTERS.put(MUSHROOMPICKER, "Choose a color of Student: during the influence calculation this turn, that color adds no influence\n");
        DESCRIPTION_CHARACTERS.put(MINSTREL, "You may exchange up to 2 Students between your Hall and your Chamber\n");
        DESCRIPTION_CHARACTERS.put(PRINCESS, "Take 1 Student from this card and place it in your Chamber.");
        DESCRIPTION_CHARACTERS.put(THIEF, "Choose a type of Student: every player (including yourself) must return 3 Students of that type from their Chamber. If any player has fewer than 3 Students of that type, return as many Students as they have\n");
    }

    public static String getDescriptionCharacter(CharacterID character){
        return DESCRIPTION_CHARACTERS.get(character);
    }

}

package it.polimi.ingsw.constants;

import it.polimi.ingsw.model.charactercards.CharacterID;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.*;

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

}

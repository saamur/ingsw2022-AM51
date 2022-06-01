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
        DESCRIPTION_CHARACTERS.put(MONK, "Pick 1 Student from the Card and place it on an Island of your choosing.\n"); /*Then, pick 1 Student from the Bag and mettilo su questa carta*/
        DESCRIPTION_CHARACTERS.put(FARMER, "During this turn, you will be in control of the Professors even if in your Chamber you have the same number of Students of the Player who has them in that moment");
        DESCRIPTION_CHARACTERS.put(HERALD, "Pick an Island and calculate the influence as if Mother Nature had terminated her movement there.\nIn this Turn Mother Nature will move as usual and on the Island where she will end her movement the influence will be calculated as normal");
        DESCRIPTION_CHARACTERS.put(POSTMAN, "You can move Mother Nature up to 2 additional Islands than what is indicated on the Assistant's card that you have chosen");
        DESCRIPTION_CHARACTERS.put(GRANDMA, "Place a Prohibition Card on an Island of you choosing.\nThe first time that Mother Nature ends her movement there, the Prohibition Card will be placed back on the Character Card without calculating the influence or placing any Towers");
        DESCRIPTION_CHARACTERS.put(CENTAUR, "Durante il conteggio dell'influenza su un'Isola (o su un gruppo di Isole), le Torri presenti non vengono calcolate");
        DESCRIPTION_CHARACTERS.put(JESTER, "Puoi prendere fino a 3 studenti da questa carta e scambiarli con altrettanti studenti presenti nel tuo ingresso");
        DESCRIPTION_CHARACTERS.put(KNIGHT, "In questo turno durante il calcolo dell'influenza hai due punti di influenza addizionali");
        DESCRIPTION_CHARACTERS.put(MUSHROOMPICKER, "Scegli il colore di uno Studente; in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza");
        DESCRIPTION_CHARACTERS.put(MINSTREL, "Puoi scambiare fra loro fino a 2 studenti presenti nella tua sala e nel tuo ingresso");
        DESCRIPTION_CHARACTERS.put(PRINCESS, "Prendi uno studente da questa carta e piazzalo nella tua sala. Poi pesca uno studente dal sacchetto e posizionalo su questa carta");
        DESCRIPTION_CHARACTERS.put(THIEF, "Scegli il colore di uno Studente; ogni giocatore (incluso te) deve rimettere nel sacchetto 3 Studenti di quel colore presenti nella tua sala. Chi avesse meno di 3 studenti di quel colore, rimetterà tutti quelli che ha");
    } //FIXME translate

    public static String getDescriptionCharacter(CharacterID character){
        return DESCRIPTION_CHARACTERS.get(character);
    }

}

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
        DESCRIPTION_CHARACTERS.put(MONK, "Prendi 1 studente dalla carta e piazzalo su un'Isola a tua scelta\nPoi, pesca 1 Studente del sacchetto e mettilo su questa carta");
        DESCRIPTION_CHARACTERS.put(FARMER, "Durante questo turno, prendi il controllo dei Professori anche se nella tua sala hai lo stesso numero di studenti del giocatore che li controlla in quel momento");
        DESCRIPTION_CHARACTERS.put(HERALD, "Scegli un'Isola e calcola la maggioranza come se Madre Natura avesse terminato il suo movimento lì.\nIn questo turno Madre Natura si muoverà come di consueto e nell'Isola dove terminerà il suo movimento la maggioranza verrà normalmente calcolata");
        DESCRIPTION_CHARACTERS.put(POSTMAN, "Puoi muovere Madre Natura fino a 2 Isole addizionali rispetto a quanto indicato sulla carta Assistente che hai giocato");
        DESCRIPTION_CHARACTERS.put(GRANDMA, "Piazza una Tessera Divieto su un'Isola a tua scelta.\nLa prima volta che Madre Natura termina il suo movimento lì, rimettete la Tessera Divieto sulla carta senza calcolare l'influenza su quest'Isola nel piazzare Torri");
        DESCRIPTION_CHARACTERS.put(CENTAUR, "Durante il conteggio dell'influenza su un'Isola (o su un gruppo di Isole), le Torri presenti non vengono calcolate");
        DESCRIPTION_CHARACTERS.put(JESTER, "Puoi prendere fino a 3 studenti da questa carta e scambiarli con altrettanti studenti presenti nel tuo ingresso");
        DESCRIPTION_CHARACTERS.put(KNIGHT, "In questo turno durante il calcolo dell'influenza hai due punti di influenza addizionali");
        DESCRIPTION_CHARACTERS.put(MUSHROOMPICKER, "Scegli il colore di uno Studente; in questo turno, durante il calcolo dell'influenza quel colore non fornisce influenza");
        DESCRIPTION_CHARACTERS.put(MINSTREL, "Puoi scambiare fra loro fino a 2 studenti presenti nella tua sala e nel tuo ingresso");
        DESCRIPTION_CHARACTERS.put(PRINCESS, "Prendi uno studente da questa carta e piazzalo nella tua sala. Poi pesca uno studente dal sacchetto e posizionalo su questa carta");
        DESCRIPTION_CHARACTERS.put(THIEF, "Scegli il colore di uno Studente; ogni giocatore (incluso te) deve rimettere nel sacchetto 3 Studenti di quel colore presenti nella tua sala. Chi avesse meno di 3 studenti di quel colore, rimetterà tutti quelli che ha");
    }

    public static String getDescriptionCharacter(CharacterID character){
        return DESCRIPTION_CHARACTERS.get(character);
    }

}

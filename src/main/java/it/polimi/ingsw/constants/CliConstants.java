package it.polimi.ingsw.constants;

import it.polimi.ingsw.model.Clan;

import java.util.EnumMap;
import java.util.Map;

public class CliConstants {

    public static final String JOINGAME_COMMAND = "joingame";
    public static final String RESTOREGAME_COMMAND = "restoregame";
    public static final String CREATENEWGAME_COMMAND = "createnewgame";
    public static final String CHAMBER_COMMAND = "chamber";
    public static final String ISLAND_COMMAND = "island";
    public static final String ENDTURN_COMMAND = "end turn";
    public static final String ACTIVATECHARACTER_COMMAND = "activatecharacter";

    public static final String ANSI_RED = "\033[31m";
    public static final String ANSI_GREEN = "\033[32m";
    public static final String ANSI_YELLOW = "\033[33m";
    public static final String ANSI_BLUE = "\033[34m";
    public static final String ANSI_PINK = "\033[95m";
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_PURPLE = "\033[35m";
    public static final String ANSI_GRAY = "\033[90m";
    public static final String ANSI_BLACK = "\033[30m";
    public static final String BACKGROUND_WHITE = "\033[47m";
    public static final String ANSI_CYAN = "\033[36m";
    public static final char TOWER_SYMBOL = '■' ;
    public static final char PROFESSOR_SYMBOL = '■';
    public static final char NO_PROFESSOR_SYMBOL = 'O';
    public static final char STUDENTS_PRESENT = '▓';
    public static final char STUDENTS_NOT_PRESENT = '░';
//"◙"
//"○"
// ○


    public static final int MAX_NAME_CARD_LENGTH = 8;

    private static final Map<Clan, String> clanMapColor;
    static {
        clanMapColor = new EnumMap<>(Clan.class);
        clanMapColor.put(Clan.PIXIES, CliConstants.ANSI_YELLOW);
        clanMapColor.put(Clan.UNICORNS, CliConstants.ANSI_BLUE);
        clanMapColor.put(Clan.TOADS, CliConstants.ANSI_GREEN);
        clanMapColor.put(Clan.DRAGONS, CliConstants.ANSI_RED);
        clanMapColor.put(Clan.FAIRIES, CliConstants.ANSI_PINK);
    }

    public static final int MAX_LENGHT_STUDENTS = 8;

    public static final int MAX_VISUAL = 6;

    public static final int MAX_VISUAL_DECK = 5;

    public static final int DOUBLE_DIGITS = 10;

    public static String getColorStudent(Clan clan){
        return clanMapColor.get(clan);
    }

}

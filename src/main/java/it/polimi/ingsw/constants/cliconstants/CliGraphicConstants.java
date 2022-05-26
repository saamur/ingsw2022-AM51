package it.polimi.ingsw.constants.cliconstants;

import it.polimi.ingsw.model.Clan;

import java.util.EnumMap;
import java.util.Map;

public class CliGraphicConstants {

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
    public static final char STUDENT_SYMBOL ='■';
    public static final char TOWER_SYMBOL = '■' ;
    public static final char PROFESSOR_SYMBOL = '■';
    public static final char NO_PROFESSOR_SYMBOL = 'O';
    public static final char STUDENTS_PRESENT = '▓';
    public static final char STUDENTS_NOT_PRESENT = '░';
//"◙"
//"○"
// ○

    public static final int MAX_NAME_CARD_LENGTH = 8;

    private static final Map<Clan, String> CLAN_MAP_COLOR;
    static {
        CLAN_MAP_COLOR = new EnumMap<>(Clan.class);
        CLAN_MAP_COLOR.put(Clan.PIXIES, CliGraphicConstants.ANSI_YELLOW);
        CLAN_MAP_COLOR.put(Clan.UNICORNS, CliGraphicConstants.ANSI_BLUE);
        CLAN_MAP_COLOR.put(Clan.TOADS, CliGraphicConstants.ANSI_GREEN);
        CLAN_MAP_COLOR.put(Clan.DRAGONS, CliGraphicConstants.ANSI_RED);
        CLAN_MAP_COLOR.put(Clan.FAIRIES, CliGraphicConstants.ANSI_PINK);
    }

    public static final int MAX_LENGHT_STUDENTS = 8;

    public static final int MAX_VISUAL = 6;

    public static final int MAX_VISUAL_DECK = 5;

    public static final int DOUBLE_DIGITS = 10;

    public static final String LOSER = """
            ▓▓  ▓▓  ▓▓▓▓▓▓  ▓▓  ▓▓    ▓▓      ▓▓▓▓▓▓  ▓▓▓▓▓▓  ▓▓▓▓▓▓
             ▓  ▓   ▓▓  ▓▓  ▓▓  ▓▓    ▓▓      ▓▓  ▓▓  ▓▓        ▓▓ \s
              ▓▓    ▓▓  ▓▓  ▓▓  ▓▓    ▓▓      ▓▓  ▓▓  ▓▓▓▓▓▓    ▓▓ \s
              ▓▓    ▓▓  ▓▓  ▓▓  ▓▓    ▓▓      ▓▓  ▓▓      ▓▓    ▓▓ \s
              ▓▓    ▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓▓▓▓▓  ▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓ \s
            """;

    public static final String WINNER = """
            ▓▓  ▓▓  ▓▓▓▓▓▓  ▓▓  ▓▓   ▓▓                 ▓▓  ▓▓  ▓▓▓     ▓▓
             ▓  ▓   ▓▓  ▓▓  ▓▓  ▓▓    ▓▓     ▓▓▓       ▓▓   ▓▓  ▓▓ ▓▓   ▓▓
              ▓▓    ▓▓  ▓▓  ▓▓  ▓▓     ▓▓   ▓▓  ▓▓   ▓▓     ▓▓  ▓▓  ▓▓  ▓▓
              ▓▓    ▓▓  ▓▓  ▓▓  ▓▓      ▓▓ ▓▓    ▓▓ ▓▓      ▓▓  ▓▓   ▓▓ ▓▓
              ▓▓    ▓▓▓▓▓▓  ▓▓▓▓▓▓       ▓▓▓      ▓▓▓       ▓▓  ▓▓     ▓▓▓
            """;

    public static final String ERIANTYS = """
            ▓▓▓▓▓▓  ▓▓▓▓▓▓  ▓▓  ▓▓▓▓▓▓  ▓▓▓    ▓▓  ▓▓▓▓▓▓  ▓▓  ▓▓  ▓▓▓▓▓▓
            ▓▓      ▓▓  ▓▓  ▓▓  ▓▓  ▓▓  ▓▓ ▓   ▓▓    ▓▓     ▓  ▓   ▓▓   \s
            ▓▓▓▓▓▓  ▓▓▓▓▓▓  ▓▓  ▓▓▓▓▓▓  ▓▓  ▓  ▓▓    ▓▓      ▓▓    ▓▓▓▓▓▓
            ▓▓      ▓▓ ▓▓   ▓▓  ▓▓  ▓▓  ▓▓   ▓ ▓▓    ▓▓      ▓▓        ▓▓
            ▓▓▓▓▓▓  ▓▓  ▓▓  ▓▓  ▓▓  ▓▓  ▓▓    ▓▓▓    ▓▓      ▓▓    ▓▓▓▓▓▓
            """;

    public static String getColorStudent(Clan clan){
        return CLAN_MAP_COLOR.get(clan);
    }

}

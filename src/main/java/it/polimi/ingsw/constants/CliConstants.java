package it.polimi.ingsw.constants;

import it.polimi.ingsw.model.Clan;

import java.util.HashMap;
import java.util.Map;

import org.fusesource.jansi.AnsiConsole;

public class CliConstants {

    public static final String ANSI_RED = "\033[31m";
    public static final String ANSI_GREEN = "\033[32m";
    public static final String ANSI_YELLOW = "\033[33m";
    public static final String ANSI_BLUE = "\033[34m";
    public static final String ANSI_PINK = "\033[95m";
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_PURPLE = "\033[35m";
    public static final String ANSI_GRAY = "\033[90m";
    public static final String BACKGROUND_WHITE = "\033[47m";
    public static final String ANSI_CYAN = "\033[36m";

    public static final int NUM_ACTIVE_CHARACTER_CARDS = 3;

    private static final Map<Clan, String> clanMapColor;
    static {
        clanMapColor = new HashMap<>();
        clanMapColor.put(Clan.PIXIES, CliConstants.ANSI_YELLOW);
        clanMapColor.put(Clan.UNICORNS, CliConstants.ANSI_BLUE);
        clanMapColor.put(Clan.TOADS, CliConstants.ANSI_GREEN);
        clanMapColor.put(Clan.DRAGONS, CliConstants.ANSI_RED);
        clanMapColor.put(Clan.FAIRIES, CliConstants.ANSI_PINK);
    }

    public static final int MAX_LENGHT_STUDENTS = 8;

    public static final int MAX_VISUAL = 6;

    public static String getColorStudent(Clan clan){
        return clanMapColor.get(clan);
    }



}

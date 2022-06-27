package it.polimi.ingsw.constants.cliconstants;

import it.polimi.ingsw.model.charactercards.CharacterID;

import java.util.EnumMap;
import java.util.Map;

/**
 * The CliCommandConstants contains the textual commands needed to play to Eriantys with the CLI
 *
 */
public class CliCommandConstants {

    public static final String JOINGAME_COMMAND = "joingame";
    public static final String RESTOREGAME_COMMAND = "restoregame";
    public static final String CREATENEWGAME_COMMAND = "createnewgame";
    public static final String CHAMBER_COMMAND = "chamber";
    public static final String ISLAND_COMMAND = "island";
    public static final String ENDTURN_COMMAND = "endturn";
    public static final String ACTIVATECHARACTER_COMMAND = "activatecharacter";
    private static final Map<CharacterID, String> CHARACTERS_EFFECTS_COMMANDS;

    static {
        CHARACTERS_EFFECTS_COMMANDS = new EnumMap<>(CharacterID.class);
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.MONK, "<clan to move to an island> <island index>");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.FARMER, "no commands");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.HERALD, "<island index>");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.POSTMAN, "no commands");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.GRANDMA, "<island index>");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.CENTAUR, "no commands");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.JESTER, "<list of clans on the card> # <list of clans in your hall>");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.KNIGHT, "no commands");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.MUSHROOMPICKER, "<clan>");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.MINSTREL, "<list of clans in your chamber> # <list of clans in your hall>");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.PRINCESS, "<clan on the card>");
        CHARACTERS_EFFECTS_COMMANDS.put(CharacterID.THIEF, "<clan of the students to remove>");
    }

    public static String getCharacterEffectCommand(CharacterID characterID) {
        return CHARACTERS_EFFECTS_COMMANDS.get(characterID);
    }

}
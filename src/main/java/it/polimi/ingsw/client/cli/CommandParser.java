package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.constants.cliconstants.CliCommandConstants;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.gamemessages.*;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.Card;

import java.util.EnumMap;
import java.util.Map;

/**
 * The CommandParser class contains the necessary methods to parse the commands inserted by a user playing with the cli
 * and convert them in the corresponding messages
 *
 */
public class CommandParser {

    /**
     * The parseCommand methods parses the given line and returns the corresponding Message
     * @param line                  the command inserted by the player
     * @param gameData              the data of the game to which the player belongs to, null if not available yet
     * @param nickname              the nickname of the player, null if not available yet
     * @param gameChosen            whether the player has chosen a game or not
     * @param availableGamesMessage the message containing the available games for this player, null if not available yet
     * @return                      the Message that correspond to the inserted command, null if it is not valid
     */
    public static Message parseCommand (String line, GameData gameData, String nickname, boolean gameChosen, AvailableGamesMessage availableGamesMessage) {

        Message message = null;

        if (gameData == null) {
            if (nickname == null) {
                if ("".equals(line))
                    System.out.println("I don't really know how to pronunce it. Try with another one: ");
                else if (line.contains(" "))
                    System.out.println("This nickname is not valid, you can't use spaces. Try with another one: ");
                else
                    message = new NicknameMessage(line);
            }
            else if (!gameChosen && availableGamesMessage != null) {
                String[] words = line.split(" ");
                if (words.length > 0) {
                    if (CliCommandConstants.JOINGAME_COMMAND.equalsIgnoreCase(words[0]) && words.length == 2) {
                        try {
                            message = new AddPlayerMessage(Integer.parseInt(words[1]));
                        } catch (IllegalArgumentException e) {}
                    }
                    else if (CliCommandConstants.RESTOREGAME_COMMAND.equalsIgnoreCase(words[0]) && words.length == 2) {
                        int index;
                        try {
                            index = Integer.parseInt(words[1]);
                            if (index < availableGamesMessage.savedGameData().size())
                                message = new RestoreGameMessage(availableGamesMessage.savedGameData().get(index));
                        } catch (NumberFormatException e) {}
                    }
                    else if (CliCommandConstants.CREATENEWGAME_COMMAND.equalsIgnoreCase(words[0]) && words.length == 3) {
                        try {
                            message = new NewGameMessage(Integer.parseInt(words[1]), Boolean.parseBoolean(words[2]));
                        } catch (IllegalArgumentException e) {}
                    }
                }

                if (message == null)
                    System.out.println("This command is not correct");

            }
            else {
                System.out.println("The game is not started yet");
            }
        }
        else if (nickname.equals(gameData.getCurrPlayer())) {
            if (gameData.getGameState() == GameState.PLANNING) {
                try {
                    message = new ChosenCardMessage(Card.valueOf(line.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("This is not a valid card. Retry: ");
                }
            }
            else if (gameData.getGameState() == GameState.ACTION) {
                String[] words = line.split(" ");
                if (gameData.getActiveCharacterCard() != null && !gameData.isActiveCharacterPunctualEffectApplied()) {
                    message = parseCharacterCommand(line, gameData.getActiveCharacterCard());
                    if (message == null)
                        System.out.println("This command is not correct");
                }
                else if (gameData.getActiveCharacterCard() == null && words.length == 2 && CliCommandConstants.ACTIVATECHARACTER_COMMAND.equalsIgnoreCase(words[0])) {
                    try {
                        message = new ActivateCharacterCardMessage(CharacterID.valueOf(words[1].toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        System.out.println("This character does not exist");
                    }
                }
                else {
                    switch (gameData.getTurnState()) {
                        case STUDENT_MOVING -> {
                            if (words.length == 2 && CliCommandConstants.CHAMBER_COMMAND.equalsIgnoreCase(words[0])) {
                                try {
                                    message = new MoveStudentToChamberMessage(Clan.valueOf(words[1].toUpperCase()));
                                } catch (IllegalArgumentException e) {
                                    System.out.println("This is not a valid clan");
                                }
                            } else if (words.length == 3 && CliCommandConstants.ISLAND_COMMAND.equalsIgnoreCase(words[0])) {
                                try {
                                    message = new MoveStudentToIslandMessage(Clan.valueOf(words[2].toUpperCase()), Integer.parseInt(words[1]));
                                } catch (Exception e) {
                                    System.out.println("The parameters are not correct");
                                }
                            } else {
                                System.out.println("This command is not correct");
                            }
                        }
                        case MOTHER_MOVING -> {
                            try {
                                message = new MoveMotherNatureMessage(Integer.parseInt(line));
                            } catch (NumberFormatException e) {
                                System.out.println("This is not a valid number");
                            }
                        }
                        case CLOUD_CHOOSING -> {
                            try {
                                message = new ChosenCloudMessage(Integer.parseInt(line));
                            } catch (NumberFormatException e) {
                                System.out.println("This is not a valid number");
                            }
                        }
                        case END_TURN -> {
                            if (CliCommandConstants.ENDTURN_COMMAND.equalsIgnoreCase(line))
                                message = new EndTurnMessage();
                            else
                                System.out.println("This command is not correct");
                        }
                    }
                }
            }
        }
        else if (gameData.getGameState() == GameState.GAME_OVER) {
            System.out.println("The game is over");
        }
        else {
            System.out.println("It is not your turn");
        }

        return message;

    }

    /**
     * The parseCharacterCommand parses the given line according to the given characterID and creates
     * the corresponding message
     * @param line          the command inserted by the player
     * @param characterID   the CharacterID of the active character
     * @return              the Message that correspond to the inserted command, null if it is not valid
     */
    private static Message parseCharacterCommand (String line, CharacterID characterID) {

        Message message = null;

        switch (characterID) {
            case MONK -> {
                String[] words = line.split(" ");
                if (words.length == 2) {
                    try {
                        message = new ApplyCharacterCardEffectMessage2(Integer.parseInt(words[1]), mapFromStringOfClans(words[0]), null);
                    } catch (IllegalArgumentException e) {}
                }
            }
            case HERALD, GRANDMA -> {
                try {
                    message = new ApplyCharacterCardEffectMessage1(Integer.parseInt(line));
                } catch (NumberFormatException e) {}
            }
            case JESTER, MINSTREL -> {
                String[] words = line.split("#");
                if (words.length == 2) {
                    try {
                        message = new ApplyCharacterCardEffectMessage2(-1, mapFromStringOfClans(words[0]), mapFromStringOfClans(words[1]));
                    } catch (IllegalArgumentException e) {}
                }
            }
            case MUSHROOMPICKER, THIEF -> {
                try {
                    message = new SetClanCharacterMessage(Clan.valueOf(line.toUpperCase()));
                } catch (IllegalArgumentException e) {}
            }
            case PRINCESS -> {
                try {
                    message = new ApplyCharacterCardEffectMessage2(-1, mapFromStringOfClans(line), null);
                } catch (IllegalArgumentException e) {}
            }
        }

        return message;

    }

    /**
     * The method mapFromStringOfClans creates, given a string of clans separated by a space, a map that for each clan
     * contains the number of times the clan was present in the string
     * @param line  the stringe to parse
     * @return      a map that for each clan contains the number of times the clan was present in the string
     * @throws IllegalArgumentException when the string contains no words, or at least one word does not correspond to any clan
     */
    private static Map<Clan, Integer> mapFromStringOfClans (String line) throws IllegalArgumentException {

        Map<Clan, Integer> map = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            map.put(c, 0);

        String[] words = line.split(" ");
        if (words.length == 0)
            throw new IllegalArgumentException();

        for (String w : words) {
            if (!"".equals(w)) {
                Clan c = Clan.valueOf(w.toUpperCase());
                map.put(c, map.get(c) + 1);
            }
        }

        return map;

    }

}

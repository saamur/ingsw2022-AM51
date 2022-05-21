package it.polimi.ingsw.client.CLI;

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

public class CommandParser {

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
        else if (gameData.getCurrPlayer().equals(nickname)) {
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
        else {
            System.out.println("It is not your turn");
        }

        return message;

    }

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
                    message = new SetClanCharacterMessage(Clan.valueOf(line));
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

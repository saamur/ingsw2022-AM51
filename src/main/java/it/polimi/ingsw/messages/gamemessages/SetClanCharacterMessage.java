package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameInterface;

/**
 * SetClanCharacterMessage receives a message containing the choice of a student's clan to use on a character card
 * @param clan clan of the student to be used
 */

public record SetClanCharacterMessage(Clan clan) implements GameMessage {

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) {

        Message answer = null;

        try {
            game.setClanCharacter(nickname, clan);
            answer = new GenericMessage("the clan has been set");
        } catch (WrongGamePhaseException | ExpertModeNotEnabledException | WrongPlayerException | NotValidMoveException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        return answer;

    }

    @Override
    public String toString() {
        return "SetClanCharacterMessage{" +
                "clan=" + clan +
                '}';
    }

}

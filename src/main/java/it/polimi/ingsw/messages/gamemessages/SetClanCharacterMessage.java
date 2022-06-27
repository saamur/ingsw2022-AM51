package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameInterface;

/**
 * The SetClanCharacterMessage record models a message containing the choice of a student's clan to use on a character card
 * @param clan clan of the student to be used
 */

public record SetClanCharacterMessage(Clan clan) implements GameMessage {

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) throws WrongGamePhaseException, ExpertModeNotEnabledException, WrongPlayerException, NonExistingPlayerException, NotValidMoveException {

        game.setClanCharacter(nickname, clan);
        return new GenericMessage("the clan has been set");

    }

    @Override
    public String toString() {
        return "SetClanCharacterMessage{" +
                "clan=" + clan +
                '}';
    }

}

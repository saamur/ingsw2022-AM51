package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Clan;

/**
 * SetClanCharacterMessage receives a message containing the choice of a student's clan to use on a character card
 * @param clan clan of the student to be used
 */

public record SetClanCharacterMessage(Clan clan) implements Message {


    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "SetClanCharacterMessage{" +
                "clan=" + clan +
                '}';
    }
}

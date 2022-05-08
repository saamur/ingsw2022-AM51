package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameInterface;

import java.util.Map;



/**
 * ApplyCharacterCardEffectMessage2 gets the message to activate the effect of an active card on a chosen island
 * @param islandIndex index of the island where to activate the effect
 * @param students1 the students to move to the destination (if necessary)
 * @param students2 the students to move from the destination, in case of an exchange of students (if necessary)
 */

public record ApplyCharacterCardEffectMessage2(int islandIndex, Map<Clan, Integer> students1, Map<Clan, Integer> students2) implements GameMessage {

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) {

        Message answer = null;

        try {
            game.applyCharacterCardEffect(nickname, islandIndex, students1, students2);
            answer = new GenericMessage("the effect has been applied");
        } catch (WrongGamePhaseException | ExpertModeNotEnabledException | WrongPlayerException | NotValidMoveException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        return answer;

    }

    @Override
    public String toString() {
        return "ApplyCharacterCardEffectMessage2{" +
                "islandIndex=" + islandIndex +
                ", students1=" + students1 +
                ", students2=" + students2 +
                '}';
    }

}

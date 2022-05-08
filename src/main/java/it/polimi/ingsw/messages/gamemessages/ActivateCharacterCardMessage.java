package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.charactercards.CharacterID;

/**
 * ActivateCharacterCardMessage gets a message to activate a character card
 * @param characterCardID ID of the card to activate
 */
public record ActivateCharacterCardMessage(CharacterID characterCardID) implements GameMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) {

        Message answer = null;

        try {
            game.activateCharacterCard(nickname, characterCardID);
            answer = new GenericMessage("You have activated " + characterCardID.name().toLowerCase());
        } catch (WrongGamePhaseException | ExpertModeNotEnabledException | WrongPlayerException | NotValidMoveException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        return answer;

    }

    @Override
    public String toString() {
        return "ActivateCharacterCardMessage{" +
                "characterCardID=" + characterCardID +
                '}';
    }

}

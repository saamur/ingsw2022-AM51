package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;

public record UpdateGamePhase(GameState gameState,
                              TurnState turnState,
                              String currPlayerNickname,
                              boolean lastRound,
                              CharacterID activatedCharacter) implements UpdateMessage {
    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "UpdateGamePhase{" +
                "gameState=" + gameState +
                ", turnState=" + turnState +
                ", currPlayerNickname='" + currPlayerNickname + '\'' +
                ", lastRound=" + lastRound +
                ", activatedCharacter=" + activatedCharacter +
                '}';
    }

}

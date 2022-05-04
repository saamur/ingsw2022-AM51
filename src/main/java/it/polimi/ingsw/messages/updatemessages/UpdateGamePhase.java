package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GamePhaseData;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;

public record UpdateGamePhase(GamePhaseData gamePhaseData) implements UpdateMessage {
    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "UpdateGamePhase{" +
                "gameState=" + gamePhaseData.gameState() +
                ", turnState=" + gamePhaseData.turnState() +
                ", currPlayerNickname='" + gamePhaseData.currPlayerNickname() + '\'' +
                ", lastRound=" + gamePhaseData.lastRound() +
                ", activatedCharacter=" + gamePhaseData.activatedCharacter() +
                '}';
    }

}

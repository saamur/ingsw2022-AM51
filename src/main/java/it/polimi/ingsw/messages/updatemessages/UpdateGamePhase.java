package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GamePhaseData;

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

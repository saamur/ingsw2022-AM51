package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.modeldata.GamePhaseData;

public record UpdateGamePhase(GamePhaseData gamePhaseData) implements UpdateMessage {

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public void updateGameData(GameData gameData) {
        gameData.setGameState(gamePhaseData.gameState());
        gameData.setTurnState(gamePhaseData.turnState());
        gameData.setCurrPlayer(gamePhaseData.currPlayerNickname());
        gameData.setLastRound(gamePhaseData.lastRound());
        gameData.setActiveCharacterCard(gamePhaseData.activatedCharacter());
        gameData.setActiveCharacterPunctualEffectApplied(gamePhaseData.activatedCharacterPunctualEffectApplied());
    }

    @Override
    public String toString() {
        return "UpdateGamePhase{" +
                "gamePhaseData=" + gamePhaseData +
                '}';
    }
}

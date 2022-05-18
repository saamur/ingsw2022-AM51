package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;

import java.io.Serializable;

public record GamePhaseData(GameState gameState,
                            TurnState turnState,
                            String currPlayerNickname,
                            boolean lastRound,
                            CharacterID activatedCharacter,
                            boolean activatedCharacterEffectApplied) implements Serializable {

    public static GamePhaseData createGamePhaseData(GameInterface game){
        return new GamePhaseData(game.getGameState(), game.getTurnState(), game.getNicknameCurrPlayer(), game.isLastRound(), game.getActivatedCharacterCard(), game.isActivatedCharacterCardEffectApplied());
    }

    @Override
    public String toString() {
        return "GamePhaseData{" +
                "gameState=" + gameState +
                ", turnState=" + turnState +
                ", currPlayerNickname='" + currPlayerNickname + '\'' +
                ", lastRound=" + lastRound +
                ", activatedCharacter=" + activatedCharacter +
                ", activatedCharacterEffectApplied=" + activatedCharacterEffectApplied +
                '}';
    }

}

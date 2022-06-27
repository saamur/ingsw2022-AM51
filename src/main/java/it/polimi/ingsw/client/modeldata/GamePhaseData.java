package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;

import java.io.Serializable;

/**
 * contains the information about the phase of the game
 * @param gameState state of the game
 * @param turnState state of the turn
 * @param currPlayerNickname nickname of the current player
 * @param lastRound flag that says if it is the last round or not
 * @param activatedCharacter active character card
 * @param activatedCharacterPunctualEffectApplied flag that says if the effect of the active character card has been applied or not
 */

public record GamePhaseData(GameState gameState,
                            TurnState turnState,
                            String currPlayerNickname,
                            boolean lastRound,
                            CharacterID activatedCharacter,
                            boolean activatedCharacterPunctualEffectApplied) implements Serializable {

    public static GamePhaseData createGamePhaseData(GameInterface game){
        return new GamePhaseData(game.getGameState(), game.getTurnState(), game.getNicknameCurrPlayer(), game.isLastRound(), game.getActivatedCharacterCard(), game.isActivatedCharacterCardPunctualEffectApplied());
    }

    @Override
    public String toString() {
        return "GamePhaseData{" +
                "gameState=" + gameState +
                ", turnState=" + turnState +
                ", currPlayerNickname='" + currPlayerNickname + '\'' +
                ", lastRound=" + lastRound +
                ", activatedCharacter=" + activatedCharacter +
                ", activatedCharacterPunctualEffectApplied=" + activatedCharacterPunctualEffectApplied +
                '}';
    }

}

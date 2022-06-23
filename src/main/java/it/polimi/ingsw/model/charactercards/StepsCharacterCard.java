package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.player.Player;

/**
 * The StepsCharacterCard class models the character cards
 * that have an impact on the calculus of the maximum number of steps that Mother Nature can take
 *
 */
public class StepsCharacterCard extends CharacterCard {

    /**
     * Constructs a CharacterCard with the given CharacterID
     * @param characterID   the CharacterID of the character to construct
     */
    public StepsCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public boolean applyInitialEffect (Turn turn, Player[] players) {
        turn.characterPunctualEffectApplied();
        return true;
    }

    @Override
    public int effectStepsMotherNature() {
        return GameConstants.getNumAdditionalStepsCharacterCard(getCharacterID());
    }

}

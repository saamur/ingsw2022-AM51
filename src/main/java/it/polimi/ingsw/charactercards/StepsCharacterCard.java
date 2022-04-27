package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.GameConstants;

/**
 * StepsCharacterCard class models the character cards
 * that have an impact on the calculus of the maximum number of steps that Mother Nature can take
 *
 */
public class StepsCharacterCard extends CharacterCard {

    public StepsCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public int effectStepsMotherNature() {
        return GameConstants.getNumAdditionalStepsCharacterCard(getCharacterID());
    }

}

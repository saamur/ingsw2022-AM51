package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.islands.Island;

/**
 * The ProhibitionCharacterCard class models the character cards
 * whose effect is to add prohibition cards on islands
 *
 */
public class ProhibitionCharacterCard extends CharacterCard {

    private int numProhibitionCards;

    /**
     * Constructs a CharacterCard with the given CharacterID and with the initial number of prohibition cards
     * @param characterID   the CharacterID of the character to construct
     */
    public ProhibitionCharacterCard(CharacterID characterID) {
        super(characterID);
        numProhibitionCards = GameConstants.getNumInitialProhibitionCardsCharacterCard(characterID);
    }

    @Override
    public boolean isAvailable() {
        return numProhibitionCards > 0;
    }

    @Override
    public boolean applyEffect(Game game, Island isl) {

        if (numProhibitionCards < 1)
            return false;

        numProhibitionCards--;
        isl.addProhibitionCard();

        return true;

    }

    /**
     * method addProhibitionCard increases the variable numProhibitionCards by 1
     */
    public void addProhibitionCard() {
        numProhibitionCards++;
    }

    public int getNumProhibitionCards() {
        return numProhibitionCards;
    }

}

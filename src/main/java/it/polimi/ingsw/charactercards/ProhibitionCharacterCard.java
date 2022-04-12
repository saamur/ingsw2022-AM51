package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Constants;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.islands.Island;

/**
 * ProhibitionCharacterCard class models the character cards
 * whose effect is to add prohibition cards on islands
 *
 */
public class ProhibitionCharacterCard extends CharacterCard {

    private int numProhibitionCards;

    public ProhibitionCharacterCard(CharacterID characterID) {
        super(characterID);
        numProhibitionCards = Constants.getNumInitialProhibitionCardsCharacterCard(characterID);
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

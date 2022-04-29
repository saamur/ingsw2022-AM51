package it.polimi.ingsw.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Deck class models the deck of the assistant cards of the game
 *
 */
public class Deck implements Serializable {

    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>(Arrays.asList(Card.values()));
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * method removeCard removes the card given by parameter from the List cards
     * @param c the card to remove from the deck
     * @return  true if c was in the deck and was removed, false if the deck didn't contain c
     */
    public boolean removeCard(Card c) {
        return cards.remove(c);
    }

    /**
     * method isEmpty returns true if the Deck is empty
     * @return  whether the Deck is empty
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

}

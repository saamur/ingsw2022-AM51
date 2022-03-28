package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of tests to make sure the class Deck is working correctly
 */
public class DeckTest {
    /**
     * Testing if the method choose works as expected
     */
    @Test
    public void chooseCardTest(){
        Deck deck = new Deck();
        deck.choose(Card.CAT); //TODO random Card generator instead of CAT
        List<Card> cardsRemaining = deck.getCards();
        int size = cardsRemaining.size();
        assertFalse(cardsRemaining.contains(Card.CAT)); //TODO check that all other cards are still there
        assertEquals(9, size);
    }

    /**
     * Testing if choose method acts as expected when it is called twice on the same Card
     */
    @Test
    public void multipleChooseCard(){
        Deck deck = new Deck();
        deck.choose(Card.CAT); //TODO random cards
        boolean result = deck.choose(Card.CAT); //TODO random cards
        assertFalse(result);
    }

}

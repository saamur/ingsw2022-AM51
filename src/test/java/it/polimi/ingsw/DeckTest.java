package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random rand = new Random();
        Card card = Card.values()[rand.nextInt(Card.values().length)];
        deck.choose(card);
        List<Card> cardsRemaining = deck.getCards();
        int size = cardsRemaining.size();
        assertFalse(cardsRemaining.contains(card));
        assertEquals(9, size);
    }

    /**
     * Testing if choose method acts as expected when it is called twice on the same Card
     */
    @Test
    public void multipleChooseCard(){
        Deck deck = new Deck();
        Random random = new Random();
        Card card = Card.values()[random.nextInt(Card.values().length)];
        deck.choose(card);
        boolean result = deck.choose(card);
        assertFalse(result);
    }

}

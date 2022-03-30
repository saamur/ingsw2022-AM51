package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of tests to make sure the class Deck is working correctly
 */
public class DeckTest {
    Deck deck;
    Random rand;
    Card card;

    @BeforeEach
    public void initialization(){
        deck = new Deck();
        rand = new Random();
    }
    /**
     * Testing if the method choose works as expected
     */
    @Test
    public void chooseCardTest(){
        card = Card.values()[rand.nextInt(Card.values().length)];
        boolean result = deck.choose(card);
        List<Card> cardsRemaining = deck.getCards();
        int size = cardsRemaining.size();

        assertTrue(result);
        assertFalse(cardsRemaining.contains(card));
        assertEquals(9, size);
    }

    /**
     * Testing if choose method acts as expected when it is called twice on the same Card
     */
    @Test
    public void multipleChoiceCard(){
        card = Card.values()[rand.nextInt(Card.values().length)];
        deck.choose(card);
        boolean result = deck.choose(card);
        assertFalse(result);
    }

    /**
     * Testing if the isEmpty() method works as expected
     */
    @Test
    public void emptyTest(){
        for(int i=0; i<Card.values().length; i++){
            deck.choose(Card.values()[i]);
        }
        assertTrue(deck.isEmpty());
    }

}

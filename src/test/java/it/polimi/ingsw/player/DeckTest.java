package it.polimi.ingsw.player;

import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class DeckTest tests Deck.
 * @see Deck
 */
public class DeckTest {
    private Deck deck;
    private Card card;

    /**
     * Method initialization() initializes the variable deck.
     */
    @BeforeEach
    public void initialization(){
        deck = new Deck();
    }
    /**
     * Method chooseCardTest() tests method Desk.choose().
     * The value returned by the method is expected to be true.
     * The chosen card is expected to be removed from the deck.
     * The size of the deck after the declaration of the method is expected to be 9.
     * @param card is the card of the deck on which we are going to test the method
     */

    @ParameterizedTest
    @EnumSource(Card.class)
    public void chooseCardTest(Card card){

        boolean result = deck.removeCard(card);
        List<Card> cardsRemaining = deck.getCards();
        int size = cardsRemaining.size();

        assertTrue(result);
        assertFalse(cardsRemaining.contains(card));
        assertEquals(9, size);
    }

    /**
     * Method multipleChoiceCard() tests the method choose() when it is called twice on the same Card.
     * The value returned is expected to be false.
     * @param card is the card of the deck on which we are going to test the method
     */
    @ParameterizedTest
    @EnumSource(Card.class)
    public void multipleChoiceCard(Card card){

        deck.removeCard(card);
        boolean result = deck.removeCard(card);
        assertFalse(result);

    }

    /**
     * Method emptyTest() tests the Deck.isEmpty() method. The method is called before and after all of the cards are removed.
     * The first result to be expected is false and the following one true.
     */
    @Test
    public void emptyTest(){
        assertFalse(deck.isEmpty());
        for(int i=0; i<Card.values().length; i++){
            deck.removeCard(Card.values()[i]);
        }
        assertTrue(deck.isEmpty());
    }

}

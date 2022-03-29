package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Deck {

    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>(Arrays.asList(Card.values()));
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public boolean choose(Card c) {
        return cards.remove(c);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

}

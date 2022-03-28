package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Deck {

    private final List<Card> cards;

    public Deck() {
        cards = Arrays.asList(Card.values());
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public boolean choose(Card c) {

        if (!cards.contains(c))
            return false;

        cards.remove(c);
        return true;

    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

}

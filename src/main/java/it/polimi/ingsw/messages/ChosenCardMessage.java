package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.player.Card;

/**
 * ChosenCardMessage gets a message to choose a card
 * @param card the chosen card
 */
public record ChosenCardMessage(Card card) implements Message {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "ChosenCardMessage{" +
                "card=" + card +
                '}';
    }

}

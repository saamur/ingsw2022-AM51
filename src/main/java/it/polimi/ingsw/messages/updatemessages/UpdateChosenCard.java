package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.model.player.Card;

public record UpdateChosenCard(Card card, String playerNickname) implements UpdateMessage {

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

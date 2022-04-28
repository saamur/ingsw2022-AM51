package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.player.Card;

public record ChosenCardMessage(Card card) implements Message{

    @Override
    public String getMessage(){
        return this.toString();
    }
}

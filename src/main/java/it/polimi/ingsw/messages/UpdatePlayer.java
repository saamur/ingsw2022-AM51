package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.player.Player;

public record UpdatePlayer(Player modifiedPlayer) implements Message{
    @Override
    public String getMessage(){
        return this.toString();
    }
}

package it.polimi.ingsw.messages;

import it.polimi.ingsw.client.modeldata.PlayerData;

public record UpdatePlayer(PlayerData modifiedPlayer) implements Message{
    @Override
    public String getMessage(){
        return this.toString();
    }
}

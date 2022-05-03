package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

public record UpdatePlayer(PlayerData modifiedPlayer) implements UpdateMessage {
    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "UpdatePlayer{" +
                "modifiedPlayer=" + modifiedPlayer +
                '}';
    }
}

package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.IslandData;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

public record UpdateIsland(IslandData islandData) implements UpdateMessage {
    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "UpdateIsland{" +
                "islandData=" + islandData +
                '}';
    }
}

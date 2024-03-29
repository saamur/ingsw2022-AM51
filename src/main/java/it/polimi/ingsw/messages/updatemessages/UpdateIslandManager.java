package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.modeldata.IslandManagerData;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

public record UpdateIslandManager(IslandManagerData islandManagerData) implements UpdateMessage {

    @Override
    public String getMessage(){
        return toString();
    }

    @Override
    public void updateGameData(GameData gameData) {
        gameData.setIslandManager(islandManagerData);
    }

    @Override
    public String toString() {
        return "UpdateIslandManager{" +
                "islandManagerData=" + islandManagerData +
                '}';
    }

}

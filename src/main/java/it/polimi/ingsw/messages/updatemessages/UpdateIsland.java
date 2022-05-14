package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.modeldata.IslandData;

public record UpdateIsland(IslandData islandData) implements UpdateMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public void updateGameData(GameData gameData) {
        gameData.getIslandManager().getIslands().set(islandData.islandIndex(), islandData);
    }

    @Override
    public String toString() {
        return "UpdateIsland{" +
                "islandData=" + islandData +
                '}';
    }

}

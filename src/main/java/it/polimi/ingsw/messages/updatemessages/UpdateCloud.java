package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.CloudData;
import it.polimi.ingsw.client.modeldata.GameData;

public record UpdateCloud(CloudData cloudData) implements UpdateMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public void updateGameData(GameData gameData) {
        gameData.getCloudManager().clouds()[cloudData.cloudIndex()] = cloudData;
    }

    @Override
    public String toString() {
        return "UpdateCloud{" +
                "cloudData=" + cloudData +
                '}';
    }

}

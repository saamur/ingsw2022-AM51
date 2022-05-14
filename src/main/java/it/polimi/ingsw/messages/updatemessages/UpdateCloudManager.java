package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.CloudManagerData;
import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

public record UpdateCloudManager(CloudManagerData cloudManagerData) implements UpdateMessage {

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public void updateGameData(GameData gameData) {
        gameData.setCloudManager(cloudManagerData);
    }

    @Override
    public String toString() {
        return "UpdateCloudManager{" +
                "cloudManagerData=" + cloudManagerData +
                '}';
    }

}

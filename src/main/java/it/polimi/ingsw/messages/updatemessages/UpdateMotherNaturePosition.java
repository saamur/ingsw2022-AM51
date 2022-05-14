package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GameData;

public record UpdateMotherNaturePosition(int islandIndex) implements UpdateMessage {

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public void updateGameData(GameData gameData) {
        gameData.getIslandManager().setMotherNaturePosition(islandIndex);
    }

    @Override
    public String toString() {
        return "UpdateMotherNaturePosition{" +
                "islandIndex=" + islandIndex +
                '}';
    }

}

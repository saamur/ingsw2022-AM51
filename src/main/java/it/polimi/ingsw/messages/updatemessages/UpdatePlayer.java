package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

public record UpdatePlayer(PlayerData modifiedPlayer) implements UpdateMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public void updateGameData(GameData gameData) {
        for (int i = 0; i < gameData.getPlayerData().length; i++)
            if (gameData.getPlayerData()[i].getNickname().equals(modifiedPlayer.getNickname()))
                gameData.getPlayerData()[i] = modifiedPlayer;
    }

    @Override
    public String toString() {
        return "UpdatePlayer{" +
                "modifiedPlayer=" + modifiedPlayer +
                '}';
    }

}

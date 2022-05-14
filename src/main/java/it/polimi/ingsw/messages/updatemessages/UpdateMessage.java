package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.messages.Message;

public interface UpdateMessage extends Message {

    void updateGameData(GameData gameData);

}

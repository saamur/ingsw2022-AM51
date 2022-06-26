package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.messages.Message;

/**
 * UpdateMessage is an interface the comprehends messages sent to the client following an update in the model
 */
public interface UpdateMessage extends Message {

    void updateGameData(GameData gameData);

}

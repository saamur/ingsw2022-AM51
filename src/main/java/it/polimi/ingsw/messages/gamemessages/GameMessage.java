package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;

public interface GameMessage extends Message {

    Message performMove (GameInterface game);

}

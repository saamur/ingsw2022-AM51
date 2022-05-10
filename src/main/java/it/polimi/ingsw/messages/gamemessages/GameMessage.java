package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;

public interface GameMessage extends Message {

    /**
     * The method performMove, calls the method that corresponds to the message
     * @param nickname of the player who performs the move
     * @param game
     * @return a Message which is an answer to be sent to the client
     */
    Message performMove (String nickname, GameInterface game);

}

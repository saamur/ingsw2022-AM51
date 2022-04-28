package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PlayerDisconnectedMessage;
import it.polimi.ingsw.model.GameInterface;

public class Controller {

    private GameInterface game;
    private boolean closing;

    public synchronized void clientDisconnected (String nickname) {

        if (!closing) {

            closing = true;
            Message message = new PlayerDisconnectedMessage(nickname);

            //todo send broadcast

        }

    }

}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.PlayerDisconnectedMessage;
import it.polimi.ingsw.model.GameInterface;

public abstract class Controller {

    private GameInterface game;
    private boolean closing;

    public Controller(GameInterface game) {
        this.game = game;
        closing = false;
    }

    public abstract void addPlayer (String nickname);

    public Message messageOnGame (Message message) {

        return null;

    }

    public synchronized void clientDisconnected (String nickname) {

        if (!closing) {

            closing = true;
            Message message = new PlayerDisconnectedMessage(nickname);

            //todo send broadcast

        }

    }

}

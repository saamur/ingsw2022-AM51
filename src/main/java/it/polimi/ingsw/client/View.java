package it.polimi.ingsw.client;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class View implements Runnable {

    protected String nickname;
    protected AvailableGamesMessage availableGamesMessage;

    protected GameData gameData;

    protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage) {
        this.availableGamesMessage = availableGamesMessage;
    }

    public void setGameData (GameData gameData) {
        this.gameData = gameData;
    }

    public void updateGameData (UpdateMessage updateMessage) {
        if (gameData != null)
            updateMessage.updateGameData(gameData);
    }

    public void addPropertyChangeListener (PropertyChangeListener propertyChangeListener) {
        pcs.addPropertyChangeListener(propertyChangeListener);
    }

}

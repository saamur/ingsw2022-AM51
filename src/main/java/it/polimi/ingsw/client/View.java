package it.polimi.ingsw.client;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface View {

    void setNickname(String nickname);

    void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage);

    void playerAddedToGame(String message);

    void setGameData (GameData gameData) ;

    void updateGameData (UpdateMessage updateMessage);

    void addPropertyChangeListener (PropertyChangeListener propertyChangeListener);

    void handleGenericMessage(String message);
    void handleErrorMessage(String message);

    void handleGameOver(List<String> winnersNickname);
    void handlePlayerDisconnected(String playerDisconnectedNickname);

}

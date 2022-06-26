package it.polimi.ingsw.client;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface View {

    /**
     * the method setNickname is used to set a player's nickname, if the chosen nickname hasn't already been used
     * @param nickname nickname chosen by the player
     */

    void setNickname(String nickname);

    /**
     * the method setAvailableGamesMessage shows the available games
     * @param availableGamesMessage contains the available games
     */

    void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage);

    /**
     * the method shows that the player has been added to the chosen game
     * @param message notifies that the player has been entered into the game
     */

    void playerAddedToGame(String message);

    /**
     * the method setGameData create a new gameData concerning the newly created match
     * @param gameData match data
     */

    void setGameData (GameData gameData) ;

    /**
     * the method updateGameData allows the update of the game status after receiving commands given by the player
     * @param updateMessage message entered by the player
     */

    void updateGameData (UpdateMessage updateMessage);

    void addPropertyChangeListener (PropertyChangeListener propertyChangeListener);

    /**
     * the method handles a generic message
     * @param message   the message to be handled
     */

    void handleGenericMessage(String message);

    /**
     * the method handles an error message
     * @param message   the error to be handled
     */

    void handleErrorMessage(String message);

    /**
     * the method handleGameOver set the winners in the game data
     * @param winnersNickname nickname of the winners
     */

    void handleGameOver(List<String> winnersNickname);

    /**
     * the method handlePlayerDisconnected notifies that a player has disconnected from the game
     * @param playerDisconnectedNickname nickname of the disconnected player
     */

    void handlePlayerDisconnected(String playerDisconnectedNickname);

    /**
     * the method handleServerDisconnected notifies that the server has disconnected
     */

    void handleServerDisconnected();

}

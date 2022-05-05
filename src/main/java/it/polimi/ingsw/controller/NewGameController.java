package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.exceptions.WrongGamePhaseException;
import it.polimi.ingsw.messages.GameStartedMessage;
import it.polimi.ingsw.messages.updatemessages.UpdateGameInfo;
import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.GameState;

import java.beans.PropertyChangeEvent;

public class NewGameController extends Controller {


    public NewGameController(GameInterface game) {
        super(game);
    }

    @Override
    public void addPlayer(String nickname) {
        System.out.println("add player controller");
        try {
            game.addPlayer(nickname);
        } catch (NicknameNotAvailableException | WrongGamePhaseException e) {
            e.printStackTrace();
        }
        if (game.getGameState() == GameState.PLANNING) {
            started = true;
            pcs.firePropertyChange("gameInfo", null, new UpdateGameInfo(game.getGameData()));
            pcs.firePropertyChange("gameStarted", null, new GameStartedMessage());
        }

    }

    public OpeningNewGameData createOpeningNewGameData() {
        return new OpeningNewGameData(getId(), game.getNumOfPlayers(), game.isExpertModeEnabled(), game.getPlayersNicknames());
    }

}

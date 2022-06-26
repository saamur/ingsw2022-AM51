package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.gamerecords.OpeningNewGameData;
import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.exceptions.WrongGamePhaseException;
import it.polimi.ingsw.messages.GameStartedMessage;
import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.GameState;

import java.util.List;

/**
 * The NewGameController class is the concrete implementation of the abstract class Controller for the newly created games
 *
 */
public class NewGameController extends Controller {

    /**
     * Constructs a Controller with the new Game given by parameter
     * @param game  the new Game to bound to this Controller
     */
    public NewGameController(GameInterface game) {
        super(game);
    }

    @Override
    public List<String> getPlayersNicknames () {
        return game.getPlayersNicknames();
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
            Lobby.getInstance().startControllerGame(this);
            pcs.firePropertyChange("gameStarted", null, new GameStartedMessage(game.getGameData()));
            SavedGameManager.saveRunningGame(game, getId());
        }

    }

    /**
     * The createOpeningNewGameData method creates a OpeningNewGameData object that represent the game bound to this controller
     * @return  an OpeningNewGameData object that represent the game bound to this controller
     */
    public OpeningNewGameData createOpeningNewGameData() {
        return new OpeningNewGameData(getId(), game.getNumOfPlayers(), game.isExpertModeEnabled(), game.getPlayersNicknames());
    }

}

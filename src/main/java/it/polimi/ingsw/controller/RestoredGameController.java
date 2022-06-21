package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.GameStartedMessage;
import it.polimi.ingsw.model.GameInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The RestoredGameController class is the concrete implementation of the abstract class Controller
 * for the previously saved games restored from file
 *
 */
public class RestoredGameController extends Controller {

    private final List<String> missingPlayers;
    private final LocalDateTime localDateTime;

    public RestoredGameController(GameInterface game, LocalDateTime localDateTime) {
        super(game);
        missingPlayers = game.getPlayersNicknames();
        this.localDateTime = localDateTime;
    }

    @Override
    public List<String> getPlayersNicknames() {
        List<String> nicknames = game.getPlayersNicknames();
        nicknames.removeAll(missingPlayers);
        return nicknames;
    }

    @Override
    public void addPlayer(String nickname) {
        missingPlayers.remove(nickname);
        if (missingPlayers.isEmpty()) {
            started = true;
            Lobby.getInstance().startControllerGame(this);
            pcs.firePropertyChange("gameStarted", null, new GameStartedMessage(game.getGameData()));
            SavedGameManager.saveRunningGame(game, getId());
        }
    }

    /**
     * The createOpeningRestoredGameData method creates a OpeningRestoredGameData object that represent the game bound to this controller
     * @return  an OpeningRestoredGameData object that represent the game bound to this controller
     */
    public OpeningRestoredGameData createOpeningRestoredGameData () {
        List<String> alreadyJoinedPlayers = game.getPlayersNicknames();
        alreadyJoinedPlayers.removeAll(missingPlayers);
        return new OpeningRestoredGameData(getId(), game.getNumOfPlayers(), game.isExpertModeEnabled(), localDateTime, alreadyJoinedPlayers, new ArrayList<>(missingPlayers));
    }

}

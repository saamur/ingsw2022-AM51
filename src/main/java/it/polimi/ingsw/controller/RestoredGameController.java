package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.messages.GameStartedMessage;
import it.polimi.ingsw.messages.updatemessages.UpdateGameInfo;
import it.polimi.ingsw.model.GameInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RestoredGameController extends Controller {

    private final List<String> missingPlayers;
    private final LocalDateTime localDateTime;

    public RestoredGameController(GameInterface game, LocalDateTime localDateTime) {
        super(game);
        missingPlayers = game.getPlayersNicknames();
        this.localDateTime = localDateTime;
    }

    @Override
    public void addPlayer(String nickname) {
        missingPlayers.remove(nickname);
        if (missingPlayers.isEmpty()) {
            started = true;
            Lobby.getInstance().startController(this);
            pcs.firePropertyChange("gameInfo", null, new UpdateGameInfo(game.getGameData()));
            pcs.firePropertyChange("gameStarted", null, new GameStartedMessage());
        }
    }

    public OpeningRestoredGameData createOpeningRestoredGameData () {
        List<String> alreadyJoinedPlayers = game.getPlayersNicknames();
        alreadyJoinedPlayers.removeAll(missingPlayers);
        return new OpeningRestoredGameData(getId(), game.getNumOfPlayers(), game.isExpertModeEnabled(), localDateTime, alreadyJoinedPlayers, new ArrayList<>(missingPlayers));
    }

}

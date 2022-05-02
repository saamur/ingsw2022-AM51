package it.polimi.ingsw.controller;

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
            //todo broadcast startGame
        }
    }

    public OpeningRestoredGameData createOpeningRestoredGameData () {
        List<String> alreadyJoinedPlayers = game.getPlayersNicknames();
        alreadyJoinedPlayers.removeAll(missingPlayers);
        return new OpeningRestoredGameData(game.getNumOfPlayers(), game.isExpertModeEnabled(), localDateTime, alreadyJoinedPlayers, new ArrayList<>(missingPlayers));
    }

}

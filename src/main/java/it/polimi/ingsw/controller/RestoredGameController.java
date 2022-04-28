package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameInterface;

import java.util.List;

public class RestoredGameController extends Controller {

    private final List<String> missingPlayers;

    public RestoredGameController(GameInterface game) {
        super(game);
        missingPlayers = game.getPlayersNicknames();
    }

    @Override
    public void addPlayer(String nickname) {
        missingPlayers.remove(nickname);
        //todo broadcast startGame if missingPlayers.isEmpty()
    }

}

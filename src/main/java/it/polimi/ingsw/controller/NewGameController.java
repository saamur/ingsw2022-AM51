package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.exceptions.WrongGamePhaseException;
import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.GameState;

public class NewGameController extends Controller {


    public NewGameController(GameInterface game) {
        super(game);
    }

    @Override
    public void addPlayer(String nickname) {
        try {
            game.addPlayer(nickname);
        } catch (NicknameNotAvailableException | WrongGamePhaseException e) {
            e.printStackTrace();
        }
        if (game.getGameState() == GameState.PLANNING) {
            started = true;
            //todo broadcast startGame
        }

    }
}

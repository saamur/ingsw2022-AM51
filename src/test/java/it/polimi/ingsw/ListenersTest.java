package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.NewGameController;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.player.Card;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.model.Clan.*;

public class ListenersTest {

    @Test
    public void conquerIslandTest(){

        //FIXME messaggio viene inviato due volte
        Game game = new Game(2, "Fede", false);
        Controller controller = new NewGameController(game);
        IslandManager islM = game.getIslandManager();

        try {
            game.addPlayer("Samu");
            if(game.getIndexCurrPlayer()==0) {
                game.chosenCard("Fede", Card.CHEETAH);
                game.chosenCard("Samu", Card.CAT);
            } else {
                game.chosenCard("Samu", Card.CAT);
                game.chosenCard("Fede", Card.CHEETAH);
            }
            Map<Clan, Integer> students = game.getPlayers()[0].getHall().getStudents();
            Clan chosenStudent = null;
            if(students.get(TOADS) > 0){
                chosenStudent = TOADS;
            } else if(students.get(FAIRIES) > 0){
                chosenStudent = FAIRIES;
            } else if(students.get(UNICORNS) > 0){
                chosenStudent = UNICORNS;
            } else if(students.get(PIXIES) > 0){
                chosenStudent = PIXIES;
            } else if(students.get(DRAGONS) > 0){
                chosenStudent = DRAGONS;
            } else {
                System.out.println("No students in the hall");
            }
            game.moveStudentToIsland("Fede", chosenStudent, 0);
        } catch (WrongGamePhaseException | NicknameNotAvailableException | NonExistingPlayerException | WrongTurnPhaseException | NotValidIndexException |WrongPlayerException | NotValidMoveException e){
            e.printStackTrace();
        }
    }
}

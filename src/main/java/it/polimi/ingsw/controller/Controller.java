package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.GameInterface;

public abstract class Controller {

    private final GameInterface game;
    private boolean closing;

    public Controller(GameInterface game) {
        this.game = game;
        closing = false;
    }

    public abstract void addPlayer (String nickname);

    public Message messageOnGame (String nickname, Message message) {

        Message answer = null;

        if (message instanceof ChosenCardMessage) {
            try {
                game.chosenCard(nickname, ((ChosenCardMessage) message).card());
                answer = new GenericMessage("Card chosen");
            } catch (WrongGamePhaseException | WrongPlayerException | NotValidMoveException e) {
                answer = new ErrorMessage(e.getMessage());
            } catch (NonExistingPlayerException e) {
                e.printStackTrace();
            }
        }

        else if (message instanceof MoveStudentToChamberMessage) {
            try {
                game.moveStudentToChamber(nickname, ((MoveStudentToChamberMessage) message).clan());
                answer = new GenericMessage("Student moved");
            } catch (WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException | NotValidMoveException e) {
                answer = new ErrorMessage(e.getMessage());
            } catch (NonExistingPlayerException e) {
                e.printStackTrace();
            }
        }

        else if (message instanceof MoveStudentToIslandMessage) {
            try {
                MoveStudentToIslandMessage tmp = (MoveStudentToIslandMessage) message;
                game.moveStudentToIsland(nickname, tmp.clan(), tmp.islandIndex());
                answer = new GenericMessage("Student moved");
            } catch (NotValidIndexException | WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException | NotValidMoveException e) {
                answer = new ErrorMessage(e.getMessage());
            } catch (NonExistingPlayerException e) {
                e.printStackTrace();
            }
        }

        else if (message instanceof MoveMotherNatureMessage) {
            try {
                game.moveMotherNature(nickname, ((MoveMotherNatureMessage) message).islandIndex());
                answer = new GenericMessage("Mother nature moved");
            } catch (NotValidIndexException | WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException | NotValidMoveException e) {
                answer = new ErrorMessage(e.getMessage());
            } catch (NonExistingPlayerException e) {
                e.printStackTrace();
            }
        }

        else if (message instanceof ChosenCloudMessage) {
            try {
                game.chosenCloud(nickname, ((ChosenCloudMessage) message).cloudIndex());
                answer = new GenericMessage("Cloud chosen");
            } catch (NotValidIndexException | WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException | NotValidMoveException e) {
                answer = new ErrorMessage(e.getMessage());
            } catch (NonExistingPlayerException e) {
                e.printStackTrace();
            }
        }

        else if (message instanceof EndTurnMessage) {
            try {
                game.endTurn(nickname);
                answer = new GenericMessage("You have ended your turn");
            } catch (WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException e) {
                answer = new ErrorMessage(e.getMessage());
            } catch (NonExistingPlayerException e) {
                e.printStackTrace();
            }
        }

        return answer;

    }

    public synchronized void clientDisconnected (String nickname) {

        if (!closing) {

            closing = true;
            Message message = new PlayerDisconnectedMessage(nickname);

            //todo send broadcast

        }

    }

}

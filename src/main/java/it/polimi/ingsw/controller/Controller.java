package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.GameInterface;

import java.util.List;

public abstract class Controller {

    private static int counter = 0;

    private final int id;

    protected final GameInterface game;
    protected boolean started;
    private boolean closing;

    public Controller(GameInterface game) {
        synchronized (Controller.class) {
            id = counter;
            counter++;
        }
        this.game = game;
        started = false;
        closing = false;
    }

    public int getId() {
        return id;
    }

    public boolean isStarted() {
        return started;
    }

    public List<String> getPlayersNicknames () {
        return game.getPlayersNicknames();
    }

    public abstract void addPlayer (String nickname);

    public synchronized Message messageOnGame (String nickname, Message message) {

        if (!started)
            return new ErrorMessage("You cannot make this move now");

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

        else if (message instanceof ActivateCharacterCardMessage) {
            try {
                game.activateCharacterCard(nickname, ((ActivateCharacterCardMessage) message).characterCardID());
                answer = new GenericMessage("You have activated " + ((ActivateCharacterCardMessage) message).characterCardID().name().toLowerCase());
            } catch (WrongGamePhaseException | ExpertModeNotEnabledException | WrongPlayerException | NotValidMoveException e) {
                answer = new ErrorMessage(e.getMessage());
            } catch (NonExistingPlayerException e) {
                e.printStackTrace();
            }
        }

        else if (message instanceof ApplyCharacterCardEffectMessage1) {
            try {
                game.applyCharacterCardEffect(nickname, ((ApplyCharacterCardEffectMessage1) message).islandIndex());
                answer = new GenericMessage("the effect has been applied");
            } catch (NotValidIndexException | WrongGamePhaseException | ExpertModeNotEnabledException | WrongPlayerException | NotValidMoveException e) {
                answer = new ErrorMessage(e.getMessage());
            } catch (NonExistingPlayerException e) {
                e.printStackTrace();
            }
        }

        else if (message instanceof ApplyCharacterCardEffectMessage2) {
            try {
                ApplyCharacterCardEffectMessage2 tmp = (ApplyCharacterCardEffectMessage2) message;
                game.applyCharacterCardEffect(nickname, tmp.islandIndex(), tmp.students1(), tmp.students2());
                answer = new GenericMessage("the effect has been applied");
            } catch (WrongGamePhaseException | ExpertModeNotEnabledException | WrongPlayerException | NotValidMoveException e) {
                answer = new ErrorMessage(e.getMessage());
            } catch (NonExistingPlayerException e) {
                e.printStackTrace();
            }
        }
        else if (message instanceof SetClanCharacterMessage) {
            try {
                game.setClanCharacter(nickname, ((SetClanCharacterMessage) message).clan());
                answer = new GenericMessage("the clan has been set");
            } catch (WrongGamePhaseException | ExpertModeNotEnabledException | WrongPlayerException | NotValidMoveException e) {
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
            //todo save game
            //todo remove this from lobby

        }

    }

}

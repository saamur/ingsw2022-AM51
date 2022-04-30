package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.GameInterface;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public abstract class Controller implements PropertyChangeListener { //FIXME LISTENER

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
        game.addListeners(this); //FIXME penso abbia senso metterlo nel costruttore
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

        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //FIXME propertyChange is called twice
        //TODO needs to be valid both for NewGame and RestoredGame
        /*
        Controller listens to: IslandManager V, Game V, Player, Clouds
         */
        /*
        Legend:
        MotherNature -> moved mother nature
        ConqueredIsland ->
        Merge ->
        movedStudentChamber
        movedStudentIsland
        chosenCloud
        activatedCharacter
        addedProhibitionCard
         */
        System.out.println("This is the object writing the sentences: " + this);
        switch(evt.getPropertyName()){ //FIXME aggiungere codice ai case, per ora solo temporaneo
            case "MotherNature" -> System.out.println("MotherNature moved");
            case "conqueredIsland" -> System.out.println("An island has been conquered");
            case "merge" -> System.out.println("Islands have been merged");
            case "movedStudentChamber" -> System.out.println("A student has been moved to a Chamber");
            case "movedStudentIsland" -> System.out.println("A student has been moved to an Island");
            case "chosenCloud" -> System.out.println("A cloud has been picked");
            case "activatedCharacter" -> System.out.println("A character has been activated");
            case "addedProhibition" -> System.out.println("The prohibition card has one more prohibition tile available");
        }
        System.out.println("This is the object firing the change: " + evt.getSource().toString());
    }

}

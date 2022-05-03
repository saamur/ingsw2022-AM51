package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.modeldata.HallData;
import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.player.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public abstract class Controller implements PropertyChangeListener {

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
        game.setListeners(this); //FIXME penso abbia senso metterlo nel costruttore
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

    /**
     * The method propertyChange is called after the method fire is executed from a class to which the controller is listening to.
     * If the property name is "playerModified" the controller will send the updated Player (data?) to all of the players in the game.
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
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
            case "MotherNature" -> {
                int islandIndex = (Integer) evt.getNewValue();
                Message update = new MoveMotherNatureMessage(islandIndex);
            }
            case "conqueredIsland" -> { //with conquered Island only the players will be changed, the island is modified with the modifiedIsland message
                Player oldConqueringPlayer = (Player) evt.getNewValue();
                //PlayerData oldConquering = new PlayerData(oldConqueringPlayer.getNickname(), );
                //Message update = new UpdatePlayer((Player) evt.getNewValue());

            }
            case "merge" -> System.out.println("Islands have been merged");
            case "chosenCloud" -> System.out.println("A cloud has been picked");
            case "activatedCharacter" -> System.out.println("A character has been activated");
            case "addedProhibition" -> System.out.println("The prohibition card has one more prohibition tile available");
            case "modifiedPlayer" -> System.out.println("The player has been modified");
            case "modifiedPlayers" -> System.out.println("Two or more players have been modified");
            case "filledClouds" -> System.out.println("The clouds have been filled");
            case "modifiedIsland" -> System.out.println("An island has been modified");
            case "addProhibition" -> System.out.println("The prohibition card has been modified");
            case "modifiedCharacter" -> System.out.println("modified character");
        }
        System.out.println("This is the object firing the change: " + evt.getSource().toString());
    }

}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.modeldata.CloudManagerData;
import it.polimi.ingsw.client.modeldata.IslandManagerData;
import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.updatemessages.*;
import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.clouds.CloudManager;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.player.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;

public abstract class Controller implements PropertyChangeListener {

    private static int counter = 0;

    private final int id;

    protected final GameInterface game;
    protected boolean started;
    private boolean closing;

    //The class Controller is listened by the ClientHandlerClass
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

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

        System.out.println("message on game");
        System.out.println(game.getGameState());
        System.out.println(game.getPlayersNicknames());
        System.out.println(started);

        if (!started)
            return new ErrorMessage("You cannot make this move now");

        Message answer = null;

        if (message instanceof ChosenCardMessage) {
            System.out.println(game.getGameState());
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

            pcs.firePropertyChange("disconnectedPlayer", null, message);

            if (game.getGameState() != GameState.INITIALIZATION) {
                game.removeListeners(this);
                try {
                    SavedGameManager.saveGame(game);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Lobby.getInstance().removeController(this);

        }

    }

    /**
     * The method propertyChange is called after the method fire is executed from a class to which the controller is listening to.
     * If the property name is "playerModified" the controller will send the updated Player (data?) to all of the players in the game.
     * @param evt is the PropertyChangeEvent that describes the object that has been modified
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        UpdateMessage update = null;
        switch(evt.getPropertyName()){ //FIXME aggiungere codice ai case, per ora solo temporaneo
            case "MotherNature" -> {
                int islandIndex = (Integer) evt.getNewValue();
                update = new UpdateMotherNaturePosition(islandIndex);
            }
            /*case "conqueredIsland" -> { //with conquered Island only the players will be changed, the island is modified with the modifiedIsland message
                Player oldConqueringPlayer = (Player) evt.getOldValue();
                Player newConqueringPlayer = (Player) evt.getNewValue();
                Message update1 = new UpdatePlayer(PlayerData.createPlayerData(oldConqueringPlayer));
                Message update2 = new UpdatePlayer(PlayerData.createPlayerData(newConqueringPlayer));
                //FIXME meglio così o con due "modifiedPlayer"?
            }*/
            case "merge" -> {
                IslandManager islandManager = (IslandManager) evt.getNewValue();
                update = new UpdateIslandManager(IslandManagerData.createIslandManagerData(islandManager));
            }
            case "chosenCloud" -> {
                int cloudIndex = (Integer) evt.getNewValue();
                update = new UpdateCloud(cloudIndex);
            }
            case "activatedCharacter" -> {
                CharacterCard characterCard = (CharacterCard) evt.getNewValue();
                update = new UpdateActivatedCard(characterCard.getCharacterID());
                //FIXME i'm not sure this is the right message, the playernickname is currPlayer
            }
            case "modifiedPlayer" -> {
                Player modifiedPlayer = (Player) evt.getNewValue();
                update = new UpdatePlayer(PlayerData.createPlayerData(modifiedPlayer));
            }
            case "filledClouds" -> {
                CloudManager cloudManager = (CloudManager) evt.getNewValue();
                update = new UpdateCloudManager(CloudManagerData.createCloudManagerData(cloudManager));
            }
            case "modifiedIsland" -> {
                int modifiedIsland = (Integer) evt.getNewValue();
                update = new UpdateIsland(modifiedIsland); //TODO Control all modifiedIsland fire are created with islandIndex and not island object
            }
            case "modifiedCharacter" -> {
                CharacterCard characterCard = (CharacterCard) evt.getNewValue();
                update = new UpdateCharacterCard(characterCard.getCharacterID());
            }
        }
        pcs.firePropertyChange("message", null, update);
    }

    public void setPropertyChangeListener (PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Controller{" +
                "id=" + id +
                '}';
    }
}

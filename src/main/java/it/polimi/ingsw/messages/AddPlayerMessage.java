package it.polimi.ingsw.messages;

/**
 * The AddPlayerMessage is the message sent to the server to add a player in the game
 * @param gameID the ID that identifies the game in which to add the player
 */
public record AddPlayerMessage(int gameID) implements Message {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "AddPlayerMessage{" +
                "gameID=" + gameID +
                '}';
    }

}

package it.polimi.ingsw.messages;

/**
 * addPlayerMessage gets a message to add a player in the game
 * @param gameID the ID that identifies the game in which to add the player
 */
public record AddPlayerMessage(int gameID) implements Message{

    @Override
    public String getMessage(){
        return this.toString();
    }
}

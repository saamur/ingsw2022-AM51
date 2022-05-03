package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

public record UpdateIsland(int modifiedIslandIndex) implements UpdateMessage {
    @Override
    public String getMessage(){
        return this.toString();
    }
}

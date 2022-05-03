package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.messages.Message;

public record UpdateCloud(int cloudIndex) implements UpdateMessage {
    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "ChosenCloudMessage{" +
                "cloudIndex=" + cloudIndex +
                '}';
    }



}

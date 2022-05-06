package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.CloudData;

public record UpdateCloud(CloudData cloudData) implements UpdateMessage {
    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "UpdateCloud{" +
                "cloudData=" + cloudData +
                '}';
    }
}

package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.messages.Message;

/**
 * ChosenCloudMessage gets a message to choose a cloud
 * @param cloudIndex index of the chosen cloud
 */
public record ChosenCloudMessage(int cloudIndex) implements Message {
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

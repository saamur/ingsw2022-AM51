package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.CloudManagerData;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

public record UpdateCloudManager(CloudManagerData cloudManagerData) implements UpdateMessage {
    @Override
    public String getMessage() {
        return toString();
    }
}

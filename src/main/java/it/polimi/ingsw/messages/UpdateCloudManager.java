package it.polimi.ingsw.messages;

import it.polimi.ingsw.client.modeldata.CloudManagerData;

public record UpdateCloudManager(CloudManagerData cloudManagerData) implements Message{
    @Override
    public String getMessage() {
        return toString();
    }
}

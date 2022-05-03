package it.polimi.ingsw.messages;

import it.polimi.ingsw.client.modeldata.IslandManagerData;

public record UpdateIslandManager(IslandManagerData islandManagerData) implements Message{
    @Override
    public String getMessage(){
        return toString();
    }
}

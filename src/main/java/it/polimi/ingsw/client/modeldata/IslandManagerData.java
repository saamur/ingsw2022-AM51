package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public record IslandManagerData(List<IslandData> islands,
                                int motherNaturePosition) implements Serializable {
    public static IslandManagerData createIslandManagerData (IslandManager islandManager){
        List<Island> islands = islandManager.getIslands();
        List<IslandData> islandData = new ArrayList<>();
        for(int i=0; i<islands.size(); i++){
            islandData.add(i, IslandData.createIslandData(islands.get(i)));
        }
        return new IslandManagerData(islandData, islands.indexOf(islandManager.getMotherNaturePosition()));
    }

}

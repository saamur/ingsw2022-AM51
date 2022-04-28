package it.polimi.ingsw.client.modeldata;

import java.io.Serializable;
import java.util.List;

public record IslandManagerData(List<IslandData> islands,
                                int motherNaturePosition) implements Serializable {
}

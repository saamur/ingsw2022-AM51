package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;

import java.io.Serializable;
import java.util.Map;

public record ChamberData(Map<Clan, Integer> students,
                          Map<Clan, Boolean> professors,
                          int coins,
                          Map<Clan, Integer> coinsGiven) implements Serializable {
}

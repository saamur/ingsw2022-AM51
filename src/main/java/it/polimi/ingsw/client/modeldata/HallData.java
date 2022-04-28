package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;

import java.io.Serializable;
import java.util.Map;

public record HallData(Map<Clan, Integer> students) implements Serializable {
}

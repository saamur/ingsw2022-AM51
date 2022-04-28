package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.player.TowerColor;

import java.io.Serializable;
import java.util.Map;

public record IslandData(int numberOfIslands,
                         Map<Clan, Integer> students,
                         TowerColor towerColor,
                         int numberOfTowers,
                         int numProhibitionCards) implements Serializable {
}

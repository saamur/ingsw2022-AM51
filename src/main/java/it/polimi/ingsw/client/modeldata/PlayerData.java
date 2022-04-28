package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.TowerColor;

import java.io.Serializable;

public record PlayerData(String nickname,
                         HallData hallData,
                         ChamberData chamberData,
                         TowerColor colorOfTowers,
                         int numberOfTowers,
                         Card currCard) implements Serializable {
}

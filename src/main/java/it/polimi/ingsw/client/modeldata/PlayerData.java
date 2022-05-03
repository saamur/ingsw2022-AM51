package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.controller.SavedGameData;
import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record PlayerData(String nickname,
                         HallData hallData,
                         ChamberData chamberData,
                         TowerColor colorOfTowers,
                         int numberOfTowers,
                         Card currCard) implements Serializable {

    public static PlayerData createPlayerData (Player player) {
        return new PlayerData(player.getNickname(), HallData.createHallData(player.getHall()), ChamberData.createChamberData(player.getChamber()), player.getColorOfTowers(), player.getNumberOfTowers(), player.getCurrCard());
    }
}

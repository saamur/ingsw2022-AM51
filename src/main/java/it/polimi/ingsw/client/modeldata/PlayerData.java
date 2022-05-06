package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;

import java.io.Serializable;
import java.util.Objects;

public record PlayerData(String nickname,
                         HallData hallData,
                         ChamberData chamberData,
                         TowerColor colorOfTowers,
                         int numberOfTowers,
                         Card currCard) implements Serializable {

    public static PlayerData createPlayerData (Player player) {
        return new PlayerData(player.getNickname(), HallData.createHallData(player.getHall()), ChamberData.createChamberData(player.getChamber()), player.getColorOfTowers(), player.getNumberOfTowers(), player.getCurrCard());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerData that = (PlayerData) o;
        return numberOfTowers == that.numberOfTowers && nickname.equals(that.nickname) && hallData.equals(that.hallData) && chamberData.equals(that.chamberData) && colorOfTowers == that.colorOfTowers && currCard == that.currCard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, hallData, chamberData, colorOfTowers, numberOfTowers, currCard);
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "nickname='" + nickname + '\'' +
                ", hallData=" + hallData +
                ", chamberData=" + chamberData +
                ", colorOfTowers=" + colorOfTowers +
                ", numberOfTowers=" + numberOfTowers +
                ", currCard=" + currCard +
                '}';
    }
}

package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public final class PlayerData implements Serializable {

    private final String nickname;
    private final HallData hallData;
    private final ChamberData chamberData;
    private final TowerColor colorOfTowers;
    private final int numberOfTowers;
    private final List<Card> availableCards;
    private Card currCard;

    public PlayerData(String nickname, HallData hallData, ChamberData chamberData, TowerColor colorOfTowers, int numberOfTowers, List<Card> availableCards, Card currCard) {
        this.nickname = nickname;
        this.hallData = hallData;
        this.chamberData = chamberData;
        this.colorOfTowers = colorOfTowers;
        this.numberOfTowers = numberOfTowers;
        this.availableCards = availableCards;
        this.currCard = currCard;
    }

    public static PlayerData createPlayerData(Player player) {
        return new PlayerData(player.getNickname(), HallData.createHallData(player.getHall()), ChamberData.createChamberData(player.getChamber()), player.getColorOfTowers(), player.getNumberOfTowers(), player.getDeck().getCards(), player.getCurrCard());
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

    public String getNickname() {
        return nickname;
    }

    public HallData getHallData() {
        return hallData;
    }

    public ChamberData getChamberData() {
        return chamberData;
    }

    public TowerColor getColorOfTowers() {
        return colorOfTowers;
    }

    public int getNumberOfTowers() {
        return numberOfTowers;
    }

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public Card getCurrCard() {
        return currCard;
    }

    public void setCurrCard(Card currCard) {
        this.currCard = currCard;
    }
}

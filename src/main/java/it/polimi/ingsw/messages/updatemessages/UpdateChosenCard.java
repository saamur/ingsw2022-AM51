package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.model.player.Card;

public record UpdateChosenCard(Card card, String playerNickname) implements UpdateMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public void updateGameData(GameData gameData) {
        for (PlayerData playerData : gameData.getPlayerData()) {
            if (playerData.getNickname().equals(playerNickname)) {
                playerData.setCurrCard(card);
                playerData.getAvailableCards().remove(card);
            }
        }
    }

    @Override
    public String toString() {
        return "ChosenCardMessage{" +
                "card=" + card +
                '}';
    }

}

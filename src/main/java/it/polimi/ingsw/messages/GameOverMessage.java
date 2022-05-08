package it.polimi.ingsw.messages;

import java.util.List;

/**
 * GameOverMessage receives the message announcing the winners of the game
 * @param nicknameWinners nicknames of the winners
 */

public record GameOverMessage(List<String> nicknameWinners) implements Message {

    @Override
    public String getMessage(){
        StringBuilder winners = new StringBuilder();
        winners.append("The winners are: ");
        for(String w : nicknameWinners){ //FIXME maybe need to add ','
            winners.append(w);
            winners.append(" ");
        }
        return winners.toString();
    }

    @Override
    public String toString() {
        return "GameOverMessage{" +
                "nicknameWinners=" + nicknameWinners +
                '}';
    }

}

package it.polimi.ingsw.messages;

import java.util.List;

/**
 * GameOverMessage receives the message announcing the winners of the game
 * @param winnersNickname nicknames of the winners
 */

public record GameOverMessage(List<String> winnersNickname) implements Message {

    @Override
    public String getMessage(){
        StringBuilder winners = new StringBuilder();
        winners.append("The winners are: ");
        for(String w : winnersNickname){ //FIXME maybe need to add ','
            winners.append(w);
            winners.append(" ");
        }
        return winners.toString();
    }

    @Override
    public String toString() {
        return "GameOverMessage{" +
                "winnersNickname=" + winnersNickname +
                '}';
    }

}

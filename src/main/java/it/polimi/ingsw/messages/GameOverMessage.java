package it.polimi.ingsw.messages;

import java.util.List;

/**
 * The GameOverMessage record models the message announcing the winners of the game
 * @param winnersNickname nicknames of the winners
 */

public record GameOverMessage(List<String> winnersNickname) implements Message {

    @Override
    public String getMessage(){
        StringBuilder winners = new StringBuilder();
        winners.append("The winners are: ");
        for(String w : winnersNickname){
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

package it.polimi.ingsw.messages;

import java.util.List;

public record GameOverMessage(List<String> nicknameWinners) implements Message{

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

}

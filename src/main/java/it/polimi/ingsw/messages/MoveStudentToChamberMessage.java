package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Clan;

public record MoveStudentToChamberMessage(Clan clan) implements Message{
    @Override
    public String getMessage(){
        return this.toString();
    }
}

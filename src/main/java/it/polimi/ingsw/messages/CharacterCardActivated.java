package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterID;

public record CharacterCardActivated(String nickname, CharacterCard characterCardActivated) implements Message{ //Not carachterID because maybe students on card have been modified
    @Override
    public String getMessage(){
        return this.toString();
    }
}

package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterID;

/**
 * CharacterCardActivated gets a message to activate a card for a player
 * @param nickname name of the player that wants to activate the card
 * @param characterCardActivated the card to activate
 */
public record CharacterCardActivated(String nickname, CharacterCard characterCardActivated) implements Message { //Not carachterID because maybe students on card have been modified

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "CharacterCardActivated{" +
                "nickname='" + nickname + '\'' +
                ", characterCardActivated=" + characterCardActivated +
                '}';
    }

}

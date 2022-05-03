package it.polimi.ingsw.messages;

/**
 * ApplyCharacterCardEffectMessage gets the message to activate the effect of an active card on a chosen island
 * @param islandIndex index of the island where to activate the effect
 */

public record ApplyCharacterCardEffectMessage1(int islandIndex) implements Message {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "ApplyCharacterCardEffectMessage1{" +
                "islandIndex=" + islandIndex +
                '}';
    }
}

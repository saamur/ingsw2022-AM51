package it.polimi.ingsw.messages;

public record ApplyCharacterCardEffectMessage1(int islandIndex) implements Message{
    @Override
    public String getMessage(){
        return this.toString();
    }
}

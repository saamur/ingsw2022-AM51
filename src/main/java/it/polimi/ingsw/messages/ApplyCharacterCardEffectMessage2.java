package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Clan;

import java.util.Map;

public record ApplyCharacterCardEffectMessage2(int islandIndex, Map<Clan, Integer> students1, Map<Clan, Integer> students2) implements Message{

    @Override
    public String getMessage() {
        return this.toString();
    }
}

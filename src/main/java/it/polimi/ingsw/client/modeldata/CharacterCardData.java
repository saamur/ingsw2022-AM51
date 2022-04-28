package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterID;

import java.io.Serializable;
import java.util.Map;

public record CharacterCardData(CharacterID characterID,
                                int cost,
                                int numProhibitionCards,
                                Map<Clan, Integer> students) implements Serializable {
}

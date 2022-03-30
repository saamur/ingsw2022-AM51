package it.polimi.ingsw;

public class CharacterCardCreator {

    public CharacterCardCreator(){}

    public CharacterCard createCharacterCard(CharacterID characterID) {

        switch (characterID) {
            case FARMER:
                return new ProfessorsCharacterCard(characterID);

            case GRANDMA:
                return new ProhibitionCharacterCard(characterID);

            case HERALD: case CENTAUR: case KNIGHT: case MUSHROOMPICKER:
                return new InfluenceCharacterCard(characterID);

            case POSTMAN:
                return new StepsCharacterCard(characterID);

            case MONK: case JESTER: case MINISTREL: case PRINCESS: case THIEF:
                return null;
        }

        return null;

    }

}

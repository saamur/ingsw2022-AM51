package it.polimi.ingsw.messages.updatemessages;

public record UpdateMotherNaturePosition(int islandIndex) implements UpdateMessage {
    @Override
    public String getMessage() {
        return toString();
    }
}

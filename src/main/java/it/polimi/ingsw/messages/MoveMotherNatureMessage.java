package it.polimi.ingsw.messages;

public record MoveMotherNatureMessage(int islandIndex) implements Message {
    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "islandIndex=" + islandIndex +
                '}';
    }
}

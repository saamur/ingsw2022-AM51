package it.polimi.ingsw.messages.updatemessages;

public record UpdateCloud(int cloudIndex) implements UpdateMessage {
    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "UpdateCloud{" +
                "cloudIndex=" + cloudIndex +
                '}';
    }

}

package it.polimi.ingsw.messages;

import java.io.Serializable;

public interface Message extends Serializable {

    /**
     * @return a text description of the message
     */
    String getMessage();

}

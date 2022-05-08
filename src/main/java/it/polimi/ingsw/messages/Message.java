package it.polimi.ingsw.messages;

import java.io.Serializable;

/**
 * Message interface will be implemented by all classes whose objects will be exchanged through the network
 *
 */
public interface Message extends Serializable {

    /**
     * @return a text description of the message
     */
    String getMessage();

}

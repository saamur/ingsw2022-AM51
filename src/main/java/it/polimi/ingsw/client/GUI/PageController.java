package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.messages.Message;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public abstract class PageController {
    protected GUI gui;

    public void setGui(GUI gui){
        this.gui = gui;
    }

    /**
     * The method sendMessage sends an object Message to the GUI's listeners. The only class that listens to the GUI is ServerHandler
     * @param message The message that the GUI wants the ServerHandler to receive
     * @see Message
     */
    public void sendMessage(Message message){
        gui.getListeners().firePropertyChange("message", null, message);
    }

    /**
     * The method handlerErrorMessage displays an Alert on the current Scene, describing what the problem is.
     * @param string A String explaining the error
     */
    public void handleErrorMessage(String string){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something is wrong");
            alert.setContentText(string);
            alert.showAndWait();
        });

    }
}


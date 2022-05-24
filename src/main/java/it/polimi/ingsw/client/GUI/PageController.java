package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.messages.Message;
import javafx.scene.control.Alert;

public abstract class PageController {
    protected GUI gui;

    public void setGui(GUI gui){
        this.gui = gui;
    }

    public void sendMessage(Message message){
        gui.getListeners().firePropertyChange("message", null, message);
    }

    public void handleErrorMessage(String string){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something is wrong");
        alert.setContentText(string);
        alert.showAndWait();
    }
}


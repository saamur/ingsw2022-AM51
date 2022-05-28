package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ServerHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.SocketException;


import static it.polimi.ingsw.constants.ConstantsGUI.GAMESELECTION;

public class ConnectionController extends PageController {

    @FXML TextField port;
    @FXML TextField address;

    public void connect(ActionEvent event){
        ServerHandler serverHandler = null;
        try {
            int portNumber = Integer.parseInt(port.getText());
            serverHandler = new ServerHandler(address.getText(), portNumber, gui);
            gui.setServerHandler(serverHandler);
            Thread serverHandlerThread = new Thread(serverHandler);
            gui.addPropertyChangeListener(serverHandler);
            serverHandlerThread.start();
        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong port");
            alert.setContentText("The port field is supposed to be a number");
            alert.showAndWait();

        } catch (IOException e) {
            if(e instanceof SocketException){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection error");
                alert.setHeaderText("Server not reachable");
                alert.setContentText("The server could not be reached.\nCheck if the parameters are correct or if the server is running");
                alert.showAndWait();
            }
        }
        if(serverHandler != null) {
            try {
                gui.setCurrScene(GAMESELECTION); //TODO test if disconnecting and reconnecting the data is erased
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

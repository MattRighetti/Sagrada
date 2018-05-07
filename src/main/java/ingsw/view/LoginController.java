package ingsw.view;

import ingsw.controller.network.socket.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LoginController implements SceneUpdater {

    @FXML
    private GridPane loginPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private ToggleButton RMIToggleButton;

    @FXML
    private ToggleGroup SocketRMIToggleGroup;

    @FXML
    private ToggleButton SocketToggleButton;

    @FXML
    private Button loginButton;

    ClientController clientController;
    GUIUpdater application;

    public LoginController() {

    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void setApplication(GUIUpdater application) {
        this.application = application;
    }

    @FXML
    void onLoginPressed(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        clientController.loginUser(username);
        application.launchSecondGUI();
    }

    @FXML
    void selectedRMI(ActionEvent event) {

    }

    @FXML
    void selectedSocket(ActionEvent event) {

    }

    @Override
    public void updateConnectedUsers(int connectedUsers) {
        application.updateConnectedUsers(connectedUsers);
    }
}


package ingsw.view;

import ingsw.controller.network.commands.ResponseHandler;
import ingsw.controller.network.socket.Client;
import ingsw.controller.network.socket.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class View extends Application {
    private String username;
    private ResponseHandler RMITransmitter;
    private ResponseHandler SocketTransmitter;
    private ResponseHandler currentTransmitter;
    private ClientController clientController;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        deployClient();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        LoginController loginController = fxmlLoader.getController();
        loginController.setClientController(clientController);
        loginController.setPrimaryStage(primaryStage);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Sagrada Game");
        primaryStage.show();
        System.out.println();
    }

    public void deployClient() throws IOException {
        Scanner fromKeyboard = new Scanner(System.in);
        System.out.println("Type host: ");
        String host = fromKeyboard.nextLine();
        System.out.println("Type port: ");
        int port = fromKeyboard.nextInt();
        Client client = new Client(host, port);
        client.connect();
        this.clientController = new ClientController(client, this);
    }
}

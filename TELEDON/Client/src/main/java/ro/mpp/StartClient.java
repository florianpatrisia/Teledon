package ro.mpp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mpp.protocol.ServicesProxy;
import ro.mpp.controller.HomeController;
import ro.mpp.controller.LoginController;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set.");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }

        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try{
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        }catch (NumberFormatException e){
            System.err.println("Wrong port number " + e.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }

        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IServices server = new ServicesProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("login-view.fxml"));

        Parent root=(Parent)loader.load();

        LoginController ctrl=(LoginController) loader.getController();
        ctrl.setServer(server);
        FXMLLoader cloader = new FXMLLoader(this.getClass().getClassLoader().getResource("home-view.fxml"));
        Parent croot = (Parent)cloader.load();
        HomeController homeCtrl=(HomeController) cloader.getController();
        homeCtrl.setServer(server);
        System.out.println("SERVER"+ server);
        ctrl.setHomeController(homeCtrl);
        ctrl.setParent(croot);
        primaryStage.setTitle("Autentificare");
        primaryStage.setScene(new Scene(root, 600.0, 300.0));
        primaryStage.show();

    }
}
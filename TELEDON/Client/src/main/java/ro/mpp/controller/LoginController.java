package ro.mpp.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ro.mpp.IServices;
import ro.mpp.TeledonException;
import ro.mpp.Voluntar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginController {
    private IServices server;
    private HomeController homeCtrl;

    @FXML
    private TextField TextFiledUsername;
    @FXML
    private PasswordField PasswordFiledParola;
    @FXML
    public Button Login;

    Parent mainChatParent;

    public void setServer(IServices s) throws Exception {
        this.server=s;
    }
    public void setParent(Parent p){
        mainChatParent=p;
    }
    public void handleLogin(ActionEvent actionEvent)
    {

        String usernameConectare = TextFiledUsername.getText();
        String parola = PasswordFiledParola.getText();
        String parolaCriptata = encryptPassword(parola);

        try
        {
            server.login(usernameConectare, parolaCriptata, homeCtrl);
            Stage stage=new Stage();
            stage.setTitle("Voluntar " + usernameConectare);
            stage.setScene(new Scene(mainChatParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    homeCtrl.logout();
                    System.exit(0);
                }
            });
            Voluntar crtVoluntar=new Voluntar(usernameConectare, parolaCriptata);
            stage.show();
            homeCtrl.setVoluntarSelectat(crtVoluntar);
            homeCtrl.initTabelCazuriCaritabile();
            homeCtrl.initTabelDonatori();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        } catch (TeledonException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Voluntar Window");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String encryptPassword(String password) {
        try {
            // Obține instanța MessageDigest pentru SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Aplica hash pe parola
            byte[] hashBytes = digest.digest(password.getBytes());

            // Converteste rezultatul la format hexazecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void setHomeController(HomeController homeController)
    {
        this.homeCtrl=homeController;
    }
}

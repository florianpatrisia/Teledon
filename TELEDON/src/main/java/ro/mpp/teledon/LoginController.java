package ro.mpp.teledon;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.mpp.teledon.controller.AddVoluntarController;
import ro.mpp.teledon.controller.MessageAlert;
import ro.mpp.teledon.model.Voluntar;
import ro.mpp.teledon.service.CazCaritabilService;
import ro.mpp.teledon.service.DonatieService;
import ro.mpp.teledon.service.DonatorService;
import ro.mpp.teledon.service.VoluntarService;
import ro.mpp.teledon.validatori.ValidationException;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class LoginController {
    @FXML
    public TextField TextFiledUsername;
    @FXML
    public PasswordField PasswordFiledParola;
    @FXML
    public Button Login;
    @FXML
    public Button ContNou;
    VoluntarService voluntarService;
    DonatorService donatorService;
    DonatieService donatieService;
    CazCaritabilService cazCaritabilService;
    private Voluntar voluntarSelectat;
    ObservableList<Voluntar> modelVoluntar = FXCollections.observableArrayList();


    public void setLoginController(VoluntarService voluntarService, DonatorService donatorService, CazCaritabilService cazCaritabilService, DonatieService donatieService)
    {
        this.voluntarService=voluntarService;
        this.donatorService=donatorService;
        this.cazCaritabilService=cazCaritabilService;
        this.donatieService=donatieService;
        initModel();
    }
    private void initModel()
    {
        Iterable<Voluntar> totiVoluntarii=voluntarService.findAllVoluntari();
        List<Voluntar> listaVoluntari= StreamSupport.stream(totiVoluntarii.spliterator(), false)
                .toList();
        modelVoluntar.setAll(listaVoluntari);
    }
    public void handleLogin()
    {
        String usernameConectare=TextFiledUsername.getText();
        String parola=PasswordFiledParola.getText();
        String parolaCriptata= encryptPassword(parola);
        try {
            voluntarSelectat=voluntarService.findByUsername(usernameConectare);
            if (voluntarSelectat==null)
                MessageAlert.showErrorMessage(null, "Voluntarul cu acest nume de utilizator nu exista!");
//                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Voluntarul cu acest nume de utilizator nu exista!", "Incearca din nou!");
            else
            if(!Objects.equals(voluntarSelectat.getParola(), parolaCriptata))
                MessageAlert.showErrorMessage(null, "Parola introdusa este gresita!");
            else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("home-view.fxml"));
                AnchorPane layout = loader.load();

                Stage LoginStage = new Stage();
                LoginStage.setTitle("Voluntar " + voluntarSelectat.getUsername());
                LoginStage.initModality(Modality.WINDOW_MODAL);
                LoginStage.setScene(new Scene(layout, Color.BLUE));

                HomeController controller = loader.getController();
                controller.setHomeController(voluntarSelectat, voluntarService, donatorService, cazCaritabilService,donatieService);
                LoginStage.show();
            }
            } catch (ValidationException e)
        {
        MessageAlert.showErrorMessage(null, e.getMessage());}
        catch (IOException e) {
        throw new RuntimeException(e);
        }
    }

    public void handleAddVoluntar() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("add-voluntar-view.fxml"));
        AnchorPane layout = loader.load();

        Stage LoginStage = new Stage();
        LoginStage.setTitle("Adauga voluntar");
        LoginStage.initModality(Modality.WINDOW_MODAL);
        LoginStage.setScene(new Scene(layout, Color.BLUE));

        AddVoluntarController controller=loader.getController();
        controller.setAddVoluntarController(voluntarService);
        LoginStage.show();
    }
    public static String encryptPassword(String password) {
        try {
            // Obține instanța MessageDigest pentru SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Aplică hash pe parolă
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
}

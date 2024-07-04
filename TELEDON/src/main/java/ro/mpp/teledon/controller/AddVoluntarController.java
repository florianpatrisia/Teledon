package ro.mpp.teledon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.mpp.teledon.model.Voluntar;
import ro.mpp.teledon.service.VoluntarService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class AddVoluntarController {
    @FXML
    public TextField TextFiledNume;
    @FXML
    public TextField TextFiledUsername;
    @FXML
    public TextField TextFiledParola;
    @FXML
    public Button ButtonAdauga;
    @FXML
    public Button ButtonIesire;
    protected VoluntarService voluntarService;

    ObservableList<Voluntar> modelVoluntar = FXCollections.observableArrayList();

    public void setAddVoluntarController(VoluntarService voluntarService)
    {
        this.voluntarService=voluntarService;
    }
    public void handleAddVoluntar()
    {
        String nume=TextFiledNume.getText();
        String username=TextFiledUsername.getText();
        String parola=TextFiledParola.getText();
        String parolaCriptata= encryptPassword(parola);

        Voluntar voluntar=new Voluntar(nume, username, parolaCriptata);
        List<Voluntar> totiVolutarii= StreamSupport.stream(voluntarService.findAllVoluntari().spliterator(), false)
                .toList();

        boolean voluntarExista = StreamSupport.stream(voluntarService.findAllVoluntari().spliterator(), false)
                .anyMatch(voluntar1 -> Objects.equals(voluntar1.getNume(), voluntar.getNume()) &&
                        Objects.equals(voluntar1.getUsername(), voluntar.getUsername()) &&
                        Objects.equals(voluntar1.getParola(), voluntar.getParola()));

        if (voluntarExista) {
            MessageAlert.showErrorMessage(null, "Voluntarul acesta deja există în baza de date!");
        } else {
            voluntarService.addVoluntar(voluntar);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Voluntar adăugat cu succes!",
                    "Voluntar adăugat cu succes în baza de date!");
        }
//        for(Voluntar voluntar1 : totiVolutarii)
//            if(Objects.equals(voluntar1.getNume(), voluntar.getNume()) && Objects.equals(voluntar1.getUsername(),
//                    voluntar.getUsername()) && Objects.equals(voluntar1.getParola(), voluntar.getParola()))
//                MessageAlert.showErrorMessage(null, "Voluntarul acesta deja exista in baza de date!");
//
//        voluntarService.addVoluntar(voluntar);
//        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Voluntar adaugat cu succes!","Voluntar adaugat cu succes in baza de date!");

    }

    public void handleClose()
    {
        Scene currentScene = ButtonIesire.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.close();
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

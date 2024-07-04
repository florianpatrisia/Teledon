package ro.mpp.teledon;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.mpp.teledon.LoginController;
import ro.mpp.teledon.controller.MessageAlert;
import ro.mpp.teledon.model.CazCaritabil;
import ro.mpp.teledon.model.Donatie;
import ro.mpp.teledon.model.Donator;
import ro.mpp.teledon.model.Voluntar;
import ro.mpp.teledon.service.CazCaritabilService;
import ro.mpp.teledon.service.DonatieService;
import ro.mpp.teledon.service.DonatorService;
import ro.mpp.teledon.service.VoluntarService;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class HomeController {
    protected VoluntarService voluntarService;
    protected CazCaritabilService cazCaritabilService;
    protected DonatorService donatorService;
    protected DonatieService donatieService;
    protected Voluntar voluntarSelectat;
    @FXML
    public TextField TextFiledNume;
    @FXML
    public TextField TextFiledAdresa;
    @FXML
    public TextField TextFiledTelefon;
    @FXML
    public TextField TextFiledSumaDonata;
    @FXML
    public TextField TextFiledNumeDonator;
//    @FXML
//    public TextField TextFiledAdresaDonator;
//    @FXML
//    public TextField TextFiledTelefonDonator;
    @FXML
    public Button ButtonAdaugaDonatie;
    @FXML
    public Button ButtonLogout;
    @FXML
    public Button ButtonCautaDonator;
    @FXML
    public TableView<CazCaritabil> TableViewCazCaritabil;
    @FXML
    public TableView<Donator> TableViewDonator;
    @FXML
    public TableColumn<Donator, String> ColumnNumeDonator;
    @FXML
    public TableColumn<Donator, String> ColumnAdresaDonator;
    @FXML
    public TableColumn<Donator, String> ColumnTelefonDonator;
    @FXML
    public TableColumn<CazCaritabil, String> ColumnNumeCazCaritabil;
    @FXML
    public TableColumn<CazCaritabil, Integer> ColumnSumaStransaCazCaritabil;
    @FXML
    public TableColumn<CazCaritabil, Integer> ColumnSumaFinalaCazCaritabil;
    public Donator donator3;

    ObservableList<CazCaritabil> modelCazCaritabil = FXCollections.observableArrayList();
    ObservableList<Donator> modelDonator = FXCollections.observableArrayList();

    public void setHomeController(Voluntar voluntarSelectat, VoluntarService voluntarService, DonatorService donatorService, CazCaritabilService cazCaritabilService, DonatieService donatieService)
    {
        this.voluntarSelectat=voluntarSelectat;
        this.voluntarService=voluntarService;
        this.donatorService=donatorService;
        this.cazCaritabilService=cazCaritabilService;
        this.donatieService=donatieService;
        initModel();
    }
    private void initModel()
    {
        List<CazCaritabil> listaCazuriCaritabile= StreamSupport.stream(cazCaritabilService.findAllCazuriCaritabile().spliterator(), false)
                .toList();
        modelCazCaritabil.setAll(listaCazuriCaritabile);

        List<Donator> listaDonatori=StreamSupport.stream(donatorService.findAllDonatori().spliterator(), false)
                .toList();
        modelDonator.setAll(listaDonatori);
    }
    @FXML
    private void initialize()
    {
        ColumnNumeCazCaritabil.setCellValueFactory(new PropertyValueFactory<>("nume"));
        ColumnSumaStransaCazCaritabil.setCellValueFactory(new PropertyValueFactory<>("sumaStransa"));
        ColumnSumaFinalaCazCaritabil.setCellValueFactory(new PropertyValueFactory<>("sumaFinala"));

        ColumnNumeDonator.setCellValueFactory(new PropertyValueFactory<>("nume"));
        ColumnAdresaDonator.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        ColumnTelefonDonator.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        TableViewCazCaritabil.setItems(modelCazCaritabil);

        TableViewDonator.setItems(modelDonator);

//        this.TableViewDonator.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//
//        if (TableViewDonator!=null)
//        {
//            this.TableViewDonator.setOnMouseClicked(new EventHandler<MouseEvent>()
//            {
//                @Override
//                public void handle(MouseEvent event) {
//                    donator3=TableViewDonator.getSelectionModel().getSelectedItem();
//                    if(donator3!=null)
//                    {
//                        TextFiledNume.setText(donator3.getNume());
//                         TextFiledAdresa.setText(donator3.getAdresa());
//                          TextFiledTelefon.setText(donator3.getTelefon());
//                    }
//                }
//            });
//        }

        //nu merge
        TableViewDonator.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(oldValue, newValue)) {
                if (newValue != null) {
                // Setarea numelui, adresei și telefonului donatorului selectat în câmpurile corespunzătoare
                TextFiledNume.setText(newValue.getNume());
                TextFiledAdresa.setText(newValue.getAdresa());
                TextFiledTelefon.setText(newValue.getTelefon());
            }
            else {
                // Dacă nu este selectat niciun donator, se șterg valorile din câmpurile de text
                TextFiledNume.clear();
                TextFiledAdresa.clear();
                TextFiledTelefon.clear();
            }
        }});

    }

    public void handleAdaugaDonatie()
    {
        CazCaritabil cazCaritabilSelectat=TableViewCazCaritabil.getSelectionModel().getSelectedItem();
        Donator donator=new Donator();
        donator=TableViewDonator.getSelectionModel().getSelectedItem();
        Donatie donatie=new Donatie();
        if(donator!=null) {
            // se completeaza campurile cu datele donatorului selectat din lista
            TextFiledNume.setText(donator.getNume());
            TextFiledAdresa.setText(donator.getAdresa());
            TextFiledTelefon.setText(donator.getTelefon());
            Integer sumaDonata = Integer.valueOf(TextFiledSumaDonata.getText());
            donatie = new Donatie(donator, cazCaritabilSelectat, sumaDonata);
            donatieService.addDonatie(donatie);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Donatie adaugata cu succes!", "Un donator existent in baza de date a facut o noua doantie!\n"+donator.getNume()+" a donat "+donatie.getSumaDonata()+" lei!");

        }
        else {
            String nume = TextFiledNume.getText();
            String adresa = TextFiledAdresa.getText();
            String telefon = TextFiledTelefon.getText();
            Integer sumaDonata = Integer.valueOf(TextFiledSumaDonata.getText());
            Integer id= donatorService.findDonatorIdByNumeAdresaTelefon(nume, adresa, telefon);
            donator = new Donator(nume, adresa, telefon);
            if(id!=null)
            {
                // se extrage id-ul unui donator existent din baza de date
                donator.setId(id);
            }
            else
            {
                // se adauga un nou doantor in baza de date si se extrage id-ul sau
                donatorService.addDonator(donator);
                Integer id2= donatorService.findDonatorIdByNumeAdresaTelefon(nume, adresa, telefon);
                donator.setId(id2);
            }
            donatie = new Donatie(donator, cazCaritabilSelectat, sumaDonata);
            donatieService.addDonatie(donatie);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Donatie adaugata cu succes!", "Donator nou adaugat in baza de date!\n"+donator.getNume()+" a donat "+donatie.getSumaDonata()+" lei!");


        }
      }

    public void handleCautaDonatorDupaNume()
    {
        String nume=TextFiledNumeDonator.getText();
        Predicate<Donator> verificaNume=a-> Objects.equals(a.getNume(), nume);
        List<Donator> listaDonatori=StreamSupport.stream(donatorService.findAllDonatori().spliterator(), false)
                .filter(verificaNume)
                .toList();
        modelDonator.setAll(listaDonatori);
        TableViewDonator.setItems(modelDonator);
    }
    public void handleLogout() throws IOException {
        // Obțineți scena curentă
        Scene currentScene = ButtonLogout.getScene();
        // Obțineți fereastra asociată scenei curente și închideți-o
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.close();

    }
}

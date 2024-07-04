package ro.mpp.controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ro.mpp.*;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class HomeController implements IObserver{

    public Button ButtonLogout;
    protected IServices server;
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

    @FXML
    public Button ButtonAdaugaDonatie;

    @FXML
    public Button ButtonCautaDonator;
    @FXML
    public Button ButtonGoleste;
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

    ObservableList<CazCaritabil> modelCazCaritabil = FXCollections.observableArrayList();
    ObservableList<Donator> modelDonator = FXCollections.observableArrayList();

    public HomeController() {
        System.out.println("Home controller ...");
    }

    public void setServer(IServices service) throws Exception
    {
        this.server=service;
    }
    public void setVoluntarSelectat(Voluntar voluntar)
    {
        this.voluntarSelectat=voluntar;
    }

    @FXML
    private void initialize() throws Exception {
        ColumnNumeCazCaritabil.setCellValueFactory(new PropertyValueFactory<>("nume"));
        ColumnSumaStransaCazCaritabil.setCellValueFactory(new PropertyValueFactory<>("sumaStransa"));
        ColumnSumaFinalaCazCaritabil.setCellValueFactory(new PropertyValueFactory<>("sumaFinala"));

        ColumnNumeDonator.setCellValueFactory(new PropertyValueFactory<>("nume"));
        ColumnAdresaDonator.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        ColumnTelefonDonator.setCellValueFactory(new PropertyValueFactory<>("telefon"));

        TableViewCazCaritabil.setItems(modelCazCaritabil);

        TableViewDonator.setItems(modelDonator);

        TableViewDonator.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(oldValue, newValue)) {
                if (newValue != null) {
                    // Setarea numelui, adresei si telefonului donatorului selectat in campurile corespunzatoare
                    TextFiledNume.setText(newValue.getNume());
                    TextFiledAdresa.setText(newValue.getAdresa());
                    TextFiledTelefon.setText(newValue.getTelefon());
                }
                else {
                    // Daca nu este selectat niciun donator, se șterg valorile din campurile de text
                    TextFiledNume.setText("");
                    TextFiledAdresa.setText("");
                    TextFiledTelefon.setText("");
                }
            }});

    }

    public void initCazuriCaritabile(List<CazCaritabil> cazCaritabilList)
    {
        modelCazCaritabil.setAll(cazCaritabilList);
        TableViewCazCaritabil.setItems(modelCazCaritabil);
    }

    void logout() {
        System.out.println("Log out: username:"+voluntarSelectat.getUsername()+ " parola "+ voluntarSelectat.getParola());
        try {
            server.logout(voluntarSelectat, this);
        } catch (TeledonException e) {
            System.out.println("Logout error " + e);
        }

    }
    @FXML
    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void handleAdaugaDonatie() throws Exception {
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
            server.addDonatie(cazCaritabilSelectat,donator, sumaDonata);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Donatie adaugata cu succes!", "Un donator existent in baza de date a facut o noua doantie!\n"+donator.getNume()+" a donat "+donatie.getSumaDonata()+" lei!");

            initTabelCazuriCaritabile();
        }
//        else {
//            String nume = TextFiledNume.getText();
//            String adresa = TextFiledAdresa.getText();
//            String telefon = TextFiledTelefon.getText();
//            Integer sumaDonata = Integer.valueOf(TextFiledSumaDonata.getText());
//            Integer id= server.findDonatorIdByNumeAdresaTelefon(nume, adresa, telefon);
//            donator = new Donator(nume, adresa, telefon);
//            if(id!=null)
//            {
//                // se extrage id-ul unui donator existent din baza de date
//                donator.setId(id);
//            }
//            else
//            {
//                // se adauga un nou doantor in baza de date si se extrage id-ul sau
//                server.addDonator(donator);
//                Integer id2= donatorService.findDonatorIdByNumeAdresaTelefon(nume, adresa, telefon);
//                donator.setId(id2);
//            }
//            donatie = new Donatie(donator, cazCaritabilSelectat, sumaDonata);
//            donatieService.addDonatie(donatie);
//            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Donatie adaugata cu succes!", "Donator nou adaugat in baza de date!\n"+donator.getNume()+" a donat "+donatie.getSumaDonata()+" lei!");
//            handleGolesteCampurile();
//        }
    }

    public void handleCautaDonatorDupaNume() throws Exception {
        String nume=TextFiledNumeDonator.getText();
        Predicate<Donator> verificaNume= a-> Objects.equals(a.getNume(), nume);
        List<Donator> listaDonatori=StreamSupport.stream(server.cautaDonatori(nume).spliterator(), false)
                .filter(verificaNume)
                .toList();
        modelDonator.setAll(listaDonatori);
        TableViewDonator.setItems(modelDonator);
    }

    public void handleGolesteCampurile()
    {
        TextFiledNume.setText("");
        TextFiledAdresa.setText("");
        TextFiledTelefon.setText("");
        TextFiledSumaDonata.setText("");
        TextFiledNumeDonator.setText("");
    }


    @Override
    public void updateAddDonatie(List<CazCaritabil> cazCaritabil) {
        Platform.runLater(()->{
            try {
                System.out.println("CONTROLLLER: Donatie noua");
                initCazuriCaritabile(cazCaritabil);
                System.out.println("Dupa initialize");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void initTabelCazuriCaritabile() throws Exception {
        if (server != null) {
            List<CazCaritabil> listaCazuriCaritabile = StreamSupport.stream(server.findAllCazCaritabil().spliterator(), false)
                    .toList();
            modelCazCaritabil.setAll(listaCazuriCaritabile);
        } else {
            System.err.println("Serverul nu este initializat in HomeController.");
        }
    }
    public void initTabelDonatori() throws Exception {
        if (server != null) {
//            String nume=TextFiledNumeDonator.getText();
            List<Donator> listaDonatori = StreamSupport.stream(server.cautaDonatori("").spliterator(), false)
                    .toList();
            modelDonator.setAll(listaDonatori);
        } else {
            System.err.println("Serverul nu este initializat in HomeController.");
        }
    }
}

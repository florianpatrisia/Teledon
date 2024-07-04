package ro.mpp.teledon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.mpp.teledon.model.Donatie;
import ro.mpp.teledon.model.Donator;
import ro.mpp.teledon.model.Voluntar;
import ro.mpp.teledon.repository.*;
import ro.mpp.teledon.service.CazCaritabilService;
import ro.mpp.teledon.service.DonatieService;
import ro.mpp.teledon.service.DonatorService;
import ro.mpp.teledon.service.VoluntarService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        String environment = "real";
        String jdbcUrl = props.getProperty("jdbc.url." + environment);
        Properties jdbcProps = new Properties();
        jdbcProps.setProperty("jdbc.url", jdbcUrl);

//        VoluntarDBRepository voluntarDBRepositor=new VoluntarDBRepository(jdbcProps);
//        DonatorDBRepository donatorDBRepository=new DonatorDBRepository(jdbcProps);
//        CazCaritabilDBRepository cazCaritabilDBRepository=new CazCaritabilDBRepository(jdbcProps);
//        DonatieDBRepository donatieDBRepository=new DonatieDBRepository(jdbcProps, donatorDBRepository, cazCaritabilDBRepository);

        IVoluntarRepository voluntarDBRepository=new VoluntarDBRepository(jdbcProps);
        IDonatorRepository donatorDBRepository=new DonatorDBRepository(jdbcProps);
        ICazCaritabilRepository cazCaritabilDBRepository=new CazCaritabilDBRepository(jdbcProps);
        IDonatieRepository donatieDBRepository=new DonatieDBRepository(props, donatorDBRepository, cazCaritabilDBRepository);


        VoluntarService voluntarService=new VoluntarService(voluntarDBRepositor);
        DonatorService donatorService=new DonatorService(donatorDBRepository);
        CazCaritabilService cazCaritabilService=new CazCaritabilService(cazCaritabilDBRepository);
        DonatieService donatieService=new DonatieService(cazCaritabilDBRepository, donatorDBRepository, donatieDBRepository);

        FXMLLoader messageLoader = new FXMLLoader();
        messageLoader.setLocation(getClass().getResource("login-view.fxml"));
        AnchorPane messageTaskLayout = messageLoader.load();
        primaryStage.setTitle("Autentificare");
        primaryStage.setScene(new Scene(messageTaskLayout));


        LoginController loginController=messageLoader.getController();
        loginController.setLoginController(voluntarService, donatorService, cazCaritabilService, donatieService);

        primaryStage.setWidth(800);
        primaryStage.show();
    }
}

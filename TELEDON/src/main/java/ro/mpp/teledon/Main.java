package ro.mpp.teledon;

import ro.mpp.teledon.repository.CazCaritabilDBRepository;
import ro.mpp.teledon.repository.DonatieDBRepository;
import ro.mpp.teledon.repository.DonatorDBRepository;
import ro.mpp.teledon.repository.VoluntarDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
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


        VoluntarDBRepository voluntarDBRepositor=new VoluntarDBRepository(jdbcProps);
        DonatorDBRepository donatorDBRepository=new DonatorDBRepository(jdbcProps);
        CazCaritabilDBRepository cazCaritabilDBRepository=new CazCaritabilDBRepository(jdbcProps);
        DonatieDBRepository donatieDBRepository=new DonatieDBRepository(jdbcProps, donatorDBRepository, cazCaritabilDBRepository);

        voluntarDBRepositor.findAll();
        donatorDBRepository.findAll();
        cazCaritabilDBRepository.findAll();
        donatieDBRepository.findAll();

    }
}
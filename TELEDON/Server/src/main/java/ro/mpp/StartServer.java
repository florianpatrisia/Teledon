package ro.mpp;

import ro.mpp.server.AbstractServer;
import ro.mpp.server.ConcurrentServer;
import ro.mpp.server.ServerException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort=55555;
    public static void main(String[] args) throws Exception {
        Properties serverProps=new Properties();
        try{
            serverProps.load(StartServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        }
        catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }

        IVoluntarRepository voluntarDBRepository=new VoluntarDBRepository(serverProps);
        IDonatorRepository donatorDBRepository=new DonatorDBRepository(serverProps);
        ICazCaritabilRepository cazCaritabilDBRepository=new CazCaritabilDBRepository(serverProps);
        IDonatieRepository donatieDBRepository=new DonatieDBRepository(serverProps, donatorDBRepository, cazCaritabilDBRepository);

//        AICI CU HIBERNATE
//        ICazCaritabilRepositoryHibernate cazCaritabilRepositoryHibernate=new CazCaritabilHibernateRepository();
//        IDonatieRepository donatieDBRepository=new DonatieDBRepository(serverProps, donatorDBRepository, cazCaritabilRepositoryHibernate);

        IServices services=new ServicesImpl(voluntarDBRepository, donatorDBRepository, cazCaritabilDBRepository, donatieDBRepository);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong Port Number" + nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: "+serverPort);
        AbstractServer server = new ConcurrentServer(serverPort, services);
        try{
            server.start();
        } catch (ServerException e){
            System.err.println("Error starting the server " + e.getMessage());
        }
    }

}

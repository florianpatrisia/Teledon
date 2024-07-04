package ro.mpp.protocol;

import ro.mpp.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;


    public ClientWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch ( IOException e){
            e.printStackTrace();
        }
    }

    public void run() {
        while (connected){
            try{
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if (response!=null){
                    sendResponse((Response) response);
                }
            }catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (TeledonException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try{
            input.close();
            output.close();
            connection.close();
        }catch (IOException e){
            System.out.println("Error : " + e);
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response " + response);
        synchronized (output){
            output.writeObject(response);
            output.flush();
        }
    }

//    @Override
//    public void handleAdaugaDonatie(CazCaritabil cazCaritabil) {
//        System.out.println("Donatie aduagata:" +cazCaritabil);
//        try{
//            sendResponse(new DonatieNouaResponse((cazCaritabil));
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }

    private Response handleRequest(Request request) throws Exception {
        Response response = null;
        if(request instanceof LoginRequest)
        {
            System.out.println("Login request ...");
            LoginRequest loginRequest = (LoginRequest) request;
            Voluntar voluntar=loginRequest.getVoluntar();
            try
            {
                server.login(voluntar.getUsername(), voluntar.getParola(), this);
                return new OkResponse();
            }
            catch(Exception e)
            {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof LogoutRequest){
            System.out.println("Log out request ... ");
            LogoutRequest logOutRequest = (LogoutRequest) request;
            Voluntar voluntar = logOutRequest.getVoluntar();
            server.logout(voluntar, this);
            return new OkResponse();
        }
        if(request instanceof FindAllCazCaritabilRequest)
        {
            System.out.println("Find All Caz Caritabil Request ...");
            List<CazCaritabil> cazuriCaritabile=server.findAllCazCaritabil() ;
            return new FindAllCazCaritabilResponse(cazuriCaritabile);
        }
        if(request instanceof CautareDonatoriRequest)
        {
            System.out.println("Cauta Donatori dupa Nume Request ...");
            CautareDonatoriRequest cautareDonatoriRequest= (CautareDonatoriRequest) request;
            String nume=cautareDonatoriRequest.getNume();
            List<Donator> donatoriCautati=server.cautaDonatori(nume);
            return new CautareDonatoriResponse(donatoriCautati);
        }
        if(request instanceof AddDonatieRequest)
        {
            System.out.println("Aduagare Donatie Request ...");
            AddDonatieRequest addDonatieRequest= (AddDonatieRequest) request;
            CazCaritabil cazCaritabil=addDonatieRequest.getCazCaritabil();
            Donator donator=addDonatieRequest.getDonator();
            cazCaritabil.setId(1000);
            donator.setId(1000);
            int suma=addDonatieRequest.getSuma_donata();
            server.addDonatie(cazCaritabil, donator, suma);
            return new OkResponse();
        }

        return response;
    }

    @Override
    public void updateAddDonatie(List<CazCaritabil> cazCaritabil) {
        System.out.println("Update Donatie/Cazuri Caritabile aduagata:" +cazCaritabil);
        try{
            sendResponse(new UpdatedAddDonatieResponse(cazCaritabil));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

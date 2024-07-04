package ro.mpp.protocol;

import ro.mpp.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesProxy implements IServices {

    private String host;
    private int port;
    private IObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    private void initializeConnection(){
        try{
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendRequest(Request request) throws TeledonException{
        try{
            output.writeObject(request);
            output.flush();
        } catch(IOException e){
            throw new TeledonException("Error sending object " + e);
        }
    }

    private Response readResponse(){
        Response response = null;
        try{
            response = qresponses.take();
        } catch( InterruptedException e){
            e.printStackTrace();
        }
        return response;
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("Response received " + response);
                    if (response instanceof UpdateResponse) {
                        handleUpdate((UpdateResponse) response);
                    } else {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
    private void handleUpdate(UpdateResponse response)
    {
       /* if(response instanceof AddDonatieResponse)
        {
            AddDonatieResponse addDonatieResponse= (AddDonatieResponse) response;
            Donatie donatie=addDonatieResponse.getDonatie();
//            System.out.println("Donatie " + donatie);
            client.handleAdaugaDonatie(donatie);
        }*/
        if(response instanceof UpdatedAddDonatieResponse)
        {
            UpdatedAddDonatieResponse donatieNouaResponse = (UpdatedAddDonatieResponse) response;
            List<CazCaritabil> cazCaritabilList= (List<CazCaritabil>) donatieNouaResponse.getCazCaritabil();
            System.out.println("PROXI  Handle: Updated cazuri caritabile "+cazCaritabilList);
            client.updateAddDonatie(cazCaritabilList);
        }
    }


    @Override
    public Voluntar login(String username, String parola, IObserver client) throws Exception {
        initializeConnection();
        Voluntar voluntar = new Voluntar(username, parola);

        sendRequest(new LoginRequest(voluntar));
        Response response=readResponse();
        System.out.println(response);
        if(response instanceof OkResponse)
        {
            this.client=client;
            return voluntar;
        }
        if(response instanceof ErrorResponse)
        {
            ErrorResponse err=(ErrorResponse) response;
            closeConnection();
            throw new Exception(err.getMessage());
        }
        return null;
    }

    @Override
    public void addDonatie(CazCaritabil cazCaritabil, Donator donator, int suma_donata) throws TeledonException {
        sendRequest(new AddDonatieRequest(donator, cazCaritabil, suma_donata));
        Response response=readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new TeledonException(err.getMessage());
        }
    }

    @Override
    public List<CazCaritabil> findAllCazCaritabil() throws Exception {
        sendRequest(new FindAllCazCaritabilRequest());
        Response response=readResponse();
        FindAllCazCaritabilResponse resp=(FindAllCazCaritabilResponse) response;
        return resp.getCazuriCaritabile();
    }

    @Override
    public List<Donator> cautaDonatori(String nume) throws TeledonException {
        sendRequest(new CautareDonatoriRequest(nume));
        Response response=readResponse();
        CautareDonatoriResponse resp=(CautareDonatoriResponse) response;
        return resp.getDonatori();
    }

    @Override
    public void logout(Voluntar voluntar, IObserver client) throws TeledonException {
        sendRequest(new LogoutRequest(voluntar));
        Response response=readResponse();
        closeConnection();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new TeledonException(err.getMessage());
        }
    }

//    @Override
//    public Integer findDonatorIdByNumeAdresaTelefon(String nume, String adresa, String telefon) throws TeledonException {
////        return findDonatorIdByNumeAdresaTelefon(nume, adresa, telefon);
//        return 0;
//    }
}

package ro.mpp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server=null;
    public AbstractServer(int port){
        this.port=port;
    }
    public void start() throws ServerException {
        try{
            System.out.println("inainte");
            server = new ServerSocket(port);
            System.out.println("dupa"+ server);
            while(true){
                System.out.println("Waiting for clients ...");
                Socket client = server.accept();
                System.out.println("Client connected ...");
                processRequest(client);
            }
        } catch(IOException e){
            throw new ServerException("Starting server error ", e);
        } finally{
            stop();
        }
    }
    protected abstract void processRequest(Socket client);

    public void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}

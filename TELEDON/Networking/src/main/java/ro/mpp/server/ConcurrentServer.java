package ro.mpp.server;

import ro.mpp.IServices;
import ro.mpp.protocol.ClientWorker;

import java.net.Socket;

public class ConcurrentServer extends AbstractConcurrentServer{
    private IServices server;

    public ConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("ConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientWorker worker=new ClientWorker(server, client);
        Thread thread=new Thread(worker);
        return thread;
    }

//    @Override
//    public void stop() throws ServerException {
//        super.stop();
//        System.out.println("Stopping services ...");
//    }
}

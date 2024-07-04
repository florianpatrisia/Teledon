package ro.mpp;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServicesImpl implements IServices{
    private IVoluntarRepository voluntarRepository;
    private IDonatorRepository donatorRepository;
    private ICazCaritabilRepository cazCaritabilRepository;
    private IDonatieRepository donatieRepository;
    private Map<String, IObserver> loggedClients;

    public ServicesImpl(IVoluntarRepository voluntarRepository, IDonatorRepository donatorRepository, ICazCaritabilRepository cazCaritabilRepository, IDonatieRepository donatieRepository) {
        this.voluntarRepository = voluntarRepository;
        this.donatorRepository = donatorRepository;
        this.cazCaritabilRepository = cazCaritabilRepository;
        this.donatieRepository = donatieRepository;
        this.loggedClients = new ConcurrentHashMap<>();
    }


    @Override
    public synchronized Voluntar login(String username, String parola, IObserver client) throws TeledonException {
        System.out.println("LOGIN username: " + username + " parola: " + parola);
        Voluntar voluntarR=voluntarRepository.findByUsernameParola(username,parola);
        if(voluntarR!=null){
            if(loggedClients.get(voluntarR.getUsername())!=null)
                throw new TeledonException("User already logged in.");
            loggedClients.put(voluntarR.getUsername(), client);
        }
        else throw new TeledonException("Authentication failed.");
        System.out.println(voluntarR);
        return voluntarR;
    }

    private final int defaultThreadsNo=5;
    @Override
    public void addDonatie(CazCaritabil cazCaritabil, Donator donator, int suma_donata) throws TeledonException {
        int idDonator=donatorRepository.findIdByNumeAdresaTelefon(donator.getNume(), donator.getAdresa(), donator.getTelefon());
        donator.setId(idDonator);
        int idCaz=cazCaritabilRepository.findIdByNumeDescriereSuma(cazCaritabil.getNume(), cazCaritabil.getDescriere(), cazCaritabil.getSumaStransa(), cazCaritabil.getSumaFinala());
        cazCaritabil.setId(idCaz);

        Donatie donatie=new Donatie(donator, cazCaritabil, suma_donata);
        donatieRepository.add(donatie);

        List<CazCaritabil> cazCaritabilList= (List<CazCaritabil>) cazCaritabilRepository.findAll();
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(IObserver observer : loggedClients.values()){
                executor.execute(() -> {
                    observer.updateAddDonatie(cazCaritabilList);
                    System.out.println("Am trimis din ServiceImpl: "+cazCaritabilList);
                    // aici trimit lista de donatii
                });
        }
        executor.shutdown();


        //ala bun
//        System.out.println("Utiliatori conctati: ");
//        for(String username :loggedClients.keySet()){
//            if(loggedClients.get(username)!=client)
//            {
//                System.out.println(username);
//                loggedClients.get(username).handleAdaugaDonatie(donatie);
//            }
//        }

//        System.out.println("Conctati: ");
//        for (IObserver observer : loggedClients.values()) {
//            if (observer != client) {
//                System.out.println(observer.);
//                observer.handleAdaugaDonatie(donatie);
//            }
//        }
    }

    @Override
    public List<CazCaritabil> findAllCazCaritabil() throws Exception {
        List<CazCaritabil> cazuriCaritabile= (List<CazCaritabil>) cazCaritabilRepository.findAll();
        return cazuriCaritabile;
    }

    @Override
    public List<Donator> cautaDonatori(String nume) throws TeledonException {
        List<Donator> donatori= (List<Donator>) donatorRepository.findAll();
        return donatori;
    }

    @Override
    public void logout(Voluntar voluntar, IObserver client)  {
        loggedClients.remove(voluntar.getUsername());
    }
}

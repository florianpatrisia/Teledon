package ro.mpp.start;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.mpp.CazCaritabil;
import ro.mpp.client.CazuriCariabileClient;
import ro.mpp.rest.ServiceException;


public class StartRestClient {
    private final static CazuriCariabileClient cazuriCariabileClient = new CazuriCariabileClient();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        CazCaritabil cazCaritabilTest=new CazCaritabil("test nume", "test descriere", 0, 1000);

        try {

            //CazCaritabil result= restTemplate.postForObject("http://localhost:8080/teledon/cazuri-caritabile", cazCaritabilTest, CazCaritabil.class);
            //System.out.println("Result received: "+result);

            System.out.println("Adaugare Caz Caritabil Nou");
            CazCaritabil cazCaritabilCuId=cazuriCariabileClient.create(cazCaritabilTest);
            System.out.println("Caz Caritabil creat cu ID: " + cazCaritabilCuId.getId());

//            show(() -> System.out.println(cazuriCariabileClient.create(cazCaritabilTest)));


            System.out.println("\n Afisam toate Cazurile Caritabile");
            show(() ->{
                CazCaritabil[] rez= cazuriCariabileClient.getAll();
                for(CazCaritabil cazCaritabil:rez){
                    System.out.println("Caz Caritabil "+ cazCaritabil.getId()+ ": "+ cazCaritabil.getNume()+ ", "+
                            cazCaritabil.getDescriere()+  ", "+ cazCaritabil.getSumaStransa()+  ", "+ cazCaritabil.getSumaFinala());
                }
            });

//            System.out.println("\nUpdate Caz Caritabil");
//            CazCaritabil cazCaritabilNou=new CazCaritabil(ultimulID)


            System.out.println("\nInfo pentru cazul caritabil cu id-ul ");
            show(() -> System.out.println(cazuriCariabileClient.getById(6)));

            System.out.println("\nStergem Cazul Caritabil Adaugat");
            show(() -> cazuriCariabileClient.delete(cazCaritabilCuId.getId()));


            System.out.println("\n Afisam toate Cazurile Caritabile");
            show(() ->{
                CazCaritabil[] rez= cazuriCariabileClient.getAll();
                for(CazCaritabil cazCaritabil:rez){
                    System.out.println("Caz Caritabil "+ cazCaritabil.getId()+ ": "+ cazCaritabil.getNume()+ ", "+
                            cazCaritabil.getDescriere()+  ", "+ cazCaritabil.getSumaStransa()+  ", "+ cazCaritabil.getSumaFinala());
                }
            });




        } catch (RestClientException e)
        {
            System.out.println("Exception: " + e.getMessage());
        }

        System.out.println();
    }

    private static void show(Runnable task)
    {
        try{
            task.run();
        }catch (ServiceException e)
        {
            System.out.println("ServiceException: " + e.getMessage());
        }
    }
}

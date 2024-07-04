package ro.mpp.client;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import ro.mpp.CazCaritabil;
import ro.mpp.rest.ServiceException;

import java.util.concurrent.Callable;

public class CazuriCariabileClient {
    public static final String URL = "http://localhost:8080/teledon/cazuri-caritabile";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable)
    {
        try{
            return callable.call();
        }catch (ResourceAccessException | HttpClientErrorException e) {
            throw new ServiceException(e);
        }
        catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public CazCaritabil[] getAll()
    {
        return execute(() -> restTemplate.getForObject(URL, CazCaritabil[].class));
    }

    public CazCaritabil getById(Integer id)
    {
        return execute(() -> restTemplate.getForObject(String.format("%s/%d", URL, id), CazCaritabil.class));
    }

    public CazCaritabil create(CazCaritabil cazCaritabil)
    {
        return execute(() -> restTemplate.postForObject(URL, cazCaritabil, CazCaritabil.class));
    }

    public void update(CazCaritabil cazCaritabil)
    {
        execute(() -> {
            restTemplate.put(String.format("%s/%d", URL, cazCaritabil.getId()), cazCaritabil);
            return null;
        });
    }

    public void delete(Integer id)
    {
        execute(() ->
        {
            restTemplate.delete(String.format("%s/%d", URL, id));
            return null;
        });
    }
}

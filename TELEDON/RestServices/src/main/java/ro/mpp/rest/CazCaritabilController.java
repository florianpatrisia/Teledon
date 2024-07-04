package ro.mpp.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mpp.CazCaritabil;
import ro.mpp.CazCaritabilDBRepository;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/teledon/cazuri-caritabile")

public class CazCaritabilController {

    @Autowired
    private CazCaritabilDBRepository cazCaritabilRepository;

    @RequestMapping(method= RequestMethod.GET)
    public CazCaritabil[] getAll()
    {
        System.out.println("Find All Cazuri Caritabile...");
        List<CazCaritabil> cazCaritabilList=new ArrayList<>();
        for(CazCaritabil cazCaritabil: cazCaritabilRepository.findAll())
        {
            cazCaritabilList.add(cazCaritabil);
        }
        return cazCaritabilList.toArray(new  CazCaritabil[cazCaritabilList.size()]);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Integer id)
    {
        System.out.println("Get by id "+ id + "...");
        CazCaritabil cazCaritabil=cazCaritabilRepository.findById(id);
        if(cazCaritabil == null)
            return new ResponseEntity<String>("Cazul Caritabil nu a fost gasit", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<CazCaritabil>(cazCaritabil, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public CazCaritabil create(@RequestBody CazCaritabil cazCaritabil)
    {
        System.out.println("Adaugare Caz Caritabl...");
        cazCaritabilRepository.add(cazCaritabil);
        return cazCaritabil;
//      return new ResponseEntity<>(cazCaritabil, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CazCaritabil update(@RequestBody CazCaritabil cazCaritabil)
    {
        System.out.println("Updateing Caz Caritabil...");
        cazCaritabilRepository.update(cazCaritabil, cazCaritabil.getId());
        return cazCaritabil;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id)
    {
        System.out.println("Deleting Caz Caritabil "+ id + "...");
        try
        {
            cazCaritabilRepository.delete(id);
            return new ResponseEntity<CazCaritabil>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            System.out.println("Controller Delete Caz Caritabil Exception");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public String cazCaritabilError(Exception e)
//    {
//        return e.getMessage();
//    }
}

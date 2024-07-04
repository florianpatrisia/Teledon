package ro.mpp.teledon.service;

import ro.mpp.teledon.model.CazCaritabil;
import ro.mpp.teledon.repository.CazCaritabilDBRepository;
import ro.mpp.teledon.repository.ICazCaritabilRepository;
import java.util.Collection;
import java.util.Properties;

public class CazCaritabilService {
    ICazCaritabilRepository cazCaritabilRepository;

    public CazCaritabilService(ICazCaritabilRepository cazCaritabilDBRepository) {
        this.cazCaritabilRepository = cazCaritabilDBRepository;
    }

    public void addCazCaritabil(CazCaritabil cazCaritabil) {
        cazCaritabilRepository.add(cazCaritabil);
    }

    public void deleteCazCaritabil(CazCaritabil cazCaritabil) {
        cazCaritabilRepository.delete(cazCaritabil);
    }

    public void updateCazCaritabil(CazCaritabil cazCaritabil, Integer id) {
        cazCaritabilRepository.update(cazCaritabil, id);
    }

    public CazCaritabil findCazCaritabilById(Integer id) {
        return cazCaritabilRepository.findById(id);
    }

    public Collection<CazCaritabil> findAllCazuriCaritabile() {
        return cazCaritabilRepository.getAll();
    }

    public Integer findCazCaritabilIdByNumeDescriereSuma(String nume, String descriere, Integer sumaDonata, Integer sumaFinala) {
        return cazCaritabilRepository.findIdByNumeDescriereSuma(nume, descriere, sumaDonata, sumaFinala);
    }
}

package ro.mpp.teledon.service;

import ro.mpp.teledon.model.Donatie;
import ro.mpp.teledon.repository.*;

import java.util.Collection;
import java.util.Properties;

public class DonatieService {
    private ICazCaritabilRepository cazCaritabilRepository;
    private IDonatorRepository donatorRepository;
    private IDonatieRepository donatieRepository;

    public DonatieService(ICazCaritabilRepository cazCaritabilRepository, IDonatorRepository donatorRepository, IDonatieRepository donatieDBRepository) {
        this.cazCaritabilRepository = cazCaritabilRepository;
        this.donatorRepository = donatorRepository;
        this.donatieRepository = donatieDBRepository;
    }

    public void addDonatie(Donatie donatie) {
        donatieRepository.add(donatie);
    }

    public void deleteDonatie(Donatie donatie) {
        donatieRepository.delete(donatie);
    }

    public void updateDonatie(Donatie donatie, Integer id) {
        donatieRepository.update(donatie, id);
    }

    public Donatie findDonatieById(Integer id) {
        return donatieRepository.findById(id);
    }

    public Collection<Donatie> findAllDonatii() {
        return donatieRepository.getAll();
    }
}

package ro.mpp.teledon.service;

import ro.mpp.teledon.model.Donator;
import ro.mpp.teledon.repository.DonatorDBRepository;
import ro.mpp.teledon.repository.IDonatorRepository;

import java.util.Collection;
import java.util.Properties;

public class DonatorService {
    IDonatorRepository donatorDBRepository;
    public DonatorService(IDonatorRepository donatorDBRepository) {
        this.donatorDBRepository = donatorDBRepository;
    }

    public void addDonator(Donator donator) {
        donatorDBRepository.add(donator);
    }

    public void deleteDonator(Donator donator) {
        donatorDBRepository.delete(donator);
    }

    public void updateDonator(Donator donator, Integer id) {
        donatorDBRepository.update(donator, id);
    }

    public Donator findDonatorById(Integer id) {
        return donatorDBRepository.findById(id);
    }

    public Collection<Donator> findAllDonatori() {
        return donatorDBRepository.getAll();
    }

    public Donator findDonatorByName(String nume) {
        return donatorDBRepository.findDonatorByName(nume);
    }

    public Integer findDonatorIdByNumeAdresaTelefon(String nume, String adresa, String telefon) {
        return donatorDBRepository.findIdByNumeAdresaTelefon(nume, adresa, telefon);
    }
}

package ro.mpp.teledon.repository;


import ro.mpp.teledon.model.Donator;

public interface IDonatorRepository extends IRepository<Donator, Integer> {
    Integer findIdByNumeAdresaTelefon(String nume, String adresa, String telefon);
    Donator findDonatorByName(String nume);
}
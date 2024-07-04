package ro.mpp.teledon.repository;


import ro.mpp.teledon.model.Donatie;

public interface IDonatieRepository extends IRepository<Donatie, Integer>
{
    Integer findIdByDonatorCazCaritabilSuma(Integer donatorId, Integer cazCaritabilId, Integer sumaDonata);

}
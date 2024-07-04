package ro.mpp;

public interface IDonatieRepository extends IRepository<Donatie, Integer>
{
    Integer findIdByDonatorCazCaritabilSuma(Integer donatorId, Integer cazCaritabilId, Integer sumaDonata);

}
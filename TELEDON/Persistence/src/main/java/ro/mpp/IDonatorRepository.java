package ro.mpp;

public interface IDonatorRepository extends IRepository<Donator, Integer> {
    Integer findIdByNumeAdresaTelefon(String nume, String adresa, String telefon);
    Donator findDonatorByName(String nume);
}
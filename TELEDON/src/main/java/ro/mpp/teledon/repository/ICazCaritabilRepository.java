package ro.mpp.teledon.repository;


import ro.mpp.teledon.model.CazCaritabil;

public interface ICazCaritabilRepository extends IRepository<CazCaritabil, Integer> {
    Integer findIdByNumeDescriereSuma(String nume, String descriere, Integer sumaDonata, Integer sumaFinala);
}


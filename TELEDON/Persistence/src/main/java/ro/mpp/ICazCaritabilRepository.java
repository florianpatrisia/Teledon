package ro.mpp;



public interface ICazCaritabilRepository extends IRepository<CazCaritabil, Integer> {

    Integer findIdByNumeDescriereSuma(String nume, String descriere, Integer sumaDonata, Integer sumaFinala);
}


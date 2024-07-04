package ro.mpp.hibernate;


import ro.mpp.IRepository;

public interface ICazCaritabilRepositoryHibernate extends IRepository<CazCaritabil, Integer> {
    Integer findIdByNumeDescriereSuma(String nume, String descriere, Integer sumaDonata, Integer sumaFinala);
}


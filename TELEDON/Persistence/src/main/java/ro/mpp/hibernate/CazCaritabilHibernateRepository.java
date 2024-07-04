package ro.mpp.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import ro.mpp.JdbcUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class CazCaritabilHibernateRepository implements ICazCaritabilRepositoryHibernate {
    @Override
    public Integer findIdByNumeDescriereSuma(String nume, String descriere, Integer sumaStransa, Integer sumaFinala) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from CazCaritabil where nume=?1 and descriere=?2 and sumaStransa = ?3 and sumaFinala = ?4 ", ro.mpp.hibernate.CazCaritabil.class)
                    .setParameter(1, nume)
                    .setParameter(2, descriere)
                    .setParameter(3, sumaStransa)
                    .setParameter(4, sumaFinala)
                    .getSingleResultOrNull()
                    .getId();
        }
    }

    @Override
    public void add(CazCaritabil elem) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void delete(CazCaritabil cazCaritabil) {
        HibernateUtils.getSessionFactory().inTransaction(session ->
                {
                    System.out.println("Se sterge Cazul Caritabil: "+ cazCaritabil);
                    if (cazCaritabil!=null)
                    {
                        session.remove(cazCaritabil);
                        session.flush();
                    }
                });
    }

    @Override
    public void update(CazCaritabil cazCaritabil, Integer id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(ro.mpp.hibernate.CazCaritabil.class, id))) {
                System.out.println("In update, am gasit cazul caritabil cu id-ul "+id);
                session.merge(cazCaritabil);
                session.flush();
            }
        });
    }

    @Override
    public ro.mpp.hibernate.CazCaritabil findById(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from CazCaritabil where id=?1 ", ro.mpp.hibernate.CazCaritabil.class)
                    .setParameter(1, id)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public Iterable<CazCaritabil> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from CazCaritabil ", CazCaritabil.class).getResultList();
        }
    }

    @Override
    public Collection<CazCaritabil> getAll() {
        return List.of();
    }
}

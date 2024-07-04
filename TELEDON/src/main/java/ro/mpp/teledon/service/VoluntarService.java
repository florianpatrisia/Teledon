package ro.mpp.teledon.service;

import ro.mpp.teledon.model.Voluntar;
import ro.mpp.teledon.repository.IVoluntarRepository;
import ro.mpp.teledon.repository.VoluntarDBRepository;

import java.util.Collection;
import java.util.Properties;

public class VoluntarService{
    IVoluntarRepository voluntarDBRepository;
    public VoluntarService(IVoluntarRepository voluntarDBRepository) {
        this.voluntarDBRepository = voluntarDBRepository;
    }

    public void addVoluntar(Voluntar voluntar) {
        voluntarDBRepository.add(voluntar);
    }

    public void deleteVoluntar(Voluntar voluntar) {
        voluntarDBRepository.delete(voluntar);
    }

    public void updateVoluntar(Voluntar voluntar, Integer id) {
        voluntarDBRepository.update(voluntar, id);
    }

    public Voluntar findVoluntarById(Integer id) {
        return voluntarDBRepository.findById(id);
    }

    public Collection<Voluntar> findAllVoluntari() {
        return voluntarDBRepository.getAll();
    }
    public Integer findVoluntarIdByNumeUsernameParola(String nume, String username, String parola) {
        return voluntarDBRepository.findIdByNumeUsernameParola(nume, username, parola);
    }

    public Voluntar findByUsername(String username)
    {
        return voluntarDBRepository.findByUsername(username);
    }
}

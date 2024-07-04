package ro.mpp.teledon.repository;

import ro.mpp.teledon.model.Voluntar;

public interface IVoluntarRepository extends IRepository<Voluntar, Integer> {
    Integer findIdByNumeUsernameParola(String nume, String username, String parola);
    Voluntar findByUsername(String username);
}
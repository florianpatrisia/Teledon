package ro.mpp;

public interface IVoluntarRepository extends IRepository<Voluntar, Integer> {
    Integer findIdByNumeUsernameParola(String nume, String username, String parola);
    Voluntar findByUsernameParola(String username, String parola);
}
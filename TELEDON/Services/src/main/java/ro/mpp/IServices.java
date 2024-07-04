package ro.mpp;

import java.util.List;
import java.util.Objects;

public interface IServices {
    Voluntar login(String username, String parola, IObserver client) throws Exception;

    void addDonatie(CazCaritabil cazCaritabil, Donator donator, int suma_donata) throws TeledonException;

    List<CazCaritabil> findAllCazCaritabil() throws Exception;
    List<Donator> cautaDonatori(String nume) throws TeledonException;

    void logout(Voluntar voluntar, IObserver client) throws TeledonException;

}

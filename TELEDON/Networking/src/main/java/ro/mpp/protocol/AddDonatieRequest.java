package ro.mpp.protocol;

import ro.mpp.CazCaritabil;
import ro.mpp.Donatie;
import ro.mpp.Donator;

public class AddDonatieRequest implements Request{
    private Donator donator;
    private CazCaritabil cazCaritabil;
    private int suma_donata;

    public AddDonatieRequest(Donator donator, CazCaritabil cazCaritabil, int suma_donata) {
        this.donator = donator;
        this.cazCaritabil = cazCaritabil;
        this.suma_donata = suma_donata;
    }

    public Donator getDonator() {
        return donator;
    }

    public CazCaritabil getCazCaritabil() {
        return cazCaritabil;
    }

    public int getSuma_donata() {
        return suma_donata;
    }
}

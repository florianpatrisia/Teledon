package ro.mpp.protocol;

import ro.mpp.Donatie;

public class AddDonatieResponse implements UpdateResponse{
    private Donatie donatie;

    public AddDonatieResponse(Donatie donatie) {
        this.donatie = donatie;
    }

    public Donatie getDonatie() {
        return donatie;
    }
}

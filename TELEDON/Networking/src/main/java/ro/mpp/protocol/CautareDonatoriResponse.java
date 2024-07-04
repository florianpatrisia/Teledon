package ro.mpp.protocol;

import ro.mpp.Donator;

import java.util.List;

public class CautareDonatoriResponse implements Response{
    private List<Donator> donatori;

    public CautareDonatoriResponse(List<Donator> donatori) {
        this.donatori = donatori;
    }

    public List<Donator> getDonatori() {
        return donatori;
    }
}

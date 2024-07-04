package ro.mpp.protocol;

public class CautareDonatoriRequest implements Request{
    private String nume;

    public CautareDonatoriRequest(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }
}

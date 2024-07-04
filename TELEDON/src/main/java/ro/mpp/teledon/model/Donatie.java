package ro.mpp.teledon.model;

public class Donatie extends Entity<Integer>{
    protected Donator donator;
    protected CazCaritabil cazCaritabil;
    protected Integer sumaDonata;

    public Donatie(Donator donator, CazCaritabil cazCaritabil, Integer sumaDonata) {
        this.donator = donator;
        this.cazCaritabil = cazCaritabil;
        this.sumaDonata = sumaDonata;
    }

    public Donatie() {
        this.donator = null;
        this.cazCaritabil=null;
        this.sumaDonata=0;
    }

    public Donator getDonator() {
        return donator;
    }

    public void setDonator(Donator donator) {
        this.donator = donator;
    }

    public CazCaritabil getCazCaritabil() {
        return cazCaritabil;
    }

    public void setCazCaritabil(CazCaritabil cazCaritabil) {
        this.cazCaritabil = cazCaritabil;
    }

    public Integer getSumaDonata() {
        return sumaDonata;
    }

    public void setSumaDonata(Integer sumaDonata) {
        this.sumaDonata = sumaDonata;
    }

    @Override
    public String toString() {
        return "Donatie{" +
                "donator=" + donator +
                ", cazCaritabil=" + cazCaritabil +
                ", sumaDonata=" + sumaDonata +
                ", id=" + id +
                "}\n";
    }
}

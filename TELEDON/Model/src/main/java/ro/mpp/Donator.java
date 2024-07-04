package ro.mpp;

import java.io.Serializable;

public class Donator extends Entity<Integer> implements Serializable {
    protected String nume, adresa, telefon;

    public Donator(String nume, String adresa, String telefon) {
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
    }

    public Donator() {
        this.nume = "";
        this.adresa="";
        this.telefon="";
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "Donator{" +
                "nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon='" + telefon + '\'' +
                ", id=" + id +
                "}\n";
    }
}

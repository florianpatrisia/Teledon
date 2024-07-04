package ro.mpp;

import java.io.Serializable;

public class CazCaritabil extends Entity<Integer> implements Serializable {
    protected String nume, descriere;
    protected Integer sumaStransa;
    protected Integer sumaFinala;

    public CazCaritabil(String nume, String descriere, Integer sumaStransa, Integer sumaFinala) {
        this.nume = nume;
        this.descriere = descriere;
        this.sumaStransa = sumaStransa;
        this.sumaFinala = sumaFinala;
    }

    public CazCaritabil() {
        this.nume = "";
        this.descriere="";
        this.sumaStransa=0;
        this.sumaFinala=0;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Integer getSumaStransa() {
        return sumaStransa;
    }

    public void setSumaStransa(Integer sumaStransa) {
        this.sumaStransa = sumaStransa;
    }

    public Integer getSumaFinala() {
        return sumaFinala;
    }

    public void setSumaFinala(Integer sumaFinala) {
        this.sumaFinala = sumaFinala;
    }

    @Override
    public String toString() {
        return "CazCaritabil{" +
                "nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                ", sumaStransa=" + sumaStransa +
                ", sumaFinala=" + sumaFinala +
                ", id=" + id +
                "}\n";
    }
}

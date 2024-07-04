package ro.mpp.hibernate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "caz_caritabil")
public class CazCaritabil extends ro.mpp.Entity<Integer> implements Serializable {
    @Id
    private Integer id;
    @GeneratedValue(strategy =IDENTITY)
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @NotNull
    @Column(name = "nume")
    protected String nume;

    @Column(name = "descriere")
    @NotNull
    private String descriere;

    @Column(name = "suma_stransa")
    @NotNull
    protected Integer sumaStransa;

    @NotNull
    @Column(name = "suma_finala")
    protected Integer sumaFinala;

    public CazCaritabil() {
    }

    public CazCaritabil(String nume, String descriere, Integer sumaStransa, Integer sumaFinala) {
        this.nume = nume;
        this.descriere = descriere;
        this.sumaStransa = sumaStransa;
        this.sumaFinala = sumaFinala;
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

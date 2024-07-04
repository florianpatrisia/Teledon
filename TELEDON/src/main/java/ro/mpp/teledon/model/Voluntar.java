package ro.mpp.teledon.model;

public class Voluntar extends Entity<Integer> {
    protected String nume, username, parola;
    public Voluntar(String nume, String username, String parola) {
        this.nume = nume;
        this.username = username;
        this.parola = parola;
    }

    public Voluntar() {
        this.nume = "";
        this.username="";
        this.parola="";
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return "Voluntar{" +
                "nume='" + nume + '\'' +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", id=" + id +
                "}\n";
    }
}

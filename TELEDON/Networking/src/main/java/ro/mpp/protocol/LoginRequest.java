package ro.mpp.protocol;

import ro.mpp.Voluntar;


public class LoginRequest implements Request {
    private Voluntar voluntar;

    public LoginRequest(Voluntar voluntar) {
        this.voluntar = voluntar;
    }

    public Voluntar getVoluntar() {
        return voluntar;
    }
}

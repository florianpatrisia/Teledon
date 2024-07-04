package ro.mpp.protocol;

import ro.mpp.Voluntar;

public class LogoutRequest implements Request {
    Voluntar voluntar;

    public LogoutRequest(Voluntar voluntar) {
        this.voluntar = voluntar;
    }

    public Voluntar getVoluntar() {
        return voluntar;
    }
}

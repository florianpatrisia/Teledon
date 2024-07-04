package ro.mpp.protocol;

import ro.mpp.CazCaritabil;

import java.util.List;

public class FindAllCazCaritabilResponse implements Response{
    private List<CazCaritabil> cazuriCaritabile;
    public FindAllCazCaritabilResponse(List<CazCaritabil> cazuriCaritabile) {
        this.cazuriCaritabile = cazuriCaritabile;
    }

    public List<CazCaritabil> getCazuriCaritabile() {
        return cazuriCaritabile;
    }
}

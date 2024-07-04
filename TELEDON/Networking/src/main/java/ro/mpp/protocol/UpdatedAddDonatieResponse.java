package ro.mpp.protocol;

import ro.mpp.CazCaritabil;

import java.util.List;

public class UpdatedAddDonatieResponse implements UpdateResponse{
    private List<CazCaritabil> cazCaritabil;

    public UpdatedAddDonatieResponse(List<CazCaritabil> cazCaritabil) {
        this.cazCaritabil = cazCaritabil;
    }

    public List<CazCaritabil> getCazCaritabil() {
        return cazCaritabil;
    }
}

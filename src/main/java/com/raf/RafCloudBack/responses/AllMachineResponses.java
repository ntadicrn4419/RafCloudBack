package com.raf.RafCloudBack.responses;

import com.raf.RafCloudBack.models.Machine;
import java.util.List;
public class AllMachineResponses {
    private List<Machine> machines;

    public AllMachineResponses(List<Machine> machines) {
        this.machines = machines;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }
}

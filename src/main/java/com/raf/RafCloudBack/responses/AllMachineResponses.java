package com.raf.RafCloudBack.responses;

import com.raf.RafCloudBack.models.Machine;
import java.util.List;
public class AllMachineResponses {
    private List<Machine> allMachines;

    public AllMachineResponses(List<Machine> allMachines) {
        this.allMachines = allMachines;
    }

    public List<Machine> getAllMachines() {
        return allMachines;
    }

    public void setAllMachines(List<Machine> allMachines) {
        this.allMachines = allMachines;
    }
}

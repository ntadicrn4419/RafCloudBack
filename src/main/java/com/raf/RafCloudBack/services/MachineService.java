package com.raf.RafCloudBack.services;
import com.raf.RafCloudBack.models.Machine;
import com.raf.RafCloudBack.models.MachineStatus;
import com.raf.RafCloudBack.models.User;
import com.raf.RafCloudBack.repositories.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;

@Service
public class MachineService {
    private final MachineRepository machineRepository;
    @Autowired
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public List<Machine> getAll() {
        return this.machineRepository.findAll();
    }

    public Machine findById(Long id) {
        return this.machineRepository.getById(id);
    }

    public Machine create(Machine machine) {
        return this.machineRepository.save(machine);
    }

    public void updateStatus(Machine machine) {
        Machine oldMachine = this.machineRepository.getById(machine.getId());
        oldMachine.setId(machine.getId());
        oldMachine.setUser(machine.getUser());
        oldMachine.setActive(machine.isActive());
        oldMachine.setStatus(machine.getStatus());
        this.machineRepository.save(oldMachine);

    }

    //soft delete -> setting attribute active to false
    public void delete(Machine machine) {
        Machine machineToDelete = this.machineRepository.getById(machine.getId());
        machineToDelete.setId(machine.getId());
        machineToDelete.setUser(machine.getUser());
        machineToDelete.setActive(false);
        machineToDelete.setStatus(machine.getStatus());
        this.machineRepository.save(machineToDelete);
    }

    public List<Machine> getUserMachines(Long id) {
        return this.machineRepository.findAll().stream().filter(machine -> machine.getUser().getId().equals(id)).toList();
    }

    public List<Machine> getFilteredUserMachines(User user, String name, String status, Timestamp runningStarted, Timestamp runningStopped, Timestamp createdAtLowerBound, Timestamp createdAtUpperBound) {
        System.out.println(runningStarted + ", " + runningStopped);
        MachineStatus machineStatus = null;
        if(status != null && (status.equals("RUNNING") || status.equals("STOPPED"))) {
            machineStatus = MachineStatus.valueOf(status);
        }
        if(name.equals("")) {
            name = null;
        }
        return this.machineRepository.findByFilters(user, machineStatus, name, runningStarted, runningStopped, createdAtLowerBound, createdAtUpperBound);
    }
}

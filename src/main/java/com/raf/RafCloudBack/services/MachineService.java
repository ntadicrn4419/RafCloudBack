package com.raf.RafCloudBack.services;
import com.raf.RafCloudBack.models.Machine;
import com.raf.RafCloudBack.models.User;
import com.raf.RafCloudBack.repositories.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        oldMachine.setCreatedBy(machine.getCreatedBy());
        oldMachine.setActive(machine.isActive());
        oldMachine.setStatus(machine.getStatus());
        this.machineRepository.save(oldMachine);

    }

    //soft delete -> setting attribute active to false
    public void delete(Machine machine) {
        Machine machineToDelete = this.machineRepository.getById(machine.getId());
        machineToDelete.setId(machine.getId());
        machineToDelete.setCreatedBy(machine.getCreatedBy());
        machineToDelete.setActive(false);
        machineToDelete.setStatus(machine.getStatus());
        this.machineRepository.save(machineToDelete);
    }
}

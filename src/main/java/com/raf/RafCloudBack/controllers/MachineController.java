package com.raf.RafCloudBack.controllers;

import com.raf.RafCloudBack.dto.MachineIdDto;
import com.raf.RafCloudBack.dto.UserEmailDto;
import com.raf.RafCloudBack.models.Machine;
import com.raf.RafCloudBack.models.MachineStatus;
import com.raf.RafCloudBack.models.User;
import com.raf.RafCloudBack.models.UserPermission;
import com.raf.RafCloudBack.responses.AllMachineResponses;
import com.raf.RafCloudBack.responses.AllUsersResponse;
import com.raf.RafCloudBack.services.AuthorisationService;
import com.raf.RafCloudBack.services.MachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@CrossOrigin
@RequestMapping("/api/machines")
public class MachineController {
    private final MachineService machineService;
    private final AuthorisationService authorisationService;

    @Autowired
    public MachineController(MachineService machineService, AuthorisationService authorisationService) {
        this.machineService = machineService;
        this.authorisationService = authorisationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllMachines() {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_SEARCH_MACHINES, email)) {
            List<Machine> machines = this.machineService.getAll();
            return ResponseEntity.ok(new AllMachineResponses((machines)));
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMachineById(@PathVariable("id") Long machineId) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_SEARCH_MACHINES, email)) {
            Machine machine = this.machineService.findById(machineId);
            return ResponseEntity.ok(machine);
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Machine> create(@Valid @RequestBody Machine machine) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_CREATE_MACHINES, email)) {
            this.machineService.create(machine);
            return ResponseEntity.ok(machine);
        }
        return ResponseEntity.status(403).build();
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatus(@Valid @RequestBody Machine machine) {
        String email = getContext().getAuthentication().getName();
        switch (machine.getStatus()) {
            case RUNNING:
                if (this.authorisationService.isAuthorised(UserPermission.CAN_START_MACHINES, email)) {
                    this.machineService.updateStatus(machine);
                    return ResponseEntity.ok(machine);
                }
                break;
            case STOPPED:
                if (this.authorisationService.isAuthorised(UserPermission.CAN_STOP_MACHINES, email)) {
                    this.machineService.updateStatus(machine);
                    return ResponseEntity.ok(machine);
                }
                break;
            default:
                return ResponseEntity.status(403).build();
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@Valid @RequestBody Machine machine) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_DESTROY_MACHINES, email)) {
            this.machineService.delete(machine);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(403).build();
    }
}

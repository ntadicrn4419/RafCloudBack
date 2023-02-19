package com.raf.RafCloudBack.controllers;

import com.raf.RafCloudBack.dto.MachineFiltersDto;
import com.raf.RafCloudBack.dto.MachineNameDto;
import com.raf.RafCloudBack.dto.MachineScheduleDto;
import com.raf.RafCloudBack.models.Machine;
import com.raf.RafCloudBack.models.MachineStatus;
import com.raf.RafCloudBack.models.User;
import com.raf.RafCloudBack.models.UserPermission;
import com.raf.RafCloudBack.responses.AllMachineResponses;
import com.raf.RafCloudBack.services.AuthorisationService;
import com.raf.RafCloudBack.services.MachineService;
import com.raf.RafCloudBack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@CrossOrigin
@RequestMapping("/api/machines")
public class MachineController {
    //TODO: check status codes
    private final MachineService machineService;
    private final UserService userService;
    private final AuthorisationService authorisationService;

    @Autowired
    public MachineController(MachineService machineService, UserService userService, AuthorisationService authorisationService) {
        this.machineService = machineService;
        this.userService = userService;
        this.authorisationService = authorisationService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMachinesInCloud() { //only for internal testing, user can not access it
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_SEE_ALL_MACHINES, email)) {
            List<Machine> machines = this.machineService.getAll();
            return ResponseEntity.ok(new AllMachineResponses((machines)));
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping()
    public ResponseEntity<?> getUserMachines() {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_SEARCH_MACHINES, email)) {
            Long signedInUserId = this.userService.findByEmail(email).getId();
            List<Machine> machines = this.machineService.getUserMachines(signedInUserId);
            return ResponseEntity.ok(new AllMachineResponses((machines)));
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping(value = "/search-by-filters", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findFilteredUserMachines(@Valid @RequestBody MachineFiltersDto dto) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_SEARCH_MACHINES, email)) {
            User user = this.userService.findByEmail(email);
            List<Machine> machines = this.machineService.getFilteredUserMachines(user, dto.getName(), dto.getStatus(), dto.getRunningStarted(), dto.getRunningStopped(), dto.getCreatedAtLowerBound(), dto.getCreatedAtUpperBound());
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
    public ResponseEntity<Machine> create(@Valid @RequestBody MachineNameDto dto) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_CREATE_MACHINES, email)) {
            User user = this.userService.findByEmail(email);
            Machine machine = new Machine();
            machine.setName(dto.getName());
            machine.setUser(user);
            machine.setActive(true);
            machine.setStatus(MachineStatus.STOPPED);
            machine.setRunningPeriods(new ArrayList<>());
            machine.setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            machine.setOperationInProgress(false);
            this.machineService.create(machine);
            return ResponseEntity.ok(machine);
        }
        return ResponseEntity.status(403).build();
    }

    @PutMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> startMachine(@PathVariable("id") Long machineId) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_START_MACHINES, email)) {
            if(this.machineService.isOperationInProgress(machineId)) {
                return ResponseEntity.status(403).body("Can't START. Another operation is in progress for machine with id " + machineId);
            }
            if(this.machineService.getMachineStatus(machineId).equals(MachineStatus.RUNNING)) {
                return ResponseEntity.status(403).body("Can't START. Machine with id " + machineId + " is already in status RUNNING.");
            }
            this.machineService.updateStatus(machineId);
            return ResponseEntity.status(200).body("Successful. Machine is starting. Please wait.");
        }
        return ResponseEntity.status(403).build();
    }

    @PutMapping(value = "/stop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> stopMachine(@PathVariable("id") Long machineId) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_STOP_MACHINES, email)) {
            if(this.machineService.isOperationInProgress(machineId)) {
                return ResponseEntity.status(403).body("Can't STOP. Another operation is in progress for machine with id " + machineId);
            }
            if(this.machineService.getMachineStatus(machineId).equals(MachineStatus.STOPPED)) {
                return ResponseEntity.status(403).body("Can't STOP. Machine with id " + machineId + " is already in status STOPPED.");
            }
            this.machineService.updateStatus(machineId);
            return ResponseEntity.status(200).body("Successful. Machine is stopping. Please wait.");
        }
        return ResponseEntity.status(403).build();
    }

    @PutMapping(value = "/restart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restart(@PathVariable("id") Long machineId) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_RESTART_MACHINES, email)) {
            if(this.machineService.isOperationInProgress(machineId)) {
                return ResponseEntity.status(403).body("Another operation is in progress for machine with id " + machineId);
            }
            if(this.machineService.getMachineStatus(machineId).equals(MachineStatus.STOPPED)) {
                return ResponseEntity.status(403).body("Can't restart machine with id " + machineId + " because it is in status STOPPED.");
            }
            this.machineService.restart(machineId);
            return ResponseEntity.status(200).body("Successful. Machine is restarting. Please wait.");
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping(value = "/schedule-start", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleStartMachine(@Valid @RequestBody MachineScheduleDto dto) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_START_MACHINES, email)) {
            this.machineService.scheduleStart(dto.getId(), dto.getDatetime());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping(value = "/schedule-stop", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleStopMachine(@Valid @RequestBody MachineScheduleDto dto) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_STOP_MACHINES, email)) {
            this.machineService.scheduleStop(dto.getId(), dto.getDatetime());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(403).build();
    }
    @PostMapping(value = "/schedule-restart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleRestartMachine(@Valid @RequestBody MachineScheduleDto dto) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_RESTART_MACHINES, email)) {
            this.machineService.scheduleRestart(dto.getId(), dto.getDatetime());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long machineId) {
        String email = getContext().getAuthentication().getName();
        if (this.authorisationService.isAuthorised(UserPermission.CAN_DESTROY_MACHINES, email)) {
            if(this.machineService.delete(machineId)) {
                return ResponseEntity.status(200).build();
            }
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.status(403).build();
    }
}

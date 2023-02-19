package com.raf.RafCloudBack.services;

import com.raf.RafCloudBack.models.Machine;
import com.raf.RafCloudBack.models.MachineStatus;
import com.raf.RafCloudBack.models.User;
import com.raf.RafCloudBack.repositories.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class MachineService {
    private final MachineRepository machineRepository;
    private final TaskScheduler taskScheduler;

    @Autowired
    public MachineService(MachineRepository machineRepository, TaskScheduler taskScheduler) {
        this.machineRepository = machineRepository;
        this.taskScheduler = taskScheduler;
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

    @Async
    public void updateStatus(Long id) {
        Optional<Machine> optionalMachine = this.machineRepository.findById(id);
        if (optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            machine.setOperationInProgress(true);
            this.machineRepository.save(machine);
            Random random = new Random();
            int randomNumber = random.nextInt(3) + 1; // [1,3]
            try {
                Thread.sleep(5000 + randomNumber * 1000);
                machine = machineRepository.findById(id).get();
                if (machine.getStatus().equals(MachineStatus.RUNNING)) {
                    machine.setStatus(MachineStatus.STOPPED);
                } else {
                    machine.setStatus(MachineStatus.RUNNING);
                }
                machine.setOperationInProgress(false);
                this.machineRepository.save(machine);
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
    }

    @Async
    public void restart(Long id) {
        Optional<Machine> optionalMachine = this.machineRepository.findById(id);
        if (optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            machine.setOperationInProgress(true);
            this.machineRepository.save(machine);
            Random random = new Random();
            int randomNumber = random.nextInt(3) + 1; // [1,3]
            try {
                Thread.sleep(5000 + randomNumber * 1000);
                machine = machineRepository.findById(id).get();
                machine.setStatus(MachineStatus.STOPPED);
                this.machineRepository.save(machine);

                Thread.sleep(5000 + randomNumber * 1000);
                machine = machineRepository.findById(id).get();
                machine.setStatus(MachineStatus.RUNNING);
                machine.setOperationInProgress(false);
                this.machineRepository.save(machine);

            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void scheduleStart(Long id, Timestamp datetime) {
        String cronExpression = this.formatLongToCronExpression(datetime);
        System.out.println(cronExpression);
        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        this.taskScheduler.schedule(() -> {
            Machine machine = this.machineRepository.findById(id).get();
            if(!machine.isOperationInProgress() && machine.getStatus().equals(MachineStatus.STOPPED)) {
                this.updateStatus(machine.getId());
            }
        }, cronTrigger);
    }

    public void scheduleStop(Long id, Timestamp datetime) {
        String cronExpression = this.formatLongToCronExpression(datetime);
        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        this.taskScheduler.schedule(() -> {
            Machine machine = this.machineRepository.findById(id).get();
            if(!machine.isOperationInProgress() && machine.getStatus().equals(MachineStatus.RUNNING)) {
                this.updateStatus(machine.getId());
            }
        }, cronTrigger);
    }

    public void scheduleRestart(Long id, Timestamp datetime) {
        String cronExpression = this.formatLongToCronExpression(datetime);
        CronTrigger cronTrigger = new CronTrigger(cronExpression);
        this.taskScheduler.schedule(() -> {
            Machine machine = this.machineRepository.findById(id).get();
            if(!machine.isOperationInProgress() && machine.getStatus().equals(MachineStatus.RUNNING)) {
                this.restart(machine.getId());
            }
        }, cronTrigger);
    }

    private String formatLongToCronExpression(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ss mm HH dd MM ?");
        return dateFormat.format(timestamp);
    }


    //soft delete -> setting attribute active to false
    public boolean delete(Long id) {
        Machine m = this.machineRepository.getById(id);
        if (m.getStatus() == MachineStatus.STOPPED) {
            this.machineRepository.deleteMachine(id);
            return true;
        }
        return false;
    }

    public List<Machine> getUserMachines(Long id) {
        return this.machineRepository.findAll().stream().filter(machine -> machine.getUser().getId().equals(id)).toList();
    }

    public List<Machine> getFilteredUserMachines(User user, String name, String status, Timestamp runningStarted, Timestamp runningStopped, Timestamp createdAtLowerBound, Timestamp createdAtUpperBound) {
        System.out.println(runningStarted + ", " + runningStopped);
        MachineStatus machineStatus = null;
        if (status != null && (status.equals("RUNNING") || status.equals("STOPPED"))) {
            machineStatus = MachineStatus.valueOf(status);
        }
        if (name.equals("")) {
            name = null;
        }
        return this.machineRepository.findByFilters(user, machineStatus, name, runningStarted, runningStopped, createdAtLowerBound, createdAtUpperBound);
    }

    public boolean isOperationInProgress(Long id) {
        Machine m = this.machineRepository.getById(id);
        return m.isOperationInProgress();
    }

    public MachineStatus getMachineStatus(Long id) {
        return this.machineRepository.findById(id).get().getStatus();
    }

}

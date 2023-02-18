package com.raf.RafCloudBack.bootstrap;

import com.raf.RafCloudBack.models.*;
import com.raf.RafCloudBack.repositories.MachineRepository;
import com.raf.RafCloudBack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MachineRepository machineRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, MachineRepository machineRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.machineRepository = machineRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args)  {

        System.out.println("Loading Data...");

        User user1 = new User();
        List<UserPermission> user1UserPermissionList = new ArrayList<>();
        user1UserPermissionList.add(UserPermission.CAN_READ_USERS);
        user1UserPermissionList.add(UserPermission.CAN_CREATE_USERS);
        user1UserPermissionList.add(UserPermission.CAN_UPDATE_USERS);
        user1UserPermissionList.add(UserPermission.CAN_DELETE_USERS);
        user1UserPermissionList.add(UserPermission.CAN_CREATE_MACHINES);
        user1UserPermissionList.add(UserPermission.CAN_DESTROY_MACHINES);
        user1UserPermissionList.add(UserPermission.CAN_SEARCH_MACHINES);
        user1UserPermissionList.add(UserPermission.CAN_START_MACHINES);
        user1UserPermissionList.add(UserPermission.CAN_STOP_MACHINES);
        user1UserPermissionList.add(UserPermission.CAN_RESTART_MACHINES);
        //user1UserPermissionList.add(UserPermission.CAN_SEE_ALL_MACHINES);

        user1.setId(Long.valueOf(1));
        user1.setFirstname("User1");
        user1.setLastname("Useric1");
        user1.setEmail("user1@gmail.com");
        user1.setPassword(this.passwordEncoder.encode("user1"));
        user1.setPermissionList(user1UserPermissionList);
        this.userRepository.save(user1);

        User user2 = new User();
        List<UserPermission> user2UserPermissionList = new ArrayList<>();
        user2UserPermissionList.add(UserPermission.CAN_READ_USERS);
        user2UserPermissionList.add(UserPermission.CAN_CREATE_USERS);
        user2UserPermissionList.add(UserPermission.CAN_CREATE_MACHINES);
        user2UserPermissionList.add(UserPermission.CAN_SEARCH_MACHINES);

        user2.setId(Long.valueOf(2));
        user2.setFirstname("User2");
        user2.setLastname("Useric2");
        user2.setEmail("user2@gmail.com");
        user2.setPassword(this.passwordEncoder.encode("user2"));
        user2.setPermissionList(user2UserPermissionList);
        this.userRepository.save(user2);

        User user3 = new User();
        List<UserPermission> user3UserPermissionList = new ArrayList<>();
        user3UserPermissionList.add(UserPermission.CAN_READ_USERS);
        user3UserPermissionList.add(UserPermission.CAN_CREATE_USERS);
        user3UserPermissionList.add(UserPermission.CAN_UPDATE_USERS);
        user3UserPermissionList.add(UserPermission.CAN_SEARCH_MACHINES);

        user3.setId(Long.valueOf(3));
        user3.setFirstname("User3");
        user3.setLastname("Useric3");
        user3.setEmail("user3@gmail.com");
        user3.setPassword(this.passwordEncoder.encode("user3"));
        user3.setPermissionList(user3UserPermissionList);
        this.userRepository.save(user3);

        Machine m1 = new Machine();
        m1.setActive(true);
        m1.setStatus(MachineStatus.RUNNING);
        m1.setUser(user1);
        m1.setName("Machine1");

        MachineRunningPeriod mrp1 = new MachineRunningPeriod();
        mrp1.setMachine(m1);
        mrp1.setDateStarted(new Date(Calendar.getInstance().getTimeInMillis()-(1000 * 3600 * 24 * 4)));//4 days ago
        mrp1.setDateStopped(new Date(Calendar.getInstance().getTimeInMillis()-(1000 * 3600 * 24 * 3)));//3 days ago

        MachineRunningPeriod mrp11 = new MachineRunningPeriod();
        mrp11.setMachine(m1);
        mrp11.setDateStarted(new Date(Calendar.getInstance().getTimeInMillis()-(1000 * 3600 * 24 * 2)));//2 days ago
        mrp11.setDateStopped(new Date(Calendar.getInstance().getTimeInMillis()-(1000 * 3600 * 24)));//1 days ago

        List<MachineRunningPeriod> runningPeriods1 = new ArrayList<>();
        runningPeriods1.add(mrp1);
        runningPeriods1.add(mrp11);

        m1.setRunningPeriods(runningPeriods1);
        this.machineRepository.save(m1);


        Machine m2 = new Machine();
        m2.setActive(true);
        m2.setStatus(MachineStatus.STOPPED);
        m2.setUser(user1);
        m2.setName("Machine2");
        this.machineRepository.save(m2);

        Machine m3 = new Machine();
        m3.setActive(true);
        m3.setStatus(MachineStatus.STOPPED);
        m3.setUser(user2);
        m3.setName("Machine3");
        this.machineRepository.save(m3);

        System.out.println("Data loaded!");
    }

}

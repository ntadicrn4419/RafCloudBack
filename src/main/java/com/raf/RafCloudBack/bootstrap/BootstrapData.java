package com.raf.RafCloudBack.bootstrap;

import com.raf.RafCloudBack.models.Machine;
import com.raf.RafCloudBack.models.MachineStatus;
import com.raf.RafCloudBack.models.UserPermission;
import com.raf.RafCloudBack.models.User;
import com.raf.RafCloudBack.repositories.MachineRepository;
import com.raf.RafCloudBack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        m1.setCreatedBy(Long.valueOf(1));
        this.machineRepository.save(m1);

        Machine m2 = new Machine();
        m2.setActive(true);
        m2.setStatus(MachineStatus.STOPPED);
        m2.setCreatedBy(Long.valueOf(1));
        this.machineRepository.save(m2);

        Machine m3 = new Machine();
        m3.setActive(true);
        m3.setStatus(MachineStatus.STOPPED);
        m3.setCreatedBy(Long.valueOf(2));
        this.machineRepository.save(m3);

        System.out.println("Data loaded!");
    }
}

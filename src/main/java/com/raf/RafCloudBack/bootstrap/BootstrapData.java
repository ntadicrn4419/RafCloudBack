package com.raf.RafCloudBack.bootstrap;
import com.raf.RafCloudBack.models.*;
import com.raf.RafCloudBack.repositories.MachineRepository;
import com.raf.RafCloudBack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;
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
        m1.setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis() - (1000 * 3600 * 24 * 10 + 1000*3600*3))); //10 days and 3 hours ago

        MachineRunningPeriod mrp1 = new MachineRunningPeriod();
        mrp1.setMachine(m1);
        mrp1.setDateStarted(new Timestamp(Calendar.getInstance().getTimeInMillis()-(1000 * 3600 * 24 * 4 + 1000*3600*2)));//4 days and 2 hours ago
        mrp1.setDateStopped(new Timestamp(Calendar.getInstance().getTimeInMillis()-(1000 * 3600 * 24 * 3 + 1000*3600)));//3 days and 1 hour ago

        MachineRunningPeriod mrp11 = new MachineRunningPeriod();
        mrp11.setMachine(m1);
        mrp11.setDateStarted(new Timestamp(Calendar.getInstance().getTimeInMillis()-(1000 * 3600 * 24 * 2 + 1000*3600*10)));//2 days and 10 hours ago
        mrp11.setDateStopped(new Timestamp(Calendar.getInstance().getTimeInMillis()-(1000 * 3600 * 24 + 1000*3600*5)));//1 day and 5 hours ago

        List<MachineRunningPeriod> runningPeriods1 = new ArrayList<>();
        runningPeriods1.add(mrp1);
        runningPeriods1.add(mrp11);

        m1.setRunningPeriods(runningPeriods1);
        m1.setOperationInProgress(false);
        this.machineRepository.save(m1);


        Machine m2 = new Machine();
        m2.setActive(true);
        m2.setStatus(MachineStatus.STOPPED);
        m2.setUser(user1);
        m2.setName("Machine2");
        m2.setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis() - (1000 * 3600 * 24 * 9 + 1000*3600*3 + 1000*60*27))); //9 days, 3 hours  and 27 minutes ago
        m2.setRunningPeriods(new ArrayList<>());
        m2.setOperationInProgress(false);
        this.machineRepository.save(m2);

        Machine m3 = new Machine();
        m3.setActive(true);
        m3.setStatus(MachineStatus.STOPPED);
        m3.setUser(user1);
        m3.setName("Machine3");
        m3.setRunningPeriods(new ArrayList<>());
        m3.setCreatedAt(new Timestamp(Calendar.getInstance().getTimeInMillis() - (1000 * 3600 * 24 * 7 + 1000*3600*6 + 1000*60*33))); //7 days, 6 hours  and 33 minutes ago
        m3.setOperationInProgress(false);
        this.machineRepository.save(m3);

        System.out.println("Data loaded!");
    }

}

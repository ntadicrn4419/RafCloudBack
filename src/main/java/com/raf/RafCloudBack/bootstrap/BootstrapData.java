package com.raf.RafCloudBack.bootstrap;

import com.raf.RafCloudBack.models.UserPermission;
import com.raf.RafCloudBack.models.User;
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

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
        user3.setFirstname("User3");
        user3.setLastname("Useric3");
        user3.setEmail("user3@gmail.com");
        user3.setPassword(this.passwordEncoder.encode("user3"));
        user3.setPermissionList(user3UserPermissionList);
        this.userRepository.save(user3);

        System.out.println("Data loaded!");
    }
}

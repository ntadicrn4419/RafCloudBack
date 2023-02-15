package com.raf.RafCloudBack.services;
import com.raf.RafCloudBack.models.User;
import com.raf.RafCloudBack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService  implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User myUser = this.findByEmail(email);
        if(myUser == null) {
            throw new UsernameNotFoundException("User with email "+email+" not found");
        }

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(), new ArrayList<>());
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    public User create(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public void update(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        User oldUser = this.userRepository.findByEmail(user.getEmail());
        oldUser.setFirstname(user.getFirstname());
        oldUser.setLastname(user.getLastname());
        oldUser.setPassword(user.getPassword());
        oldUser.setPermissionList(user.getPermissionList());
        this.userRepository.save(oldUser);
    }

    public void delete(String userEmail) {
        User userToDelete = this.findByEmail(userEmail);
        this.userRepository.delete(userToDelete);
    }

    public User findById(Long id) {
        return this.userRepository.getById(id);
    }
}

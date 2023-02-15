package com.raf.RafCloudBack.services;
import com.raf.RafCloudBack.models.UserPermission;
import com.raf.RafCloudBack.models.User;
import com.raf.RafCloudBack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorisationService {
    private final UserRepository userRepository;

    @Autowired
    public AuthorisationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean isAuthorised(UserPermission requiredUserPermission, String userEmail) {
        User user = this.userRepository.findByEmail(userEmail);
        return user.getPermissionList().contains(requiredUserPermission);
    }
}

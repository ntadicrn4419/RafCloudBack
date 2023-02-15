package com.raf.RafCloudBack.controllers;
import com.raf.RafCloudBack.dto.UserEmailDto;
import com.raf.RafCloudBack.models.UserPermission;
import com.raf.RafCloudBack.models.User;
import com.raf.RafCloudBack.responses.AllUsersResponse;
import com.raf.RafCloudBack.services.AuthorisationService;
import com.raf.RafCloudBack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthorisationService authorisationService;

    @Autowired
    public UserController(UserService userService, AuthorisationService authorisationService) {
        this.userService = userService;
        this.authorisationService = authorisationService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        String email = getContext().getAuthentication().getName();
        if(this.authorisationService.isAuthorised(UserPermission.CAN_READ_USERS, email)){
            List<User> users = this.userService.getAll();
            return ResponseEntity.ok(new AllUsersResponse(users));
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping( "/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String userEmail){
        String email = getContext().getAuthentication().getName();
        if(this.authorisationService.isAuthorised(UserPermission.CAN_READ_USERS, email)){
            User user = this.userService.findByEmail(userEmail);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping(value ="/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        String email = getContext().getAuthentication().getName();
        if(this.authorisationService.isAuthorised(UserPermission.CAN_CREATE_USERS, email)){
            this.userService.create(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(403).build();
    }

    @PutMapping(value ="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody User user) {
        String email = getContext().getAuthentication().getName();
        if(this.authorisationService.isAuthorised(UserPermission.CAN_UPDATE_USERS, email)){
            this.userService.update(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(403).build();
    }

    @PostMapping(value ="/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@Valid @RequestBody UserEmailDto dto) {
        String email = getContext().getAuthentication().getName();
        if(this.authorisationService.isAuthorised(UserPermission.CAN_DELETE_USERS, email)){
            this.userService.delete(dto.getEmail());
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(403).build();
    }
}

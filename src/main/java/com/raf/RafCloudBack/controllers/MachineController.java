package com.raf.RafCloudBack.controllers;

import com.raf.RafCloudBack.services.AuthorisationService;
import com.raf.RafCloudBack.services.MachineService;
import com.raf.RafCloudBack.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class MachineController {
    private final MachineService machineService;
    private final AuthorisationService authorisationService;

    @Autowired
    public MachineController(MachineService machineService, AuthorisationService authorisationService) {
        this.machineService = machineService;
        this.authorisationService = authorisationService;
    }
    //TODO: define CRUD methods
}

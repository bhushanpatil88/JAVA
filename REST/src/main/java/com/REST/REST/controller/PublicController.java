package com.REST.REST.controller;

import com.REST.REST.entity.Users;
import com.REST.REST.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/create-user")
    public void signup(@RequestBody Users user) {
        userService.saveNewUser(user);
    }
}
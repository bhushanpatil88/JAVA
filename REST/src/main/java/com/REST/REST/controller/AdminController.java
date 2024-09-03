package com.REST.REST.controller;

import com.REST.REST.entity.Users;
import com.REST.REST.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<Users> all = userService.getAllUsers();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin")
    public void createUser(@RequestBody Users user) {
        userService.saveAdmin(user);
    }
}
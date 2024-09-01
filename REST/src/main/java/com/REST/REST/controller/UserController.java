package com.REST.REST.controller;


import com.REST.REST.entity.Users;
import com.REST.REST.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String greeting = ", Welcome";
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(userService.deleteUser(authentication.getName()))return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if(userService.deleteUser(authentication.getName()))return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users userInDB = userService.findByUserName(authentication.getName());
        if(userInDB!=null) {
            userInDB.setUserName(user.getUserName());
            userInDB.setEmail(user.getEmail());
            userInDB.setRoles(!user.getRoles().isEmpty()? user.getRoles(): userInDB.getRoles());
            userService.createUser(userInDB);
            return new ResponseEntity<>(userInDB, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

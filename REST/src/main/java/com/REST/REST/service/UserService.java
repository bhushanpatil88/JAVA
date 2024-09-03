package com.REST.REST.service;


import com.REST.REST.entity.Users;
import com.REST.REST.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private RedisService redisService;

    public boolean saveNewUser(Users user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public void saveAdmin(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }



    public List<Users> getAllUsers(){
        List<Users> users = redisService.get("allUsers", List.class);

        if (users != null && !users.isEmpty()) {
            return users;
        }

        users = userRepository.findAll();
        redisService.set("allUsers", users, 600L);
        return users;
    }

    public Users createUser(Users user){
        return userRepository.save(user);
    }

    public boolean deleteUser(String username){
        userRepository.deleteByUserName(username);
        return true;
    }

    public Users findByUserName(String username){
        return userRepository.findByUserName(username);
    }
}

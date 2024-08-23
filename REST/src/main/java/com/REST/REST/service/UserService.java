package com.REST.REST.service;


import com.REST.REST.entity.Users;
import com.REST.REST.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users createUser(Users user){
        return userRepository.save(user);
    }

    public boolean deleteUser(String username){
        Users user = userRepository.findByUsername(username);
        userRepository.deleteById(user.getId());
        return true;
    }

    public Users findByUserName(String username){
            return userRepository.findByUsername(username);
    }
}

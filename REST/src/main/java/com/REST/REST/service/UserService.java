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

    public Users updateUser(Users newUser, Long id){
            Users oldUser = userRepository.findById(id).orElse(null);
            if(oldUser != null){
                oldUser.setName(!newUser.getName().equals("") ? newUser.getName() : oldUser.getName());
                oldUser.setDob( newUser.getDob()!= null ? newUser.getDob():oldUser.getDob() );
                return userRepository.save(oldUser);
            }
            return null;
    }

    public boolean deleteUser(Long id){
        userRepository.deleteById(id);
        return true;
    }
}

package com.REST.REST.service;

import com.REST.REST.entity.Users;
import com.REST.REST.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private Users user1;
    private Users user2;
    private List<Users> users;

    @BeforeEach
    void setup(){
        user1 = Users.builder()
                .userName("bhushan")
                .password("root@123")
                .build();
        user2 = Users.builder()
                .userName("Soham")
                .password("root@123")
                .build();
        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

    }

    @Test
    void testsaveNewUser(){

        when(userRepository.save(Mockito.any(Users.class))).thenReturn(user1);
        assertThat(userService.saveNewUser(user1)).isEqualTo(true);
    }



    @Test
    public void testgetAllUsers(){
        when(userRepository.findAll()).thenReturn(users);
        assertThat(userService.getAllUsers()).isEqualTo(users);
    }

    @Test
    void testDeleteUser() {
        String username = "bhushan";

        boolean result = userService.deleteUser(username);
        Mockito.verify(userRepository, Mockito.times(1)).deleteByUserName(username);
        assertThat(result).isTrue();
    }


    @Test
    void testFindByUserName() {
        String username = "bhushan";
        when(userRepository.findByUserName(username)).thenReturn(user1);
        Users foundUser = userService.findByUserName(username);
        assertThat(foundUser).isEqualTo(user1);
    }
}

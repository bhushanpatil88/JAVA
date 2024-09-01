package com.REST.REST.controller;

import com.REST.REST.entity.Users;
import com.REST.REST.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private Users user;

    @Before
    public void setup() {
        user = new Users();
        user.setUserName("testUser");
        user.setEmail("testEmail");

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), "password");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testGreeting() {
        ResponseEntity<?> response = userController.greeting();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hi testUser, Welcome", response.getBody());
    }

    @Test
    public void testDeleteUserSuccess() {
        when(userService.deleteUser(any())).thenReturn(true);
        ResponseEntity<?> response = userController.deleteUser();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteUserFailure() {
        when(userService.deleteUser(any())).thenReturn(false);
        ResponseEntity<?> response = userController.deleteUser();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUserSuccess() {
        Users updatedUser = new Users();
        updatedUser.setUserName("updatedUser");
        updatedUser.setEmail("updatedEmail");

        when(userService.findByUserName(any())).thenReturn(user);
        when(userService.createUser(any())).thenReturn(user);

        ResponseEntity<?> response = userController.updateUser(updatedUser);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedUser.getUserName(), ((Users) response.getBody()).getUserName());
        assertEquals(updatedUser.getEmail(), ((Users) response.getBody()).getEmail());
    }

    @Test
    public void testUpdateUserFailure() {
        when(userService.findByUserName(any())).thenReturn(null);
        ResponseEntity<?> response = userController.updateUser(user);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
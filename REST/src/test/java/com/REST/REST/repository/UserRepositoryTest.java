package com.REST.REST.repository;

import com.REST.REST.entity.Users;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Users user;

    @BeforeEach
    public void setup() {
        user = Users.builder()
                .userName("testUser1")
                .email("testEmail")
                .password("testPassword")
                .build();
        userRepository.save(user);
    }

    @Test
    public void testFindByUserName() {
        Users foundUser = userRepository.findByUserName(user.getUserName());
        assertNotNull(foundUser);
        assertEquals(user.getUserName(), foundUser.getUserName());
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    public void testFindByUserNameNotFound() {
        Users foundUser = userRepository.findByUserName("nonExistentUser");
        assertNull(foundUser);
    }

    @Test
    public void testDeleteByUserName() {
        userRepository.deleteByUserName(user.getUserName());
        Users deletedUser = userRepository.findByUserName(user.getUserName());
        assertNull(deletedUser);
    }

    @Test
    public void testDeleteByUserNameNotFound() {
        userRepository.deleteByUserName("nonExistentUser");
        assertEquals(0, userRepository.count());
    }

    @Test
    public void testSave() {
        Users savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId());
        assertEquals(user.getUserName(), savedUser.getUserName());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    public void testFindAll() {
        Iterable<Users> users = userRepository.findAll();
        assertNotNull(users);
        assertTrue(users.iterator().hasNext());
        assertEquals(1, ((List<Users>) users).size());
    }

    @Test
    public void testFindById() {
        Users foundUser = userRepository.findByUserName(user.getUserName());
        assertEquals(user.getUserName(), foundUser.getUserName());
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<Users> foundUser = userRepository.findById(new ObjectId());
        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testUpdateUser() {
        Users userInDB = userRepository.findByUserName(user.getUserName());
        userInDB.setEmail("updatedEmail");
        Users updatedUser = userRepository.save(userInDB);
        assertEquals("updatedEmail", updatedUser.getEmail());
    }

    @Test
    public void testDeleteAll() {
        userRepository.deleteAll();
        assertEquals(0, userRepository.count());
    }

    @Test
    public void testCount() {
        long count = userRepository.count();
        assertEquals(1, count);
    }
}

package com.REST.REST.service;

import com.REST.REST.entity.Posts;
import com.REST.REST.entity.Users;
import com.REST.REST.repository.PostsRepository;
import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UserService userService;



    @Transactional
    public void savePost(Posts post, String userName) {
        try {
            Users user = userService.findByUserName(userName);
            post.setDate(LocalDateTime.now());
            Posts saved = postsRepository.save(post);
            user.getPosts().add(saved);
            userService.createUser(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
    }

    public void savePost(Posts post) {
        postsRepository.save(post);
    }

    public List<Posts> getAll() {
        return postsRepository.findAll();
    }

    public Optional<Posts> findById(ObjectId id) {
        return postsRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            Users user = userService.findByUserName(userName);
            removed = user.getPosts().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.createUser(user);
                postsRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return removed;
    }
}

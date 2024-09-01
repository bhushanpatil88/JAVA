package com.REST.REST.controller;


import com.REST.REST.entity.Posts;
import com.REST.REST.entity.Users;
import com.REST.REST.service.PostsService;
import com.REST.REST.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostsService postsService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllPostsOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userService.findByUserName(userName);
        List<Posts> all = user.getPosts();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Posts> createPost(@RequestBody Posts post) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            postsService.savePost(post, userName);
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{myId}")
    public ResponseEntity<?> getPostById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userService.findByUserName(userName);
        List<Posts> collect = user.getPosts().stream().filter(x -> x.getId().equals(myId)).toList();
        if (!collect.isEmpty()) {
            Optional<Posts> posts = postsService.findById(myId);
            if (posts.isPresent()) {
                return new ResponseEntity<>(posts.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{myId}")
    public ResponseEntity<?> deletePostById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = postsService.deleteById(myId, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{myId}")
    public ResponseEntity<?> updatePostById(@PathVariable ObjectId myId, @RequestBody Posts post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Users user = userService.findByUserName(userName);
        List<Posts> collect = user.getPosts().stream().filter(x -> x.getId().equals(myId)).toList();
        if (!collect.isEmpty()) {
            Optional<Posts> posts = postsService.findById(myId);
            if (posts.isPresent()) {
                Posts old = posts.get();
                old.setTitle(!post.getTitle().equals("") ? post.getTitle() : old.getTitle());
                old.setContent(post.getContent() != null && !post.getContent().equals("") ? post.getContent() : old.getContent());
                postsService.savePost(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

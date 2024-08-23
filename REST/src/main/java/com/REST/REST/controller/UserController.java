package com.REST.REST.controller;


import com.REST.REST.entity.Posts;
import com.REST.REST.entity.Users;
import com.REST.REST.service.PostsService;
import com.REST.REST.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostsService postsService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<Users> l = userService.getAllUsers();
        if(!l.isEmpty()) return new ResponseEntity<>(l, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Users user){
        try{
            Users newUser = userService.createUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        if(userService.deleteUser(username))return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{username}")
    public ResponseEntity<?> updateUser(@RequestBody Users user, @PathVariable String username){
        Users userInDB = userService.findByUserName(username);
        if(userInDB!=null) {
            userInDB.setUsername(user.getUsername());
            userInDB.setDob(user.getDob() != null ? user.getDob() : userInDB.getDob());
            userService.createUser(userInDB);
            new ResponseEntity<>(userInDB, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{username}/posts")
    public ResponseEntity<?> getAllPosts(@PathVariable String username){
        Users user = userService.findByUserName(username);
        if(user!=null) {
            if(!user.getPosts().isEmpty()) return new ResponseEntity<>(user.getPosts(), HttpStatus.OK);
            else return new ResponseEntity<>(user.getPosts(), HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("{username}/posts")
    public ResponseEntity<?> createPost(@PathVariable String username,@Validated @RequestBody Posts post){
        Users user = userService.findByUserName(username);
        if(user!=null) {
            post.setUser(user);
            postsService.createPost(post);
            return new ResponseEntity<>(user.getPosts(), HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PutMapping("{username}/posts/{post_id}")
    public ResponseEntity<?> updatePost(@RequestBody Posts newPost, @PathVariable String username, @PathVariable Long post_id){
        Users user = userService.findByUserName(username);
        if(user!=null){
            Optional<Posts> oldPostOptional = user.getPosts()
                    .stream()
                    .filter(p -> p.getId().equals(post_id))
                    .findFirst();

            if (oldPostOptional.isPresent()) {
                Posts oldPost = oldPostOptional.get();
                oldPost.setDescription(newPost.getDescription() != null ?
                        newPost.getDescription() :
                        oldPost.getDescription());
                postsService.createPost(oldPost);
            }
            else return new ResponseEntity<>(user.getPosts(), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(user.getPosts(), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("{username}/posts/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable String username, @PathVariable Long post_id){
        Users user = userService.findByUserName(username);
        if(user!=null){
            List<Posts> posts = user.getPosts().stream().filter(p -> !p.getId().equals(post_id)).toList();
            user.setPosts(posts);
            if(postsService.deletePost(post_id)) return new ResponseEntity<>(user.getPosts(), HttpStatus.NO_CONTENT);
            else new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

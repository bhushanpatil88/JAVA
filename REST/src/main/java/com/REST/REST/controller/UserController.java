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

    @GetMapping("{id}/posts")
    public ResponseEntity<?> getAllPosts(@PathVariable Long id){
        Users user = userService.getUserById(id);
        if(user!=null) {
            if(!user.getPosts().isEmpty()) return new ResponseEntity<>(user.getPosts(), HttpStatus.OK);
            else return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Users user = userService.getUserById(id);
        if(user!=null) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @PostMapping("{id}/posts")
    public ResponseEntity<?> createPost(@PathVariable Long id,@Validated @RequestBody Posts post){
        Users user = userService.getUserById(id);

        if(user!=null) {
            post.setUser(user);
            postsService.createPost(post);
            return new ResponseEntity<>(user.getPosts(), HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@RequestBody Users user, @PathVariable Long id){
        Users updatedUser = userService.updateUser(user,id);
        if(updatedUser!=null)return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("{id}/posts/{post_id}")
    public ResponseEntity<?> updatePost(@RequestBody Posts newPost, @PathVariable Long id, @PathVariable Long post_id){
        Users user = userService.getUserById(id);
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

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        if(userService.deleteUser(id))return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}/posts/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @PathVariable Long post_id){
        Users user = userService.getUserById(id);
        if(user!=null){
            List<Posts> posts = user.getPosts().stream().filter(p -> !p.getId().equals(post_id)).toList();
            user.setPosts(posts);
            if(postsService.deletePost(post_id)) return new ResponseEntity<>(user.getPosts(), HttpStatus.NO_CONTENT);
            else new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

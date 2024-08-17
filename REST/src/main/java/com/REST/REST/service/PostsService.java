package com.REST.REST.service;

import com.REST.REST.entity.Posts;
import com.REST.REST.repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PostsService {

    @Autowired
    private PostsRepository postsRepository;

    public void createPost(Posts post){
        postsRepository.save(post);
    }


    public boolean deletePost(Long id){
        try{
            postsRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}

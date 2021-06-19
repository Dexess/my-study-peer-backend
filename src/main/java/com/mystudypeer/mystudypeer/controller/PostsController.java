package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.service.PostService;
import com.mystudypeer.mystudypeer.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
public class PostsController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public List<Post> getPosts(@RequestParam(required = false) int page) {

        return postService.getAllPosts(page);
    }

    @GetMapping(value = "/post")
    public Optional<Post> getPost(@RequestParam int id) throws EntityNotFoundException {
        return postService.getPost(id);
    }
}













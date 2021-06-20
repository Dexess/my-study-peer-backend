package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.domains.CreateComment;
import com.mystudypeer.mystudypeer.pojo.Comment;
import com.mystudypeer.mystudypeer.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping(value = "/comments")
    public Comment createComment(@RequestBody CreateComment createComment){
        return commentService.createComment(createComment);
    }

    @GetMapping(value = "/comments")
    public List<Comment> getComments(int postId){
        return commentService.getComments(postId);
    }
}

package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.customs.CommentCustom;
import com.mystudypeer.mystudypeer.domains.CreateComment;
import com.mystudypeer.mystudypeer.pojo.Comment;
import com.mystudypeer.mystudypeer.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping(value = "/Comment/Create")
    public CommentCustom createComment(@RequestBody CreateComment createComment){
        return commentService.createComment(createComment);
    }
}

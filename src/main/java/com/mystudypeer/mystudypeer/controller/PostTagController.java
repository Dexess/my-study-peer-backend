package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.repository.PostTagRepository;
import com.mystudypeer.mystudypeer.service.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostTagController {
    @Autowired
    PostTagService postTagService;
}

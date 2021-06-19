package com.mystudypeer.mystudypeer.controller;
import com.mystudypeer.mystudypeer.pojo.News;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public List<News> getAllPosts() {
        return newsService.getAllNews();
    }
}

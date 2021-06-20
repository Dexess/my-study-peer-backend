package com.mystudypeer.mystudypeer.service;
import com.mystudypeer.mystudypeer.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.mystudypeer.mystudypeer.pojo.News;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    public List<NewsRepository.MyNews> getAllNews() {
        return newsRepository.homePageNews();
        /*List<News> myNews = new ArrayList<News>();
        return myNews;*/
    }
}

package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.repository.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostTagService {
    @Autowired
    PostTagRepository postTagRepository;
}

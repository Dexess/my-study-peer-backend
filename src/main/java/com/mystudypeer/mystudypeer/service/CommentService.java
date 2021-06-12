package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
}

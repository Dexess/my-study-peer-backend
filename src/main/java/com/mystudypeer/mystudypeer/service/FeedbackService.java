package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
}

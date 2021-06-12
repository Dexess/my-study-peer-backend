package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
}

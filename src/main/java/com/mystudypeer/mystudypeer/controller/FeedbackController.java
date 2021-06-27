package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.domains.CreateFeedback;
import com.mystudypeer.mystudypeer.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;


    @PostMapping(value = "/users/feedback/create")
    public void addFeedback(@RequestBody CreateFeedback createFeedback){
        feedbackService.addFeedback(createFeedback);
    }
}

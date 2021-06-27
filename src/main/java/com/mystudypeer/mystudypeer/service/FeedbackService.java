package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.domains.CreateFeedback;
import com.mystudypeer.mystudypeer.domains.FeedbackId;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.Feedback;
import com.mystudypeer.mystudypeer.pojo.Users;
import com.mystudypeer.mystudypeer.repository.FeedbackRepository;
import com.mystudypeer.mystudypeer.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    UsersRepository usersRepository;

    public void addFeedback(CreateFeedback createFeedback){
        Users user = usersRepository.getUsersByIdAndToken(createFeedback.getGivenBy(),createFeedback.getToken());

        if(user == null){
            throw new EntityNotFoundException("You are not authorized to give feedback!");
        }

        Feedback checkFeedback =
                feedbackRepository.findByFeedbackId_GivenToAndAndFeedbackId_GivenByAndFeedbackId_ForPost(createFeedback.getGivenTo(),createFeedback.getGivenBy(),createFeedback.getForPost());

        if(checkFeedback != null){
            throw new EntityNotFoundException("You have already gave feedback for this post!");
        }

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        FeedbackId feedbackId = new FeedbackId();
        feedbackId.setForPost(createFeedback.getForPost()); feedbackId.setGivenBy(createFeedback.getGivenBy()); feedbackId.setGivenTo(createFeedback.getGivenTo());

        Feedback feedback = new Feedback();
        feedback.setFeedbackId(feedbackId);
        feedback.setFeedbackPoints(createFeedback.getFeedbackPoints());
        feedback.setFeedbackText(createFeedback.getFeedbackText());
        feedback.setFeedbackTitle(createFeedback.getFeedbackTitle());
        feedback.setFeedbackDate(date);

        feedback = feedbackRepository.save(feedback);

    }
}

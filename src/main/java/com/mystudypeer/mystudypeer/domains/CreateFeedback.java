package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

@Data
public class CreateFeedback {
    private String feedbackTitle;
    private String feedbackText;
    private int feedbackPoints;
    private int givenTo;
    private int givenBy;
    private int forPost;
    private String token;
}

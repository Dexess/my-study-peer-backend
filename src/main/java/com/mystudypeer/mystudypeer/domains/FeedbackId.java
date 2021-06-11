package com.mystudypeer.mystudypeer.domains;

import java.io.Serializable;

public class FeedbackId implements Serializable {
    private int forPost;
    private String givenTo;
    private String givenBy;

    public FeedbackId(){}

    public FeedbackId(String givenTo, String givenBy, int forPost){
        this.forPost = forPost;
        this.givenBy = givenBy;
        this.givenTo = givenTo;
    }
}

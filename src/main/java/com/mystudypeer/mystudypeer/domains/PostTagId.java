package com.mystudypeer.mystudypeer.domains;

import java.io.Serializable;

public class PostTagId implements Serializable {
    private int postId;
    private String tag;

    public PostTagId(){}

    public PostTagId(String myTag, int myPostId){
        postId = myPostId;
        tag = myTag;
    }
}
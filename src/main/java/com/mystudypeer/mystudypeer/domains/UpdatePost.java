package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

@Data
public class UpdatePost {

    private int userId;
    private String token;
    private int postId;
    private String title;
    private String description;
    private String course;
    private Boolean postEnabled;
}

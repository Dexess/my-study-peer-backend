package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

@Data
public class CreateComment {

    String commentText;
    String token;
    int postId;
    int userId;

}

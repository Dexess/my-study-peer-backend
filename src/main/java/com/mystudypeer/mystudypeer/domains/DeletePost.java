package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

@Data
public class DeletePost {
    private int postId;
    private int userId;
    private String token;
}

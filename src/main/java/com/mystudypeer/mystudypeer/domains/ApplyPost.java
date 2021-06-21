package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

@Data
public class ApplyPost {
    private int userId;
    private String token;
    private int postId;
}

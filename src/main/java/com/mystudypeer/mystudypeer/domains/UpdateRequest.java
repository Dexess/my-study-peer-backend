package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

@Data
public class UpdateRequest {
    private int userId;
    private String token;
    private int postId;
    private int applierUserId;
    private String status;
}

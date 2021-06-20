package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

import java.util.List;

@Data
public class CreatePost {
    String token;
    int id;
    String title;
    String course;
    String description;
    List<String> postTags;
}

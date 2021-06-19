package com.mystudypeer.mystudypeer.customs;

import com.mystudypeer.mystudypeer.pojo.Comment;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.pojo.PostTag;
import com.mystudypeer.mystudypeer.repository.RequestRepository;
import lombok.Data;

import java.util.List;

@Data
public class PostCustom {

    Post post;
    List<RequestRepository.Teammates> teammates;
    List<Comment> comments;
    List<PostTag> postTags;
}

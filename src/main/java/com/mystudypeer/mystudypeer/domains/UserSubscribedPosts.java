package com.mystudypeer.mystudypeer.domains;


import com.mystudypeer.mystudypeer.pojo.Post;
import lombok.Data;

import java.util.List;

@Data
public class UserSubscribedPosts {

    List<Post> ownedPost;
    List<Post> memberPost;
}

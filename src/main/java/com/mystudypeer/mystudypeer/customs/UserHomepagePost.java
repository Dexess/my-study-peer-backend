package com.mystudypeer.mystudypeer.customs;


import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.pojo.Request;
import com.mystudypeer.mystudypeer.repository.RequestRepository;
import lombok.Data;

import java.util.List;

@Data
public class UserHomepagePost {

    String name;
    String surname;
    List<Post> ownedPost;
    List<Post> memberPost;
    List<RequestRepository.OwnedPostRequests> postRequest;
}

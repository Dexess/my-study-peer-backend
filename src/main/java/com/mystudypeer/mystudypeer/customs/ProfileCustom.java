package com.mystudypeer.mystudypeer.customs;

import com.mystudypeer.mystudypeer.domains.ProfileUser;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.repository.FeedbackRepository;
import lombok.Data;

import java.util.List;


@Data
public class ProfileCustom {

    private ProfileUser user;
    private Float rating;
    private List<Post> posts;
    private List<FeedbackRepository.Feedbacks> feedbacks;
}

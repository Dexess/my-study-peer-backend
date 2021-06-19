package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.customs.PostCustom;
import com.mystudypeer.mystudypeer.pojo.Comment;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.pojo.PostTag;
import com.mystudypeer.mystudypeer.repository.CommentRepository;
import com.mystudypeer.mystudypeer.repository.PostRepository;
import com.mystudypeer.mystudypeer.repository.PostTagRepository;
import com.mystudypeer.mystudypeer.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    PostTagRepository postTagRepository;

    public List<Post> getAllPosts(int page) {
        // 10 Per page
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate")).subList((10 * (page - 1)), 3+(10*(page-1)));


    }

    public PostCustom getPost(int id) {

        Post post = postRepository.findByPostId(id);
        List<Comment> comments = commentRepository.findByPostId(id);
        List<PostTag> postTags = postTagRepository.findByPostTagId_PostId(id);
        List<RequestRepository.Teammates> team = requestRepository.findTeammatesForPost(id, "accepted");
        PostCustom postCustom = new PostCustom();
        postCustom.setPost(post);
        postCustom.setPostTags(postTags);
        postCustom.setTeammates(team);
        postCustom.setComments(comments);

        return  postCustom;
    }
}

package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.customs.PostCustom;
import com.mystudypeer.mystudypeer.domains.CreatePost;
import com.mystudypeer.mystudypeer.domains.PostTagId;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.Comment;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.pojo.PostTag;
import com.mystudypeer.mystudypeer.pojo.Users;
import com.mystudypeer.mystudypeer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private PostTagRepository postTagRepository;
    @Autowired
    private UsersRepository usersRepository;

    public List<PostRepository.GetPosts> getAllPosts(int page) {
        // 10 Per page
        List<PostRepository.GetPosts> posts = postRepository.findPostsForPage((page-1)*10);

        return  posts;

    }

    public PostCustom getPost(int id) {

        Post post = postRepository.findByPostId(id);
        Users user = usersRepository.findUsersByEmail(post.getEmail());
        List<Comment> comments = commentRepository.findByPostId(id);
        List<PostTag> postTags = postTagRepository.findByPostTagId_PostId(id);
        List<RequestRepository.Teammates> team = requestRepository.findTeammatesForPost(id, "accepted");
        PostCustom postCustom = new PostCustom();
        postCustom.setPostAuthorId(user.getId());
        postCustom.setPost(post);
        postCustom.setPostTags(postTags);
        postCustom.setTeammates(team);
        postCustom.setComments(comments);

        return  postCustom;
    }

    public int createPost(CreatePost createPost){
        Post post = new Post();
        Users user = usersRepository.getUsersByIdAndToken(createPost.getId(),createPost.getToken());

        if( createPost.getTitle() == null || createPost.getTitle().length() < 5 ){
            throw new EntityNotFoundException("Title can't be empty");
        }
        post.setTitle(createPost.getTitle());
        post.setDescription(createPost.getDescription());
        post.setCourse(createPost.getCourse());
        post.setPostEnabled(Boolean.TRUE);
        post.setEmail(user.getEmail());
        post.setAuthorName(user.getName());
        post.setAuthorSurname(user.getSurname());
        post.setAuthorClass(user.getUserClass());
        post.setUniversityProgram(user.getUniversityProgram());

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        post.setCreationDate(date);



        post = postRepository.save(post);
        if(createPost.getPostTags() != null) {
            for (int i = 0; i < createPost.getPostTags().size(); i++) {
                PostTag postTag = new PostTag();
                PostTagId postTagId = new PostTagId();
                postTagId.setPostId(post.getPostId());
                postTagId.setTag(createPost.getPostTags().get(i));
                postTag.setPostTagId(postTagId);
                postTag = postTagRepository.save(postTag);
            }
        }
        return post.getPostId();
    }
}

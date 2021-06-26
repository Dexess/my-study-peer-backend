package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.customs.PostCustom;
import com.mystudypeer.mystudypeer.domains.CreatePost;
import com.mystudypeer.mystudypeer.domains.DeletePost;
import com.mystudypeer.mystudypeer.domains.PostTagId;
import com.mystudypeer.mystudypeer.domains.UpdatePost;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.*;
import com.mystudypeer.mystudypeer.repository.*;
import org.apache.catalina.User;
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
        List<PostRepository.GetPosts> posts = postRepository.findPostsForPage((page-1)*5);

        return  posts;

    }

    public PostCustom getPost(int id) {

        Post post = postRepository.findByPostId(id);
        if(post == null){
            throw new EntityNotFoundException("Post doesn't exist");
        }

        List<PostTag> postTags = postTagRepository.findByPostTagId_PostId(id);
        List<RequestRepository.Teammates> team = requestRepository.findTeammatesForPost(id, "accepted");
        PostCustom postCustom = new PostCustom();
        postCustom.setPost(post);
        postCustom.setPostTags(postTags);
        postCustom.setTeammates(team);

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
        post.setUserId(user.getId());
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

    public Post updatePost(UpdatePost updatePost){
        Post post = postRepository.findByPostId(updatePost.getPostId());

        if(post == null || post.getUserId() != updatePost.getUserId()){
            throw new EntityNotFoundException("You are not the owner of this Post!");
        }

        post.setPostEnabled(updatePost.getPostEnabled());
        post.setTitle(updatePost.getTitle());
        post.setCourse(updatePost.getCourse());
        post.setDescription(updatePost.getDescription());

        post = postRepository.save(post);
        return  post;
    }

    public String deletePost(DeletePost deletePost){
        Post post = postRepository.findByPostId(deletePost.getPostId());
        Users user = usersRepository.findUsersById(deletePost.getUserId());
        List <Comment> comment = commentRepository.findByPostId(post.getPostId());
        List <RequestRepository.Teammates> teammates = requestRepository.findTeammatesForPost(post.getPostId(), "Accepted");
        if(user == null){
            throw new EntityNotFoundException("User doesn't exist");
        }
        if(post == null){
            throw new EntityNotFoundException("Post doesn't exists!");
        }
        else if( post.getUserId() != deletePost.getUserId()){
            throw new EntityNotFoundException("You are not the owner of the this post!");
        }
        else if(comment.size() < 1 || teammates.size() < 1){
            throw new EntityNotFoundException("You cannot delete a post with a comment or team member!");
        }

        List<PostTag> postTags = postTagRepository.findByPostTagId_PostId(post.getPostId());
        for(PostTag postTag : postTags){
            postTagRepository.delete(postTag);
        }

        postRepository.delete(post);
        return "Post deleted!";
    }
}

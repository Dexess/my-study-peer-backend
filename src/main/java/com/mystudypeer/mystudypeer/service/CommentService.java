package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.domains.CreateComment;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.Comment;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.pojo.Request;
import com.mystudypeer.mystudypeer.pojo.Users;
import com.mystudypeer.mystudypeer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    PostRepository postRepository;

    public List<Comment> getComments(int postId){
        return commentRepository.findByPostId(postId);
    }

    public Comment createComment(CreateComment createComment){

        Users user = usersRepository.getUsersByIdAndToken(createComment.getUserId(), createComment.getToken());
        Request request = requestRepository.findByRequestId_PostIdAndRequestId_ApplierUserIdAndStatus(createComment.getPostId(),createComment.getUserId(),"accepted");


        if(request == null){
            Post post = postRepository.findByPostId(createComment.getPostId());
            if(user == null || post.getUserId() != user.getId()){
                throw new EntityNotFoundException("You don't have rights to comment this post");
            }
        }
        Comment comment = new Comment();

        comment.setCommentText(createComment.getCommentText());
        comment.setCommenterName(user.getName());
        comment.setCommenterSurname(user.getSurname());
        comment.setCommenterUserId(user.getId());
        comment.setPostId(createComment.getPostId());

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        comment.setCommentDate(date);
        comment = commentRepository.save(comment);

        return comment;
    }
}

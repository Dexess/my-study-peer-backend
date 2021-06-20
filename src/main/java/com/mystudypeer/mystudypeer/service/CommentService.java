package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.customs.CommentCustom;
import com.mystudypeer.mystudypeer.domains.CreateComment;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.Comment;
import com.mystudypeer.mystudypeer.pojo.Request;
import com.mystudypeer.mystudypeer.pojo.Users;
import com.mystudypeer.mystudypeer.repository.CommentRepository;
import com.mystudypeer.mystudypeer.repository.RequestRepository;
import com.mystudypeer.mystudypeer.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RequestRepository requestRepository;

    public CommentCustom createComment(CreateComment createComment){

        Users user = usersRepository.getUsersByIdAndToken(createComment.getUserId(), createComment.getToken());
        Request request = requestRepository.findByRequestId_PostIdAndRequestId_ApplierEmailAndStatus(createComment.getPostId(),user.getEmail(),"accepted");

        if(request == null){
            throw new EntityNotFoundException("You don't have rights to comment this post");
        }

        Comment comment = new Comment();

        comment.setCommentText(createComment.getCommentText());
        comment.setCommenterName(user.getName());
        comment.setCommenterSurname(user.getSurname());
        comment.setCommenterEmail(user.getEmail());
        comment.setPostId(createComment.getPostId());

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        comment.setCommentDate(date);
        comment = commentRepository.save(comment);

        CommentCustom commentCustom = new CommentCustom();
        commentCustom.setCommenterId(user.getId());
        commentCustom.setCommentText(comment.getCommentText());
        commentCustom.setCommenterName(comment.getCommenterName());
        commentCustom.setCommenterSurname(comment.getCommenterSurname());
        commentCustom.setCommentDate(comment.getCommentDate());

        return commentCustom;
    }
}

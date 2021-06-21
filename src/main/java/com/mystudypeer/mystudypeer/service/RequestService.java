package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.domains.ApplyPost;
import com.mystudypeer.mystudypeer.domains.RequestId;
import com.mystudypeer.mystudypeer.domains.UpdateRequest;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.pojo.Request;
import com.mystudypeer.mystudypeer.pojo.Users;
import com.mystudypeer.mystudypeer.repository.PostRepository;
import com.mystudypeer.mystudypeer.repository.RequestRepository;
import com.mystudypeer.mystudypeer.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PostRepository postRepository;

    public List<RequestRepository.OwnedPostRequests> ownerPostRequests(Users user) {
        Users existUser = usersRepository.getUsersByIdAndToken(user.getId(), user.getToken());
        if (existUser == null) {
            throw new EntityNotFoundException("You can't send this request. User doesn't exist!");
        }

        return requestRepository.ownedPostRequests(existUser.getId());

    }

    public List<RequestRepository.SubsPostRequests> subsPostRequests(Users user) {
        Users existUser = usersRepository.getUsersByIdAndToken(user.getId(), user.getToken());
        if (existUser == null) {
            throw new EntityNotFoundException("You can't send this request. User doesn't exist!");
        }

        return requestRepository.subsPostRequests(existUser.getId());
    }

    public Request updateRequest(UpdateRequest updateRequest){
        Users user = usersRepository.getUsersByIdAndToken(updateRequest.getUserId(),updateRequest.getToken());
        if (user == null) {
            throw new EntityNotFoundException("You can't send this request. User doesn't exist!");
        }
        Post post = postRepository.findByPostId(updateRequest.getPostId());
        if(post == null || post.getUserId() != updateRequest.getUserId()){
            throw new EntityNotFoundException("You can't send this request. You are not the owner of the post!");
        }
        RequestId requestId = new RequestId();
        requestId.setPostId(updateRequest.getPostId());
        requestId.setApplierUserId(updateRequest.getApplierUserId());

        Request request =  requestRepository.findByRequestId_PostIdAndRequestId_ApplierUserId(updateRequest.getPostId(),updateRequest.getApplierUserId());
        if (request == null) {
            throw new EntityNotFoundException("Request doesn't exist!");
        }

        request.setStatus(updateRequest.getStatus());

        request = requestRepository.save(request);

        return request;

    }

    public Request applyPost(ApplyPost applyPost){
        Users user = usersRepository.getUsersByIdAndToken(applyPost.getUserId(),applyPost.getToken());

        if (user == null) {
            throw new EntityNotFoundException("You can't send this request. User doesn't exist!");
        }
        Post post = postRepository.findByPostIdAndPostEnabled(applyPost.getPostId(), Boolean.TRUE);
        if (post == null) {
            throw new EntityNotFoundException("You can't send this request. Post doesn't exist or not enabled!");
        }
        if (post.getUserId() == applyPost.getUserId()) {
            throw new EntityNotFoundException("You can't apply your own post!");
        }
        Request tempRequest = requestRepository.findByRequestId_PostIdAndRequestId_ApplierUserId(applyPost.getPostId(),applyPost.getUserId());
        if (tempRequest != null) {
            throw new EntityNotFoundException("You can't apply more than once!");
        }

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);

        RequestId requestId  = new RequestId();
        requestId.setPostId(applyPost.getPostId());
        requestId.setApplierUserId(applyPost.getUserId());

        Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestDate(date);
        request.setStatus("ongoing");

        request = requestRepository.save(request);
        return request;

    }
}


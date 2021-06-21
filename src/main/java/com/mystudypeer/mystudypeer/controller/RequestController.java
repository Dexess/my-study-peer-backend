package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.domains.ApplyPost;
import com.mystudypeer.mystudypeer.domains.UpdateRequest;
import com.mystudypeer.mystudypeer.pojo.Request;
import com.mystudypeer.mystudypeer.pojo.Users;
import com.mystudypeer.mystudypeer.repository.RequestRepository;
import com.mystudypeer.mystudypeer.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping(value =  "/request/owned")
    public List<RequestRepository.OwnedPostRequests> ownerPostRequests(@RequestBody Users user){
        return requestService.ownerPostRequests(user);
    }

    @PostMapping(value = "/request/subs")
    public List<RequestRepository.SubsPostRequests> subsPostRequests(@RequestBody Users user){
        return requestService.subsPostRequests(user);
    }

    @PutMapping(value = "/request/update")
    public Request updateRequest(@RequestBody UpdateRequest updateRequest){
        return  requestService.updateRequest(updateRequest);
    }

    @PostMapping(value = "/request/apply")
    public Request applyPost(@RequestBody ApplyPost applyPost){
        return requestService.applyPost(applyPost);
    }
}

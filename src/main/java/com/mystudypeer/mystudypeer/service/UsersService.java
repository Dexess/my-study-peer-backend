package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.customs.ProfileCustom;
import com.mystudypeer.mystudypeer.domains.CheckCanFeedback;
import com.mystudypeer.mystudypeer.domains.ProfileUser;
import com.mystudypeer.mystudypeer.customs.UserHomepagePost;
import com.mystudypeer.mystudypeer.domains.Registration;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.*;
import com.mystudypeer.mystudypeer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.html.parser.Entity;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    UniversityProgramRepository universityProgramRepository;

    public Optional<Users> getUserForLogin(String email, String password ) {
        String encodedPass = this.hashSHA512(password);
        Optional<Users> users = usersRepository.getUsersByEmailAndPassword(email,encodedPass);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Login info couldn't found");
        }
        users.ifPresent(user -> {user.setPassword("0");});
        return users;
    }

    public Users registerUser(Registration registration){

        Users checkUser = usersRepository.findUsersByEmail(registration.getEmail());
        if(checkUser != null){
            throw new EntityNotFoundException("Email is already registered!");
        }
        Users user = new Users();
        user.setEmail(registration.getEmail()); user.setPassword(this.hashSHA512(registration.getPassword()));
        user.setName(registration.getName()); user.setSurname(registration.getSurname());
        user.setCity(registration.getCity()); user.setUserClass(registration.getUserClass());
        UniversityProgram universityProgram = universityProgramRepository.findByUniversityNameAndProgramName(registration.getUniName(),registration.getProgName());
        if(universityProgram == null){
            throw new EntityNotFoundException("University and program should be selected!");
        }
        user.setTelno(registration.getTelno()); user.setUniversityProgram(universityProgram);
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        user.setRegisterDate(date); user.setToken("6");
        user = usersRepository.save(user);
        return user;
    }

    public UserHomepagePost userHomepagePosts(Users user){

        Users foundUser = usersRepository.getUsersByIdAndToken(user.getId(),user.getToken());
        List <Post>  ownedPosts = postRepository.findAllByUserIdOrderByCreationDateDesc(foundUser.getId());
        List <Request> request  = requestRepository.findTop5ByRequestIdApplierUserIdAndStatusOrderByRequestDateDesc(foundUser.getId(),"accepted");
        List <RequestRepository.OwnedPostRequests> ownedPostRequests = requestRepository.ownedPostRequests(user.getId());
        List<Post> memberPosts = new ArrayList<Post>();
        for(int i  = 0; i < request.size(); i++){
            Post post = postRepository.findByPostId(request.get(i).getRequestId().getPostId());
            memberPosts.add(post);
        }

        UserHomepagePost userHomepagePost = new UserHomepagePost();
        userHomepagePost.setName(foundUser.getName());
        userHomepagePost.setSurname(foundUser.getSurname());
        userHomepagePost.setOwnedPost(ownedPosts);
        userHomepagePost.setMemberPost(memberPosts);
        userHomepagePost.setPostRequest(ownedPostRequests);
        return userHomepagePost;
    }

    public ProfileCustom getProfile(int id){

        Users user = usersRepository.findUsersById(id);
        if(user == null){
            throw new EntityNotFoundException("User doesn't exist");
        }
        List<Post> posts = postRepository.findByUserIdOrderByCreationDateDesc(user.getId());
        Float rating = feedbackRepository.averageFeedbackPoint(user.getId());
        List<FeedbackRepository.Feedbacks> feedbacks = feedbackRepository.findFeedbackForProfile(user.getId());

        ProfileUser userProfile = new ProfileUser();
        userProfile.setName(user.getName()); userProfile.setSurname(user.getSurname());
        userProfile.setRegisterDate(user.getRegisterDate()); userProfile.setUserClass(user.getUserClass());
        userProfile.setUniversityName(user.getUniversityProgram().getUniversityName()); userProfile.setProgramName(user.getUniversityProgram().getProgramName());
        userProfile.setCity(user.getCity());

        ProfileCustom profile = new ProfileCustom();
        profile.setUser(userProfile);
        profile.setPosts(posts);
        profile.setFeedbacks(feedbacks);
        profile.setRating(rating);

        return profile;
    }

    public List<FeedbackRepository.GiveFeedbackOn> canGiveFeedback(CheckCanFeedback checkCanFeedback){

        if(checkCanFeedback.getId() == checkCanFeedback.getGivenBy()){
            throw new EntityNotFoundException("Own profile!");
        }

        List<Post> posts = postRepository.findAllByUserIdOrderByCreationDateDesc(checkCanFeedback.getId());
        List <Request> request  = requestRepository.findAllByRequestIdApplierUserIdAndStatus(checkCanFeedback.getId(),"accepted");

        List<Post> memberPost = new ArrayList<Post>();
        for(int i  = 0; i < request.size(); i++){
            Post post = postRepository.findByPostId(request.get(i).getRequestId().getPostId());
            memberPost.add(post);
        }
        List <FeedbackRepository.GiveFeedbackOn> giveFeedbackOn = new ArrayList<FeedbackRepository.GiveFeedbackOn>();
        for(Post post : posts){
            FeedbackRepository.GiveFeedbackOn giveFeedback = feedbackRepository.canGiveFeedback(post.getPostId(),checkCanFeedback.getGivenBy());
            if(giveFeedback != null)
                giveFeedbackOn.add(giveFeedback);
        }
        for(Post post : memberPost){
            FeedbackRepository.GiveFeedbackOn giveFeedback = feedbackRepository.canGiveFeedbackToMember(post.getPostId(),checkCanFeedback.getGivenBy(),checkCanFeedback.getId());
            if(giveFeedback != null)
                giveFeedbackOn.add(giveFeedback);
            else{
                FeedbackRepository.GiveFeedbackOn giveFeedback2 = feedbackRepository.canGiveFeedback(post.getPostId(),checkCanFeedback.getGivenBy());
                if(giveFeedback2 != null)
                    giveFeedbackOn.add(giveFeedback2);
            }

        }
        List<Integer> index = new ArrayList<Integer>();
        for(int i = 0; i < giveFeedbackOn.size(); i++){
            Feedback checkFeedback = feedbackRepository.findByFeedbackId_GivenToAndAndFeedbackId_GivenByAndFeedbackId_ForPost(checkCanFeedback.getId(),checkCanFeedback.getGivenBy(),giveFeedbackOn.get(i).getPostId());
            if(checkFeedback != null){
                index.add(i);
            }
        }

        for(int i = 0; i < index.size(); i++){
            giveFeedbackOn.remove(i);
        }
        return giveFeedbackOn;
    }

    public String updateProfile(Users user){
        Users checkUser = usersRepository.getUsersByIdAndToken(user.getId(), user.getToken()) ;
        if(checkUser == null){
            throw new EntityNotFoundException("User doesn't exist");
        }
        checkUser.setUserClass(user.getUserClass());
        checkUser.setCity(user.getCity());
        usersRepository.save(checkUser);
        return "Updated!";
    }

    public String hashSHA512(String password){
        String encodedPass = new String("");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] byteOfText = password.getBytes(StandardCharsets.UTF_8);
            byte[] hashedByteOfText = md.digest(byteOfText);
            StringBuilder hexString = new StringBuilder();
            for(int i: hashedByteOfText){
                hexString.append(String.format("%02x", 0XFF & i));
            }

            encodedPass = hexString.toString();
            return encodedPass;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodedPass;
    }


}

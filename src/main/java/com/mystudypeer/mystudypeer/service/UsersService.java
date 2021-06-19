package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.customs.ProfileCustom;
import com.mystudypeer.mystudypeer.domains.ProfileUser;
import com.mystudypeer.mystudypeer.domains.UserSubscribedPosts;
import com.mystudypeer.mystudypeer.domains.Registration;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.exceptions.UserNotFoundException;
import com.mystudypeer.mystudypeer.pojo.*;
import com.mystudypeer.mystudypeer.repository.FeedbackRepository;
import com.mystudypeer.mystudypeer.repository.PostRepository;
import com.mystudypeer.mystudypeer.repository.RequestRepository;
import com.mystudypeer.mystudypeer.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Users> getUserForLogin(String email, String password ) {
        String encodedPass = this.hashSHA512(password);
        Optional<Users> users = usersRepository.getUsersByEmailAndPassword(email,encodedPass);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Login info couldn't found");
        }
        users.ifPresent(user -> {user.setPassword("0");});
        return users;
    }

    public Optional<Users> registerUser(Registration registration){

        Users user = new Users();
        user.setEmail(registration.getEmail()); user.setPassword(this.hashSHA512(registration.getPassword()));
        user.setName(registration.getName()); user.setSurname(registration.getSurname());
        user.setCity(registration.getCity()); user.setUserClass(registration.getUserClass());
        user.setTelno(registration.getTelno()); user.getUniversityProgram().setProgramId(registration.getProgramId());
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        user.setRegisterDate(date); user.setToken("6");
        usersRepository.save(user);
        Optional<Users> users = usersRepository.getUsersByEmailEquals(user.getEmail());
        return users;
    }

    public UserSubscribedPosts userHomepagePosts(Users user){
        Users users = usersRepository.getUsersByIdAndToken(user.getId(),user.getToken());

        user.setEmail(users.getEmail());
        List <Post>  posts = postRepository.findTop5ByEmailOrderByCreationDateDesc(user.getEmail());

        List <Request> request  = requestRepository.findTop5ByRequestIdApplierEmailAndStatus(user.getEmail(),RequestStatus.accepted);
        List<Post> secondPosts = new ArrayList<Post>();
        for(int i  = 0; i < request.size(); i++){
            Post post = postRepository.findByPostId(request.get(i).getRequestId().getPostId());
            secondPosts.add(post);
        }
        Post myPost = new Post();

        UserSubscribedPosts doubleList = new UserSubscribedPosts();
        doubleList.setOwnedPost(posts);
        doubleList.setMemberPost(secondPosts);
        return doubleList;
    }

    public ProfileCustom getProfile(int userId) throws UserNotFoundException {
        Users user = usersRepository.findUsersById(userId);


        List<Post> posts = postRepository.findByEmailOrderByCreationDateDesc(user.getEmail());
        Float rating = feedbackRepository.averageFeedbackPoint(user.getEmail());
        List<FeedbackRepository.Feedbacks> feedbacks = feedbackRepository.findFeedbackForProfile(user.getEmail());


        ProfileUser userProfile = new ProfileUser();
        userProfile.setName(user.getName()); userProfile.setSurname(user.getSurname());
        userProfile.setRegisterDate(user.getRegisterDate()); userProfile.setUserClass(user.getUserClass());
        userProfile.setUniversityName(user.getUniversityProgram().getUniversityName()); userProfile.setProgramName(user.getUniversityProgram().getProgramName());
        userProfile.setCity(user.getCity());

        ProfileCustom profile = new ProfileCustom();
        profile.setProfileUser(userProfile);
        profile.setPosts(posts);
        profile.setFeedbacks(feedbacks);
        profile.setRating(rating);

        return profile;
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

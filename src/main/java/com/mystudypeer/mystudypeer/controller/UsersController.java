package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.customs.ProfileCustom;
import com.mystudypeer.mystudypeer.customs.UserHomepagePost;
import com.mystudypeer.mystudypeer.domains.Registration;
import com.mystudypeer.mystudypeer.exceptions.UserNotFoundException;
import com.mystudypeer.mystudypeer.pojo.Users;
import com.mystudypeer.mystudypeer.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping(value = "/users/login")
    public Optional<Users> login(@RequestBody Registration registration) throws UserNotFoundException {
        return usersService.getUserForLogin(registration.getEmail(), registration.getPassword());
    }

    @PostMapping(value = "/users/register")
    public Users register(@RequestBody Registration registration){
        return usersService.registerUser(registration);
    }

    @PostMapping(value = "/users/posts")
    public UserHomepagePost userHomepagePosts(@RequestBody Users user){
        return usersService.userHomepagePosts(user);
    }

    @GetMapping(value = "/users/profile")
    public ProfileCustom profile(@RequestParam int id) {
        return usersService.getProfile(id);
    }
}

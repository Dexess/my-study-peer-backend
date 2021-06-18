package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.domains.Registration;
import com.mystudypeer.mystudypeer.pojo.Users;
import com.mystudypeer.mystudypeer.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping(value = "/api/users/login")
    @ResponseBody
    public Optional<Users> login(@RequestParam String email, String password) throws EntityNotFoundException {
        return usersService.getUserForLogin(email, password);
    }

    @PostMapping(value = "/api/users/register")
    public Optional<Users> register(@RequestBody Registration registration){


        return usersService.registerUser(registration);
    }
}

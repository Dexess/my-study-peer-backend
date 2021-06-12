package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;
}

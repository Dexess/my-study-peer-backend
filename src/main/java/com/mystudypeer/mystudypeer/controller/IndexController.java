package com.mystudypeer.mystudypeer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping(value = "/")
    public String connected(){
        return "Service is running.";
    }
}

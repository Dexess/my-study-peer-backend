package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.service.UniversityProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniversityProgramController {
    @Autowired
    UniversityProgramService universityProgramService;
}

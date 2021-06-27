package com.mystudypeer.mystudypeer.controller;

import com.mystudypeer.mystudypeer.pojo.UniversityProgram;
import com.mystudypeer.mystudypeer.service.UniversityProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UniversityProgramController {
    @Autowired
    UniversityProgramService universityProgramService;

    @GetMapping("/university")
    public List<UniversityProgram> getPosts() {
        return universityProgramService.getUniversity();
    }

    @PostMapping("/university/add")
    public void addUniversity(@RequestBody UniversityProgram universityProgram){
        universityProgramService.addUniversity(universityProgram);
    }

}

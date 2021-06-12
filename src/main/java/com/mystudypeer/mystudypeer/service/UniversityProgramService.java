package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.repository.UniversityProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityProgramService {
    @Autowired
    UniversityProgramRepository universityProgramRepository;
}

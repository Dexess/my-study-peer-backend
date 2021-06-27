package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.controller.UniversityProgramController;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.UniversityProgram;
import com.mystudypeer.mystudypeer.repository.UniversityProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityProgramService {
    @Autowired
    UniversityProgramRepository universityProgramRepository;

    public List<UniversityProgram> getUniversity() {
        List<UniversityProgram> universityProgram = universityProgramRepository.findAll();
        return universityProgram;
    }

    public void addUniversity(UniversityProgram universityProgram){
        UniversityProgram checkUniProg = universityProgramRepository.findByUniversityNameAndProgramName(universityProgram.getUniversityName(),universityProgram.getProgramName());
        if(checkUniProg != null){
            throw new EntityNotFoundException("University and Program is already exist!");
        }
        universityProgram = universityProgramRepository.save(universityProgram);
    }
}

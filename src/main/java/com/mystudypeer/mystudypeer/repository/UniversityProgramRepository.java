package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.pojo.UniversityProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityProgramRepository extends JpaRepository<UniversityProgram, Integer> {

    UniversityProgram findByUniversityNameAndProgramName(String uniName, String programName);
}

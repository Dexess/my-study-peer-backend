package com.mystudypeer.mystudypeer.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "UniversityProgram")
public class UniversityProgram {
    @Id
    @Column(name="programId")
    private int programId;

    @Column(name="universityName")
    private String universityName;

    @Column(name="programName")
    private String programName;
}

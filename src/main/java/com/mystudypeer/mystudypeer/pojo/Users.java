package com.mystudypeer.mystudypeer.pojo;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "Users")
public class Users {
    @Column(name = "userId")
    private int id;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String token;

    @Column(name = "name")
    private String name;

    @Column(name = "registerDate")
    private Date registerDate;

    @Column(name = "password")
    private String password;

    @Column(name = "surname")
    private String surname;

    @Column(name = "city")
    private String city;

    @Column(name = "telno")
    private String telno;

    @Column(name = "class")
    private int userClass;

    @ManyToOne
    @JoinColumn(name = "programId")
    private UniversityProgram universityProgram;
}

package com.mystudypeer.mystudypeer.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Post implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "postId", nullable = false)
    private int postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "course")
    private String course;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "description")
    private String description;

    @Column(name = "postEnabled")
    private Boolean postEnabled;

    @Column(name = "email")
    private String email;

    @Column(name = "authorName")
    private String authorName;

    @Column(name = "authorSurname")
    private String authorSurname;

    @Column(name = "authorClass")
    private int authorClass;

    @Column(name = "universityId")
    private int universityId;
}

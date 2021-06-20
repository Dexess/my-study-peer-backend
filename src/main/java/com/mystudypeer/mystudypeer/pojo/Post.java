package com.mystudypeer.mystudypeer.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@Table (name = "Post" )
public class Post implements Serializable {
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

    @Column(name = "userId")
    private int userId;

    @Column(name = "authorName")
    private String authorName;

    @Column(name = "authorSurname")
    private String authorSurname;

    @Column(name = "authorClass")
    private int authorClass;

    @ManyToOne
    @JoinColumn(name = "universityId")
    private UniversityProgram universityProgram;
}

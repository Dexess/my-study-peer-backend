package com.mystudypeer.mystudypeer.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "commentId", nullable = false)
    private int commandId;
    @Column(name = "commentDate", nullable = false)
    private Date commentDate;
    @Column(name = "commentText", nullable = false)
    private String commentText;
    @Column(name = "commentorName", nullable = false)
    private String commentorName;
    @Column(name = "commentorSurname", nullable = false)
    private String commentorSurname;
    @Column(name = "commentorEmail", nullable = false)
    private String commentorEmail;
    @Column(name = "postId", nullable = false)
    private int postId;
}

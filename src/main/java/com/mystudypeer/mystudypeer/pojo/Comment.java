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
    private int commentId;
    @Column(name = "commentDate", nullable = false)
    private Date commentDate;
    @Column(name = "commentText", nullable = false)
    private String commentText;
    @Column(name = "commenterName", nullable = false)
    private String commenterName;
    @Column(name = "commenterSurname", nullable = false)
    private String commenterSurname;
    @Column(name = "commenterUserId", nullable = false)
    private int commenterUserId;
    @Column(name = "postId", nullable = false)
    private int postId;
}

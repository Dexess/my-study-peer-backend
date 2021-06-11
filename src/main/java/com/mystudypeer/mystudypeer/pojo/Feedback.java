package com.mystudypeer.mystudypeer.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Data
public class Feedback {
    @Id
    @Column(name = "givenTo", nullable = false)
    private String givenTo;
    @Id
    @Column(name = "givenBy", nullable = false)
    private String givenBy;
    @Id
    @Column(name = "forPost", nullable = false)
    private int forPost;
    @Column(name = "feedbackTitle", nullable = false)
    private String feedbackTitle;
    @Column(name = "feedbackText", nullable = false)
    private String feedbackText;
    @Column(name = "feedbackPoints", nullable = false)
    private int feedbackPoints;
    @Column(name = "feedbackDate", nullable = false)
    private Date feedbackDate;
}

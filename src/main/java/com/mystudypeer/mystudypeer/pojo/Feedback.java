package com.mystudypeer.mystudypeer.pojo;

import com.mystudypeer.mystudypeer.domains.FeedbackId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
public class Feedback implements Serializable {
    @EmbeddedId
    private FeedbackId feedbackId;
    @Column(name = "feedbackTitle", nullable = false)
    private String feedbackTitle;
    @Column(name = "feedbackText", nullable = false)
    private String feedbackText;
    @Column(name = "feedbackPoints", nullable = false)
    private int feedbackPoints;
    @Column(name = "feedbackDate", nullable = false)
    private Date feedbackDate;
}

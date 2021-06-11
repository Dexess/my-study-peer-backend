package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class FeedbackId implements Serializable {
    private int forPost;
    private String givenTo;
    private String givenBy;
}

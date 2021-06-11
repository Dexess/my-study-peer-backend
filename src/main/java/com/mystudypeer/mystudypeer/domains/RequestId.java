package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class RequestId implements Serializable {
    private String applierEmail;
    private int postId;

}

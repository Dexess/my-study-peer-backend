package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class RequestId implements Serializable {
    private int applierUserId;
    private int postId;

}

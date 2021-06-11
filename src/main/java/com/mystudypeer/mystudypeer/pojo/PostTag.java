package com.mystudypeer.mystudypeer.pojo;

import com.mystudypeer.mystudypeer.domains.PostTagId;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class PostTag implements Serializable {
    @EmbeddedId
    private PostTagId postTagId;
}


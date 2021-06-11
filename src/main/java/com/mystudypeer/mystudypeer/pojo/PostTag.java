package com.mystudypeer.mystudypeer.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class PostTag implements Serializable {
    @Id
    @Column(name = "postId")
    private int postId;
    @Id
    @Column(name = "tag")
    private String tag;


}


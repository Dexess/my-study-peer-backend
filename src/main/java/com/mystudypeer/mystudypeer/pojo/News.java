package com.mystudypeer.mystudypeer.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
public class News implements Serializable {
    @Id
    @Column(name = "newsId", nullable = false)
    private int newsId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "creationDate", nullable = false)
    private Date creationDate;
}

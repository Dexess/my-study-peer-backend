package com.mystudypeer.mystudypeer.domains;

import lombok.Data;

import java.sql.Date;

@Data
public class CreateNews {
    private String title;
    private String description;
    private Date creationDate;
}

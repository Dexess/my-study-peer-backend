package com.mystudypeer.mystudypeer.customs;

import lombok.Data;

import java.sql.Date;

@Data
public class CommentCustom {
    Date commentDate;
    String commentText;
    String commenterName;
    String commenterSurname;
    int commenterId;
}

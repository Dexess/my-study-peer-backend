package com.mystudypeer.mystudypeer.domains;

import lombok.Data;


import java.util.Date;

@Data
public class ProfileUser {

    private String name;
    private String surname;
    private Date registerDate;
    private String city;
    private String universityName;
    private String programName;
    private int userClass;

}

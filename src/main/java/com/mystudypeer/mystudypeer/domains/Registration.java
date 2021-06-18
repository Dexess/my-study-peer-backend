package com.mystudypeer.mystudypeer.domains;


import lombok.Data;

@Data
public class Registration {

    private String email;
    private String name;
    private String password;
    private String surname;
    private String city;
    private String telno;
    private int userClass;
    private int programId;
}

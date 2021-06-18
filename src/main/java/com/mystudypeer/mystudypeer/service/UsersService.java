package com.mystudypeer.mystudypeer.service;

import com.mystudypeer.mystudypeer.domains.Registration;
import com.mystudypeer.mystudypeer.exceptions.EntityNotFoundException;
import com.mystudypeer.mystudypeer.pojo.Users;
import com.mystudypeer.mystudypeer.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public Optional<Users> getUserForLogin(String email, String password ) {


        String encodedPass = this.hashSHA256(password);
        System.out.println(encodedPass);
        Optional<Users> users = usersRepository.getUsersByEmailAndPassword(email,encodedPass);
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Login info couldn't found");
        }
        return users;
    }

    public Optional<Users> registerUser(Registration registration){

        Users user = new Users();
        user.setEmail(registration.getEmail()); user.setPassword(this.hashSHA256(registration.getPassword()));
        user.setName(registration.getName()); user.setSurname(registration.getSurname());
        user.setCity(registration.getCity()); user.setUserClass(registration.getUserClass());
        user.setProgramId(registration.getProgramId()); user.setTelno(registration.getTelno());

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        user.setRegisterDate(date); user.setToken("6");
        usersRepository.save(user);
        Optional<Users> users = usersRepository.getUsersByEmailEquals(user.getEmail());
        return users;
    }

    public String hashSHA256(String password){
        String encodedPass = new String("");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] byteOfText = password.getBytes(StandardCharsets.UTF_8);
            byte[] hashedByteOfText = md.digest(byteOfText);
            StringBuilder hexString = new StringBuilder();
            for(int i: hashedByteOfText){
                hexString.append(String.format("%02x", 0XFF & i));
            }

            encodedPass = hexString.toString();
            return encodedPass;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodedPass;
    }
}

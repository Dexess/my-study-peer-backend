package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    /*@Query("SELECT u FROM Users AS u WHERE u.email = :email AND u.password = :password")
    public Optional<Users> getUserForLogin(@Param("email") String email, @Param("password") String password );*/

    public Optional<Users> getUsersByEmailAndPassword(String email, String password);
    public Optional<Users> getUsersByEmailEquals(String email);
}

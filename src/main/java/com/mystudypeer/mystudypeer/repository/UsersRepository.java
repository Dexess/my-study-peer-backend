package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

}

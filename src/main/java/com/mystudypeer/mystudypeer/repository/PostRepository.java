package com.mystudypeer.mystudypeer.repository;
import com.mystudypeer.mystudypeer.pojo.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

}

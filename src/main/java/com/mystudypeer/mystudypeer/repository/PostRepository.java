package com.mystudypeer.mystudypeer.repository;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findTop5ByEmailOrderByCreationDateDesc(String email);
    Post findByPostId(int postId);

}

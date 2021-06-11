package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}

package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.domains.PostTagId;
import com.mystudypeer.mystudypeer.pojo.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, PostTagId> {

    List<PostTag> findByPostTagId_PostId(int postId);
}

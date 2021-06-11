package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.domains.PostTagId;
import com.mystudypeer.mystudypeer.pojo.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, PostTagId> {

}

package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.pojo.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
}

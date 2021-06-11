package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.pojo.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}

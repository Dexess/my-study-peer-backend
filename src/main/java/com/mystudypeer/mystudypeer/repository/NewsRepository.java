package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.pojo.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

   @Query(nativeQuery = true, value = "SELECT n.newsId, n.title, n.description, n.creationDate " +
           "FROM News AS n ORDER BY n.creationDate DESC LIMIT 5 ")
   List<MyNews> homePageNews();

   public static interface MyNews{
      Integer getNewsId();
      String getTitle();
      String getDescription();
      Date getCreationDate();
   }
}

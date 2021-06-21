package com.mystudypeer.mystudypeer.repository;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.pojo.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findTop5ByUserIdOrderByCreationDateDesc(int userId);
    List<Post> findByUserIdOrderByCreationDateDesc(int userId);
    Post findByPostId(int postId);
    Post findByPostIdAndPostEnabled(int postId, Boolean bool);

    @Query(nativeQuery = true,value="SELECT p.postId, p.title, p.course, p.creationDate, p.description, p.authorName, p.authorSurname " +
            "FROM Post AS p WHERE p.postEnabled = 1 ORDER BY p.creationDate DESC LIMIT ?1,5 ")
    List<GetPosts> findPostsForPage(int page);
    public static interface GetPosts{
        int getPostId();
        String getTitle();
        String getCourse();
        Date getCreation();
        String getDescription();
        String getAuthorName();
        String getAuthorSurname();
    }

}

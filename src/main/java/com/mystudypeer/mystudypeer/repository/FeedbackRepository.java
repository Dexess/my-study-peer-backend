package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.domains.FeedbackId;
import com.mystudypeer.mystudypeer.pojo.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, FeedbackId> {

    @Query(nativeQuery = true,value = "SELECT u.userId as userId, u.name as name ,u.surname as surname,f.feedbackTitle as feedbackTitle, " +
            "f.feedbackText as feedbackText, f.feedbackPoints as feedbackPoints, f.feedbackDate as feedbackDate, f.forPost as forPost "+
            "FROM Users AS u, Feedback AS f " +
            "WHERE u.userId = f.givenBy AND f.givenTo = ?1 ")
    List<Feedbacks> findFeedbackForProfile(int givenTo);

    @Query(value = "SELECT avg(f.feedbackPoints + 0.0) FROM Feedback as f INNER JOIN Users as u on u.id = f.feedbackId.givenTo WHERE f.feedbackId.givenTo = ?1")
    Float averageFeedbackPoint(int givenTo);


    public static interface Feedbacks{
        int getUserId();
        String getName();
        String getSurname();
        String getFeedbackTitle();
        String getFeedbackText();
        int getFeedbackPoints();
        Date getFeedbackDate();
        int getForPost();
    }

    @Query(nativeQuery = true, value = "SELECT p.title as title, p.postId as postId " +
            "FROM Post as p " +
            "INNER JOIN Request as r on p.postId = r.postId " +
            "WHERE p.postId = ?1 AND r.applierUserId = ?2 AND r.status = 'accepted'")
    GiveFeedbackOn canGiveFeedback(int postId, int givenBy);

    @Query(nativeQuery = true, value = "SELECT p.title as title, p.postId as postId " +
            "FROM Post as p " +
            "INNER JOIN Request as r on p.postId = r.postId " +
            "WHERE p.postId = ?1 AND p.userId = ?2 AND r.status = 'accepted' AND r.applierUserId = ?3")
    GiveFeedbackOn canGiveFeedbackToMember(int postId, int givenBy, int givenTo);

    public static interface GiveFeedbackOn{
        String getTitle();
        int getPostId();
    }
}

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

    @Query(nativeQuery = true,value = "SELECT u.name as name ,u.surname as surname,f.feedbackTitle as feedbackTitle, " +
            "f.feedbackText as feedbackText, f.feedbackPoints as feedbackPoints, f.feedbackDate as feedbackDate, f.forPost as forPost "+
            "FROM Users AS u, Feedback AS f " +
            "WHERE u.email = f.givenBy AND f.givenTo = ?1 ")
    List<Feedbacks> findFeedbackForProfile(String givenTo);

    @Query(value = "SELECT avg(f.feedbackPoints + 0.0) FROM Feedback as f INNER JOIN Users as u on u.email = f.feedbackId.givenTo WHERE f.feedbackId.givenTo = ?1")
    Float averageFeedbackPoint(String givenTo);


    public static interface Feedbacks{
        String getName();
        String getSurname();
        String getFeedbackTitle();
        String getFeedbackText();
        int getFeedbackPoints();
        Date getFeedbackDate();
        int getForPost();
    }
}

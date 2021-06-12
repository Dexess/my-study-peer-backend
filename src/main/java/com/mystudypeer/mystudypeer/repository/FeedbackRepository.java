package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.domains.FeedbackId;
import com.mystudypeer.mystudypeer.pojo.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, FeedbackId> {
}

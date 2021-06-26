package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.domains.RequestId;
import com.mystudypeer.mystudypeer.pojo.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, RequestId> {


    List<Request> findTop5ByRequestIdApplierUserIdAndStatusOrderByRequestDateDesc(int userId, String status);
    List<Request> findAllByRequestIdApplierUserIdAndStatus(int userId, String status);
    Request findByRequestId_PostIdAndRequestId_ApplierUserIdAndStatus(int postId, int userId, String status);

    @Query(nativeQuery = true, value = "SELECT u.name, u.surname, u.class as userClass, uni.universityName, uni.programName, u.userId " +
            "FROM Users as u " +
            "INNER JOIN Request as r on u.userId = r.applierUserId " +
            "INNER JOIN UniversityProgram as uni on u.programId = uni.programId " +
            "WHERE r.status = ?2 and r.postId = ?1")
    List<Teammates> findTeammatesForPost(int postId, String status);


    static interface Teammates{
        String getName();
        String getSurname();
        int getUserClass();
        String getUniversityName();
        String getProgramName();
        int getUserId();
    }

    @Query(nativeQuery = true, value = "SELECT p.postId, p.title, u.name, u.surname, u.userId, r.requestDate, r.status " +
            "FROM Post as p " +
            "INNER JOIN Request as r on p.postId = r.postId " +
            "INNER JOIN Users as u on r.applierUserId = u.userId " +
            "WHERE p.userId = ?1 AND r.status = 'ongoing' " +
            "ORDER BY r.requestDate DESC")
    List<OwnedPostRequests> ownedPostRequests(int userId);

    public static interface OwnedPostRequests{
        int getPostId();
        String getTitle();
        String getName();
        String getSurname();
        String getUserId();
        String getRequestDate();
        String getStatus();
    }

    @Query(nativeQuery = true, value = "SELECT p.postId, p.title, r.status, r.requestDate " +
            "FROM Post as p " +
            "INNER JOIN Request as r on p.postId = r.postId " +
            "WHERE r.applierUserId = ?1 " +
            "ORDER BY r.requestDate DESC ")
    List<SubsPostRequests> subsPostRequests(int userId);

    public static interface SubsPostRequests{
        int getPostId();
        String getTitle();
        String getStatus();
        String getRequestDate();
    }

    Request findByRequestId_PostIdAndRequestId_ApplierUserId(int postId, int applierUserId);
}

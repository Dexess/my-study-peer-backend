package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.domains.RequestId;
import com.mystudypeer.mystudypeer.pojo.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, RequestId> {


    List<Request> findTop5ByRequestIdApplierEmailAndStatusOrderByRequestDateDesc(String applierEmail, String status);

    @Query(nativeQuery = true, value = "SELECT u.name, u.surname, u.class as userClass, uni.universityName, uni.programName, u.userId " +
            "FROM Users as u " +
            "INNER JOIN Request as r on u.email = r.applierEmail " +
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
}

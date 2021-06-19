package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.domains.RequestId;
import com.mystudypeer.mystudypeer.pojo.Post;
import com.mystudypeer.mystudypeer.pojo.Request;
import com.mystudypeer.mystudypeer.pojo.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, RequestId> {

    List<Request> findTop5ByRequestIdApplierEmailAndStatus(String applierEmail, RequestStatus status);
}

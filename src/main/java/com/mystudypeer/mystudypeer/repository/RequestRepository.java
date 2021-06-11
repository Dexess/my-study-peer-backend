package com.mystudypeer.mystudypeer.repository;

import com.mystudypeer.mystudypeer.domains.RequestId;
import com.mystudypeer.mystudypeer.pojo.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, RequestId> {

}

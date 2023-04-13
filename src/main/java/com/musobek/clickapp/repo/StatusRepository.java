package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    boolean existsByNameAndSpaceId(String name, Long spaceId);

    boolean existsByNameAndSpaceIdAndIdNot(String name, Long spaceId, Long statusId);

}
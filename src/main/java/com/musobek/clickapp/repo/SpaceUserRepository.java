package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Space;
import com.musobek.clickapp.entity.SpaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, Integer> {

    List<SpaceUser> findAllBySpaceIdId(Long spaceId_id);
}
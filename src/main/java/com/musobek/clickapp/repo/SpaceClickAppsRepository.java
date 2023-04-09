package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.SpaceClickApps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceClickAppsRepository extends JpaRepository<SpaceClickApps, Integer> {

    List<SpaceClickApps> findAllBySpaceIdId(Long spaceId_id);
}
package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.SpaceView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceViewRepository extends JpaRepository<SpaceView, Integer> {

    List<SpaceView> findAllBySpaceIdId(Long spaceId_id);
}
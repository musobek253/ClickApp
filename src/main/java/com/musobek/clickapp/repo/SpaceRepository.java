package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceRepository extends JpaRepository<Space, Integer> {
}
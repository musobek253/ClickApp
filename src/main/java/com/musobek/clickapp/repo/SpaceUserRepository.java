package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.SpaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceUserRepository extends JpaRepository<SpaceUser, Integer> {
}
package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.TimeTracked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTrackedRepository extends JpaRepository<TimeTracked, Integer> {
}
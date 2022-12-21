package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Integer> {
}
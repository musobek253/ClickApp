package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskUserRepository extends JpaRepository<TaskUser, Integer> {
}
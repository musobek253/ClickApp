package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Integer> {
}
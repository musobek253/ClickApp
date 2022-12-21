package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Integer> {
}
package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Task;
import com.musobek.clickapp.entity.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskUserRepository extends JpaRepository<TaskUser, Integer> {
    boolean existsByUserIdIdAndTaskIdId(Long userId_id, Long taskId_id);

    void deleteByUserIdIdAndTaskIdId(Long userId_id, Long taskId_id);
}
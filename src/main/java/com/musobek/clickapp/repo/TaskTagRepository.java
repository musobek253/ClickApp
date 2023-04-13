package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, Integer> {
    boolean existsByTagIdIdAndTaskIdId(Integer tagId_id, Long taskId_id);

    void deleteByTagIdIdAndTaskIdId(Integer tagId_id, Long taskId_id);
}
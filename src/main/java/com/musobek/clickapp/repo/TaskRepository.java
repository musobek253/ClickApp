package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByCategoryId(Long category_id);
}
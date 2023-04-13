package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByNameAndSpace_Id(String name, Long space_id);
}
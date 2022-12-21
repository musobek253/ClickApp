package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Integer> {
}
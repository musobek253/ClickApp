package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.WorkspacePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, Long> {
}
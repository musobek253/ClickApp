package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.WorkspacePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, Long> {
    /// query orqali ishlatish
    @Query(value = "select w from WorkspacePermission w where w.workspaceRoleId.workspaceId.id = ?1 and w.workspaceRoleId.name = ?2")
    List<WorkspacePermission> findAllByRolePermission(Long workspaceRole_workspace_id, String workspaceRole_name);

    List<WorkspacePermission>findAllByWorkspaceRoleId_WorkspaceId_IdAndWorkspaceRoleId_Name(Long workspaceRoleId_workspaceId_id, String workspaceRoleId_name);
}
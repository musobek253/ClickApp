package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Workspace;
import com.musobek.clickapp.entity.WorkspaceRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceRoleRepository extends JpaRepository<WorkspaceRole, Long> {


    Optional<WorkspaceRole> findByWorkspaceId(Workspace workspaceId);
    Optional<WorkspaceRole> findByIdAndWorkspaceIdId(Long id, Long workspaceId_id);
    boolean existsByNameAndWorkspaceIdId(String name, Long workspaceId_id);
}
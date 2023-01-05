package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Workspace;
import com.musobek.clickapp.entity.WorkspaceRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceRoleRepository extends JpaRepository<WorkspaceRole, Long> {


    Optional<WorkspaceRole> findByWorkspaceId(Workspace workspaceId);
}
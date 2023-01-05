package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Workspace;
import com.musobek.clickapp.entity.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {

    boolean existsWorkspaceUserByWorkspaceIdAndOwnerId_Id(Workspace workspaceId, Long ownerId_id);
    Optional<WorkspaceUser> findByOwnerId_Id(Long ownerId_id);

}
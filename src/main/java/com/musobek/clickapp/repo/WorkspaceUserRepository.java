package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Workspace;
import com.musobek.clickapp.entity.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {

    boolean existsWorkspaceUserByWorkspaceIdAndOwnerId_Id(Workspace workspaceId, Long ownerId_id);
    Optional<WorkspaceUser> findByOwnerId_Id(Long ownerId_id);
    List<WorkspaceUser> findAllByWorkspaceId_IdAndWorkspaceRole_Name(Long workspaceId_id, String workspaceRole_name);
    Optional<WorkspaceUser>  findByWorkspaceIdIdAndOwnerIdId(Long workspaceId_id, Long ownerId_id);
    @Transactional
    @Modifying
    void deleteByWorkspaceIdIdAndOwnerIdId(Long workspace_id, Long user_id);
    List<WorkspaceUser> findAllByWorkspaceIdId(Long workspaceId_id);


}
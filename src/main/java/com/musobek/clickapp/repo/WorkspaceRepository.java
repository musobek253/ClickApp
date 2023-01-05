package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    boolean existsByOwnerIdAndName(Long owner_id, String name);
}
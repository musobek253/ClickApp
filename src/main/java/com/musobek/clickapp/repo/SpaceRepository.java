package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    List<Space> findAllByWorkspaceId(Long workspace_id);
    boolean existsByNameAndWorkspaceId(String name, Long workspace_id);
    boolean existsByNameAndWorkspaceIdAndIdNot(String name, Long workspace_id, Long id);
    boolean existsByOwnerIdAndId(Long owner_id, Long id);
}
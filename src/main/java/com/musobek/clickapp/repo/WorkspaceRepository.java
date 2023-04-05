package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    boolean existsByOwnerIdAndName(Long owner_id, String name);

    boolean existsByOwnerIdAndNameAndIdNot(Long owner_id, String name, Long id);

    List<Workspace> findAllByOwnerId(Long owner_id);

    Optional<Workspace> findByIdAndOwnerId(Long id, Long owner_id);
}
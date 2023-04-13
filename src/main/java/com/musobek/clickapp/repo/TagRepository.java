package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findAllByWorkspaceIdId(Long workspaceId_id);
}
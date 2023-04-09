package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Integer> {
    boolean existsByProjectIdAndMemberId(Long id, Long memberId);

    void deleteAllByProjectIdAndMemberId(Long id, Long memberId);
}
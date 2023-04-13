package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByTaskIdId(Long taskId_id);
}
package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

     boolean existsByNameAndProjectIdAndIdNot(String name, Long projectId, Long id) ;


    List<Category> findAllByProjectId(Long project_id);

    boolean existsByNameAndProjectId(String name, Long projectId);

}
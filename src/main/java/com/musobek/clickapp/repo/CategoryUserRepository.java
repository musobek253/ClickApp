package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.CategoryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryUserRepository extends JpaRepository<CategoryUser, Integer> {
}
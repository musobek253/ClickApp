package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewRepository extends JpaRepository<View, Integer> {
    View getByName(String list);
}
package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, Integer> {
}
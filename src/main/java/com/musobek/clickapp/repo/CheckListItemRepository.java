package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.CheckListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListItemRepository extends JpaRepository<CheckListItem, Integer> {
}
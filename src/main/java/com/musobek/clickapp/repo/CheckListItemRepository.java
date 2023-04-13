package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.CheckListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckListItemRepository extends JpaRepository<CheckListItem, Integer> {
    List<CheckListItem> findAllByCheckListIdId(Integer checkListId_id);



}
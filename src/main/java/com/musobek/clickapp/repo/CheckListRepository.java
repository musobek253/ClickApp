package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckListRepository extends JpaRepository<CheckList, Integer> {

    List<CheckList> findAllByTaskIdId(Long taskId_id);
}
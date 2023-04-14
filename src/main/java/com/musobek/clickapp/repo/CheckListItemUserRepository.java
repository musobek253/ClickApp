package com.musobek.clickapp.repo;

import com.musobek.clickapp.dto.ChecklistItemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListItemUserRepository extends JpaRepository<ChecklistItemUser ,Long> {

    boolean existsByCheckListItemIdAndUserId(Integer checkListItem_id, Long user_id);
    void deleteByCheckListItemIdAndUserId(Integer checkListItem_id, Long user_id);

}

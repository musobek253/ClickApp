package com.musobek.clickapp.dto;

import com.musobek.clickapp.entity.CheckListItem;
import com.musobek.clickapp.entity.User;
import com.musobek.clickapp.entity.template.AbsLongEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChecklistItemUser extends AbsLongEntity {
    @ManyToOne
    private CheckListItem checkListItem;

    @ManyToOne
    private User user;
}

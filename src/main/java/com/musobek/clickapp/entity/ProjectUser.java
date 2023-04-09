package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.enam.Permission;
import com.musobek.clickapp.entity.template.AbsLongEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProjectUser extends AbsLongEntity {

    @ManyToOne
    private User member;

    @ManyToOne
    private Project project;

    @Enumerated(EnumType.STRING)
    private Permission permission;
}

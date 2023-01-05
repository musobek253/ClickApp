package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.enam.Permission;
import com.musobek.clickapp.entity.template.AbsLongEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspacePermission extends AbsLongEntity {


    @Enumerated(value = EnumType.STRING)
    private Permission permission;

    @ManyToOne
    private WorkspaceRole workspaceRoleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkspacePermission that = (WorkspacePermission) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.enam.WorkSpaceRoleName;
import com.musobek.clickapp.entity.template.AbsLongEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspaceRole  extends AbsLongEntity {


    @Column(nullable = false)
    private String name;


    @ManyToOne
    private Workspace workspaceId;

    @Enumerated(EnumType.STRING)
    private WorkSpaceRoleName extendsRole;

    public WorkspaceRole(Workspace workspaceSave, WorkSpaceRoleName roleOwner, Object extendsRole) {
    }



    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.template.AbsLongEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspaceUser extends AbsLongEntity {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Workspace workspaceId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User ownerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WorkspaceRole workspaceRole;

    private Timestamp dateInvited;

    private Timestamp dateJoined;

}

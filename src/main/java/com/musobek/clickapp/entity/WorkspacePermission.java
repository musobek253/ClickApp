package com.musobek.clickapp.entity;
import com.musobek.clickapp.entity.enam.Permission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspacePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private Permission permission;
    @ManyToOne
    private WorkspaceRole workspaceRoleId;
}

package com.musobek.clickapp.dto;

import lombok.Data;

@Data
public class WorkspaceRoleDto {
    private Long workspaceId;
    private String name;
    private Long extendsRoleId;
}

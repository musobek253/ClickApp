package com.musobek.clickapp.dto;

import lombok.*;

import java.util.List;
@Setter
@Getter
public class EditWorkspacePermissionDto {
    private Long workspaceId;

    private Long roleId;

    List<String> permissions;
}

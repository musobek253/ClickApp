package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.EditWorkspacePermissionDto;
import com.musobek.clickapp.dto.WorkspaceRoleDto;

public interface WorkspaceRoleService {
    ApiResponse addWorkspaceRole(WorkspaceRoleDto dto);

    ApiResponse addPermissionToRole(EditWorkspacePermissionDto dto);

    ApiResponse removePermissionToRole(EditWorkspacePermissionDto dto);
}

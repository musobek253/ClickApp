package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.WorkspaceDTO;
import com.musobek.clickapp.entity.User;


public interface WorkspaceService {
    ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user);

    ApiResponse editWorkspace(WorkspaceDTO workspaceDTO);

    ApiResponse changeOwnerWorkspace(Long id, Long ownerId);
}

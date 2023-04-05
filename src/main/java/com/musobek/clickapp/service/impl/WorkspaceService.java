package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.MemberDTO;
import com.musobek.clickapp.dto.WorkspaceDTO;
import com.musobek.clickapp.entity.User;
import com.musobek.clickapp.service.ResourceNotFoundException;

import java.util.UUID;


public interface WorkspaceService {
    ApiResponse addWorkspace(WorkspaceDTO dto, User user);

    ApiResponse editWorkspace(Long id, WorkspaceDTO dto) throws ResourceNotFoundException;

    ApiResponse changeOwnerWorkspace(Long id, Long ownerId);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO dto) throws ResourceNotFoundException;

    ApiResponse joinToWorkspace(Long id, User user);

    ApiResponse getWorkspaceMembers(Long id);

    ApiResponse getWorkspaceGuests(Long id);

    ApiResponse getUserWorkspaces(Long userId);;
}

package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.EditWorkspacePermissionDto;
import com.musobek.clickapp.dto.WorkspaceRoleDto;
import com.musobek.clickapp.entity.Workspace;
import com.musobek.clickapp.entity.WorkspacePermission;
import com.musobek.clickapp.entity.WorkspaceRole;
import com.musobek.clickapp.entity.enam.Permission;
import com.musobek.clickapp.entity.enam.WorkSpaceRoleName;
import com.musobek.clickapp.repo.WorkspacePermissionRepository;
import com.musobek.clickapp.repo.WorkspaceRepository;
import com.musobek.clickapp.repo.WorkspaceRoleRepository;
import com.musobek.clickapp.service.impl.WorkspaceRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceRoleServiceimpl implements WorkspaceRoleService {
    private final WorkspaceRoleRepository workspaceRoleRepository;

    private final WorkspaceRepository workspaceRepository;

    private final WorkspacePermissionRepository workspacePermissionRepository;
    @Override
    public ApiResponse addWorkspaceRole(WorkspaceRoleDto dto) {
        if (workspaceRoleRepository.existsByNameAndWorkspaceIdId(dto.getName(), dto.getWorkspaceId())) {
            return new ApiResponse("Role has already created", false);
        }
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (optionalWorkspace.isEmpty()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(dto.getExtendsRoleId());
        if (optionalWorkspaceRole.isEmpty()) {
            return new ApiResponse("Workspace role not found", false);
        }
        WorkspaceRole extendsRole = optionalWorkspaceRole.get();
        WorkspaceRole workspaceRole = new WorkspaceRole(
                dto.getName(),
                workspace,
                WorkSpaceRoleName.valueOf(extendsRole.getName())
                );
        WorkspaceRole savedWorkspaceRole = workspaceRoleRepository.save(workspaceRole);

        List<WorkspacePermission> permissions = new ArrayList<>();
        List<WorkspacePermission> workspacePermissions = workspacePermissionRepository.findAllByWorkspaceRoleId_WorkspaceId_IdAndWorkspaceRoleId_Name(dto.getWorkspaceId(), extendsRole.getName());
        workspacePermissions.stream()
                .map(workspacePermission ->
                         permissions.add(new WorkspacePermission(workspacePermission.getPermission(),savedWorkspaceRole)))
                .collect(Collectors.toList());

        workspacePermissionRepository.saveAll(permissions);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse addPermissionToRole(EditWorkspacePermissionDto dto) {
        if (!workspaceRepository.existsById(dto.getWorkspaceId())) {
            return new ApiResponse("Workspace not found", false);
        }
        Optional<WorkspaceRole> optionalRole = workspaceRoleRepository.findByIdAndWorkspaceIdId(dto.getRoleId(), dto.getWorkspaceId());
        if (optionalRole.isEmpty()) {
            return new ApiResponse("Role not found", false);
        }
        WorkspaceRole workspaceRole;
        workspaceRole = optionalRole.get();
        List<WorkspacePermission> permissions = workspacePermissionRepository.findAllByRolePermission(dto.getWorkspaceId(), workspaceRole.getName()); // native query bn yozilgan
        List<WorkspacePermission> newPermission = new ArrayList<>(permissions);
//        List<String> permissions1 = dto.getPermissions();
//        permissions1.stream();
//        permissions.stream().filter(workspacePermission -> !workspacePermission.getPermission().equals())

        for (String dtoPermission : dto.getPermissions()) {
            boolean has = false;
            for (WorkspacePermission permission : permissions) {
                if (permission.getPermission().equals(Permission.valueOf(dtoPermission))) {
                    has = true;
                    break;
                }
            }
            if (!has){
                newPermission.add(new WorkspacePermission(
                        Permission.valueOf(dtoPermission),
                        workspaceRole
                        ));
            }
        }
        workspacePermissionRepository.saveAll(newPermission);
        return new ApiResponse("Edited", true);
    }

    @Override
    public ApiResponse removePermissionToRole(EditWorkspacePermissionDto dto) {
        if (!workspaceRepository.existsById(dto.getWorkspaceId())) {
            return new ApiResponse("Workspace not found", false);
        }
        Optional<WorkspaceRole> optionalRole = workspaceRoleRepository.findByIdAndWorkspaceIdId(dto.getRoleId(), dto.getWorkspaceId());
        if (optionalRole.isEmpty()) {
            return new ApiResponse("Role not found", false);
        }
        WorkspaceRole workspaceRole = optionalRole.get();
        List<WorkspacePermission> permissions = workspacePermissionRepository.findAllByRolePermission(dto.getWorkspaceId(), workspaceRole.getName());
        List<WorkspacePermission> deletedPermissions = new ArrayList<>();
        for (String dtoPermission : dto.getPermissions()) {
            for (WorkspacePermission permission : permissions) {
                if (permission.getPermission().equals(Permission.valueOf(dtoPermission))) {
                    deletedPermissions.add(permission);
                }
            }
        }
        workspacePermissionRepository.deleteAll(deletedPermissions);
        return new ApiResponse("deleted", true);
    }
}

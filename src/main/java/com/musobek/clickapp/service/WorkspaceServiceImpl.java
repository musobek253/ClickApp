package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.WorkspaceDTO;
import com.musobek.clickapp.entity.*;
import com.musobek.clickapp.entity.enam.Permission;
import com.musobek.clickapp.entity.enam.WorkSpaceRoleName;
import com.musobek.clickapp.repo.*;
import com.musobek.clickapp.service.impl.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final AttachmentRepository attachmentRepository;

    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspacePermissionRepository workspacePermissionRepository;

    private final WorkspaceUserRepository workspaceUserRepository;

    private final UserRepository userRepository;


    @SneakyThrows
    @Override
    public ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user) {
        if (workspaceRepository.existsByOwnerIdAndName(user.getId(),workspaceDTO.getName())){
            return new ApiResponse("Already exist by workspaceName",false);
        }
        Workspace workspace = new Workspace(
                workspaceDTO.getName(),
                workspaceDTO.getColor(),
                user,
                workspaceDTO.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDTO.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")));
        Workspace workspaceSave = workspaceRepository.save(workspace);
        // ishxonaga xodimlarga rollarini berish
        WorkspaceRole ownerUser = workspaceRoleRepository.save(
                new WorkspaceRole(
                    workspaceSave,
                    WorkSpaceRoleName.ROLE_OWNER, null
                )
        );
        WorkspaceRole AdminUser = workspaceRoleRepository.save(
                new WorkspaceRole(
                        workspaceSave, WorkSpaceRoleName.ROLE_ADMIN,null
                )
        );
        WorkspaceRole memberUser = workspaceRoleRepository.save(
                new WorkspaceRole(
                        workspaceSave,
                        WorkSpaceRoleName.ROLE_MEMBER, null
                )
        );
        WorkspaceRole guestUser = workspaceRoleRepository.save(
                new WorkspaceRole(
                        workspaceSave,
                        WorkSpaceRoleName.ROLE_GUEST, null
                )
        );
        // ishxon  xodimlariga vazifa va huqularini berish
        Permission[] permissions = Permission.values();
        List<WorkspacePermission> workspacePermissionList = new ArrayList<>();
        for (Permission permission : permissions) {
        workspacePermissionList.add(new WorkspacePermission(permission,ownerUser));
        if (permission.getWorkspaceRoleNames().contains(WorkSpaceRoleName.ROLE_ADMIN)){
            workspacePermissionList.add(new WorkspacePermission(permission,AdminUser));
            }
        if (permission.getWorkspaceRoleNames().contains(WorkSpaceRoleName.ROLE_GUEST)) {
                workspacePermissionList.add(new WorkspacePermission(permission,guestUser));
            }
        if (permission.getWorkspaceRoleNames().contains(WorkSpaceRoleName.ROLE_MEMBER)) {
            workspacePermissionList.add(new WorkspacePermission(permission,memberUser));
            }
        }
        workspacePermissionRepository.saveAll(workspacePermissionList);

        workspaceUserRepository.save(new WorkspaceUser(workspaceSave,user,ownerUser,new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())));
        return new ApiResponse("Successfully Added", true);
    }

    @Override
    public ApiResponse editWorkspace(WorkspaceDTO workspaceDTO) {
        return null;
    }

    @Override
    public ApiResponse changeOwnerWorkspace(Long id, Long ownerId) {

//        Optional<User> userchange = userRepository.findById(id);
//
//        Optional<User> ownerOptinal = userRepository.findById(ownerId);
//
//        if(userchange.isEmpty()){
//            return new ApiResponse("user not found",false);
//
//        }
//        if(ownerOptinal.isEmpty()){
//            return new ApiResponse("owner not found",false);
//        }
//        User user = userchange.get();
//        User owner = ownerOptinal.get();
        Optional<WorkspaceUser> ownerChangerOptional = workspaceUserRepository.findByOwnerId_Id(ownerId);
        Optional<WorkspaceUser> userChangeOptional = workspaceUserRepository.findByOwnerId_Id(id);


        if (userChangeOptional.isEmpty()){
            return new ApiResponse("user not found",false);
        }
        if (ownerChangerOptional.isEmpty()){
            return new ApiResponse("user not found",false);
        }
        WorkspaceUser director = ownerChangerOptional.get();
        WorkspaceUser workspaceUser1 = userChangeOptional.get();
        if (director.getWorkspaceId().getId() != workspaceUser1.getWorkspaceId().getId()){
            return new ApiResponse("users do not exist in the same workplace",false);
        }
        WorkspaceRole directorWorkspaceRole = director.getWorkspaceRole();
        director.setWorkspaceRole(workspaceUser1.getWorkspaceRole());
        workspaceUser1.setWorkspaceRole(directorWorkspaceRole);
        workspaceUserRepository.saveAll(Arrays.asList(director,workspaceUser1));


//        Workspace workspace = director.getWorkspaceId();
//        User owner = workspace.getOwner();
//        User changeOwner = workspaceUser1.getOwnerId();
//        Optional<WorkspaceRole> workspaceRoleOptional = workspaceRoleRepository.findByWorkspaceId(workspace);
//        WorkspaceRole workspaceRole = workspaceRoleOptional.get();
//        WorkSpaceRoleName[] workSpaceRoleNames = WorkSpaceRoleName.values();
//        workspaceRole.setName(WorkSpaceRoleName.ROLE_ADMIN.name());
//        workspace.setOwner(changeOwner);
//        workspaceUser1.setOwnerId();

        return new ApiResponse("Successfully Edited",true);
    }
}

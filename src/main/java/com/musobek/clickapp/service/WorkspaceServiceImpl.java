package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.MemberDTO;
import com.musobek.clickapp.dto.WorkspaceDTO;
import com.musobek.clickapp.entity.*;
import com.musobek.clickapp.entity.enam.AddType;
import com.musobek.clickapp.entity.enam.Permission;
import com.musobek.clickapp.entity.enam.WorkSpaceRoleName;
import com.musobek.clickapp.mapper.MapstructMapper;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WorkspaceServiceImpl implements WorkspaceService {
    private final MapstructMapper mapper;
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
                null);
        Workspace workspaceSave = workspaceRepository.save(workspace);
        // ishxonaga xodimlarga rollarini berish

        WorkspaceRole ownerUser = new WorkspaceRole();
        ownerUser.setWorkspaceId(workspaceSave);
        ownerUser.setName(WorkSpaceRoleName.ROLE_OWNER.name());
        ownerUser.setExtendsRole(null);
        workspaceRoleRepository.save(ownerUser);
        WorkspaceRole AdminUser = new WorkspaceRole();
        AdminUser.setWorkspaceId(workspaceSave);
        AdminUser.setName(WorkSpaceRoleName.ROLE_ADMIN.name());
        AdminUser.setExtendsRole(null);
        workspaceRoleRepository.save(AdminUser);
        WorkspaceRole memberUser = new WorkspaceRole();
        memberUser.setWorkspaceId(workspaceSave);
        memberUser.setName(WorkSpaceRoleName.ROLE_MEMBER.name());
        memberUser.setExtendsRole(null);
        workspaceRoleRepository.save(memberUser);        WorkspaceRole guestUser = new WorkspaceRole();
        guestUser.setWorkspaceId(workspaceSave);
        guestUser.setName(WorkSpaceRoleName.ROLE_GUEST.name());
        guestUser.setExtendsRole(null);
        workspaceRoleRepository.save(guestUser);
        //ishxon  xodimlariga vazifa va huqularini berish
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
    public ApiResponse editWorkspace(Long id, WorkspaceDTO dto) throws ResourceNotFoundException {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Workspace workspace = optionalWorkspace.get();
        if (workspaceRepository.existsByOwnerIdAndNameAndIdNot(workspace.getOwner().getId(), dto.getName(), id)){
            return new ApiResponse("Sizda bunday nomli ishxona mavjud", false);
        }

        workspace.setAttachmentId(dto.getAvatarId() == null ? null : attachmentRepository.findById(dto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment")));
        workspace.setColor(dto.getColor());
        workspace.setName(dto.getName());
        workspaceRepository.save(workspace);
        return new ApiResponse("Updated", true);
    }


    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("O'chirildi", true);
        } catch (Exception e) {
            return new ApiResponse("Xatolik", false);
        }
    }

    @Override
    public ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO dto) throws ResourceNotFoundException {
        if (dto.getAddType().equals(AddType.ADD)){
            WorkspaceUser workspaceUser = new WorkspaceUser();
            Workspace workspace = workspaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id"));
            User user = userRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("id"));
            WorkspaceRole workspaceRole = workspaceRoleRepository.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id"));

            workspaceUser.setWorkspaceId(workspace);
            workspaceUser.setWorkspaceRole(workspaceRole);
            workspaceUser.setOwnerId(user);
            workspaceUser.setDateInvited(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("successfuly",true);
        } else if (dto.getAddType().equals(AddType.EDIT)) {
            WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdIdAndOwnerIdId(id, dto.getId()).orElseGet(WorkspaceUser::new);
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("id")));
            workspaceUserRepository.save(workspaceUser);
        } else if (dto.getAddType().equals(AddType.REMOVE)) {
            workspaceUserRepository.deleteByWorkspaceIdIdAndOwnerIdId(id, dto.getId());
        }
        return new ApiResponse("Muvaffaqiyatli", true);

    }


    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdIdAndOwnerIdId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("Success", true);
        }
        return new ApiResponse("Error", false);
    }

    @Override
    public ApiResponse getWorkspaceMembers(Long id) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (optionalWorkspace.isEmpty()){
            return new ApiResponse("Workspace not found", false);
        }
        List<User> members = new ArrayList<>();
        for (WorkSpaceRoleName value : WorkSpaceRoleName.values()) {
            if (value!=WorkSpaceRoleName.ROLE_GUEST){
                List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findAllByWorkspaceId_IdAndWorkspaceRole_Name(id, value.name());
                workspaceUsers.stream().map(workspaceUser -> members.add(workspaceUser.getOwnerId())).collect(Collectors.toList());
            }
        }
        return new ApiResponse("OK", true,
                mapper.toUserDto(members));
    }

    @Override
    public ApiResponse getWorkspaceGuests(Long id) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (optionalWorkspace.isEmpty()){
            return  new ApiResponse("Workspace not found",false);
        }
        List<User> gustUsers = new ArrayList<>();
        List<WorkspaceUser> gusetWorkspaceUsers = workspaceUserRepository.findAllByWorkspaceId_IdAndWorkspaceRole_Name(id, WorkSpaceRoleName.ROLE_GUEST.name());
        gusetWorkspaceUsers.stream().map(workspaceUser -> gustUsers.add(workspaceUser.getOwnerId())).collect(Collectors.toList());
        return new ApiResponse("Ok",true ,mapper.toUserDto(gustUsers)); // bu yerda mehmonUserlar ro'xati
    }

    @Override
    public ApiResponse getUserWorkspaces(Long userId) {
        return new ApiResponse("OK", true,
                mapper.toWorkspaceDto(workspaceRepository.findAllByOwnerId(userId)));
    }

    @Override
    public ApiResponse changeOwnerWorkspace(Long id, Long ownerId) {
//
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


//

        return new ApiResponse("Successfully Edited",true);
    }
}

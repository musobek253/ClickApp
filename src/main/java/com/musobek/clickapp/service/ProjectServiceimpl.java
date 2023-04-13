package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.AttachMemberPermissionDto;
import com.musobek.clickapp.dto.ProjectDto;
import com.musobek.clickapp.entity.*;
import com.musobek.clickapp.entity.enam.AccessType;
import com.musobek.clickapp.entity.enam.Permission;
import com.musobek.clickapp.entity.enam.Types;
import com.musobek.clickapp.repo.*;
import com.musobek.clickapp.service.impl.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceimpl implements ProjectService {

    private final ProjectUserRepository projectUserRepository;
    private final ProjectRepository projectRepository;
    private  final SpaceRepository spaceRepository;
    private final SpaceUserRepository spaceUserRepository;
    private  final UserRepository userRepository;
    private final  StatusRepository statusRepository;
    @Override
    public ApiResponse create(ProjectDto dto) {
        if(projectRepository.existsByNameAndSpace_Id(dto.getName(),dto.getSpaceId())){
            return new ApiResponse("Allready exist by project name",false);
        }
        Optional<Space> optionalSpace = spaceRepository.findById(dto.getSpaceId());
        if (!optionalSpace.isPresent()) {
            return new ApiResponse("Space not found", false);
        }
        Space space = optionalSpace.get();
        Project project = new Project();
        List<Category> categories = new ArrayList<>();
        for (String list : dto.getLists()) {
            Category category = new Category(list, project);
            categories.add(category);
        }
        project.setColor(dto.getColor());
        project.setCategories(categories);
        project.setAccessType(dto.getAccessType());
        project.setSpace(space);
        project.setName(dto.getName());
        Project savedProject = projectRepository.save(project);
        for (Category category : savedProject.getCategories()) {
            statusRepository.save(new Status(
                    "TO DO",
                    "gray",
                    space,
                    project,
                    category,
                    Types.OPEN
            ));
            statusRepository.save(new Status(
                    "Completed",
                    "green",
                    space,
                    project,
                    category,
                    Types.CLOSED
            ));
        }
        if (dto.getAccessType().equals(AccessType.PUBLIC)) {
            for (SpaceUser spaceUser : spaceUserRepository.findAllBySpaceIdId(dto.getSpaceId())) {
                for (Permission value : Permission.values()) {
                    projectUserRepository.save(new ProjectUser(spaceUser.getUserId(), savedProject, value));
                }
            }
        } else {
            for (Permission value : Permission.values()) {
                projectUserRepository.save(new ProjectUser(User.getCurrentUser(), savedProject, value));
            }
        }
        return new ApiResponse("Created", true);
    }
    @Override
    public ApiResponse attachMember(Long id, AttachMemberPermissionDto dto) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isEmpty()) {
            return new ApiResponse("Project not found", false);
        }
        Project project = optionalProject.get();
        List<ProjectUser> projectUsers = new ArrayList<>();
        dto.getMemberPermission().forEach((Long, permissionNames) -> {
                    if (spaceRepository.existsByOwnerIdAndId(id, project.getSpace().getId())) {
                        for (Permission permissionName : permissionNames) {
                            projectUsers.add(new ProjectUser(userRepository.getById(id), project, permissionName));
                        }
                    }
                }
        );
        projectUserRepository.saveAll(projectUsers);
        return new ApiResponse("OK", true);
    }

    @Override
    public ApiResponse deleteMember(Long id, Long memberId) {
        if (projectUserRepository.existsByProjectIdAndMemberId(id, memberId)) {
            projectUserRepository.deleteAllByProjectIdAndMemberId(id, memberId);
        }
        return new ApiResponse("Deleted", true);
    }
}

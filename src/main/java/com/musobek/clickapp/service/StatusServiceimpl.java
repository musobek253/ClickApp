package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.StatusDto;
import com.musobek.clickapp.dto.StatusEditDto;
import com.musobek.clickapp.entity.Category;
import com.musobek.clickapp.entity.Project;
import com.musobek.clickapp.entity.Space;
import com.musobek.clickapp.entity.Status;
import com.musobek.clickapp.entity.enam.Types;
import com.musobek.clickapp.mapper.MapstructMapper;
import com.musobek.clickapp.repo.CategoryRepository;
import com.musobek.clickapp.repo.ProjectRepository;
import com.musobek.clickapp.repo.SpaceRepository;
import com.musobek.clickapp.repo.StatusRepository;
import com.musobek.clickapp.service.impl.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StatusServiceimpl implements StatusService {

    private final ProjectRepository projectRepository;

    private final StatusRepository statusRepository;
    private final SpaceRepository spaceRepository;
    private final CategoryRepository categoryRepository;
    private  final MapstructMapper mapper;


    @Override
    public ApiResponse get(Long id) {
        Optional<Status> optionalStatus = statusRepository.findById(id);
        return optionalStatus.map(status -> new ApiResponse("OK", true, mapper.toStatusDto(status))).orElseGet(() -> new ApiResponse("Status not found", false));

    }

    @Override
    public ApiResponse getByCategory(Long categoryId) {
        return null;
    }

    @Override
    public ApiResponse create(StatusDto dto) {
        if (statusRepository.existsByNameAndSpaceId(dto.getName(), dto.getSpaceId())) {
            return new ApiResponse("Status already created!", false);
        }
        Optional<Space> optionalSpace = spaceRepository.findById(dto.getSpaceId());
        if (optionalSpace.isEmpty()) {
            return new ApiResponse("Space not found", false);
        }
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());
        if (optionalProject.isEmpty()) {
            return new ApiResponse("Project not found", false);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new ApiResponse("Category not found", false);
        }
        Category category = optionalCategory.get();
        Project project = optionalProject.get();
        Space space = optionalSpace.get();
        Status status = new Status(dto.getName(), dto.getColor(), space, project, category, Types.CUSTOM);
        statusRepository.save(status);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse edit(Long id, StatusEditDto dto) {
        Optional<Status> optionalStatus = statusRepository.findById(id);
        if (optionalStatus.isEmpty()) {
            return new ApiResponse("Status not found", false);
        }
        Status status = optionalStatus.get();
        if (statusRepository.existsByNameAndSpaceIdAndIdNot(dto.getName(), status.getSpace().getId(), id)) {
            return new ApiResponse("Status has already existed", false);
        }
        status.setName(dto.getName());
        status.setColor(dto.getColor());
        statusRepository.save(status);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse delete(Long id) {
        if (!statusRepository.existsById(id)){
            return new ApiResponse("Status not found", false);
        }
        statusRepository.deleteById(id);
        return new ApiResponse("Delete", true);
    }
}

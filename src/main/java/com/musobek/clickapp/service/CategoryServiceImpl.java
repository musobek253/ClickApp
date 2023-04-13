package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.CategoryDto;
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
import com.musobek.clickapp.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


  private final CategoryRepository categoryRepository;

  private final ProjectRepository projectRepository;

  private final StatusRepository statusRepository;
  private final SpaceRepository spaceRepository;

  private final MapstructMapper mapper;




    @Override
    public ApiResponse get(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(category -> new ApiResponse("OK", true, mapper.toCategoryDto(category))).orElseGet(() -> new ApiResponse("Category not found", false));
    }

    @Override
    public ApiResponse getByProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            return new ApiResponse("Project not found", false);
        }
        return new ApiResponse("OK", true, mapper.toCategoryDto(categoryRepository.findAllByProjectId(projectId)));
    }

    @Override
    public ApiResponse create(CategoryDto dto) {
        if (categoryRepository.existsByNameAndProjectId(dto.getName(),dto.getProjectId())){
            return new ApiResponse("Category Already exit by name", false);

        }
        Optional<Space> optionalSpace = spaceRepository.findById(dto.getSpaceId());
        if (optionalSpace.isEmpty()){
            return  new ApiResponse("Space not found", false);
        }
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());

        if (optionalProject.isEmpty()){
            return new ApiResponse("project not found",false);
        }
        Space space = optionalSpace.get();
        Project project = optionalProject.get();
        Category category = new Category(dto.getName(), project);
        categoryRepository.save(category);
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
                Types.CLOSED.CLOSED
        ));
        return  new ApiResponse("creted",true);
    }
    @Override
    public ApiResponse edit(Long id, CategoryDto dto) {
        if (categoryRepository.existsByNameAndProjectIdAndIdNot(dto.getName(), dto.getProjectId(), id)) {
            return new ApiResponse("Category has already existed", false);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return new ApiResponse("Category not found", false);
        }
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());
        if (optionalProject.isEmpty()) {
            return new ApiResponse("Project not found", false);
        }
        Project project = optionalProject.get();
        Category category = optionalCategory.get();
        category.setName(dto.getName());
        category.setProject(project);
        categoryRepository.save(category);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            return new ApiResponse("Category not found", false);
        }
        categoryRepository.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}

package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.CategoryDto;

public interface CategoryService {
    ApiResponse get(Long id);

    ApiResponse getByProject(Long projectId);

    ApiResponse create(CategoryDto dto);

    ApiResponse edit(Long id, CategoryDto dto);

    ApiResponse delete(Long id);
}

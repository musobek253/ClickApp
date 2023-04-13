package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.StatusDto;
import com.musobek.clickapp.dto.StatusEditDto;

import java.util.UUID;

public interface StatusService {
    ApiResponse get(Long id);

    ApiResponse getByCategory(Long categoryId);

    ApiResponse create(StatusDto dto);

    ApiResponse edit(Long id, StatusEditDto dto);

    ApiResponse delete(Long id);
}

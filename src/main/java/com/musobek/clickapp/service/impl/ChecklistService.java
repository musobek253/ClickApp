package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.ChecklistDto;

public interface ChecklistService {
    ApiResponse getChecklistByTask(Long taskId);

    ApiResponse create(ChecklistDto dto);

    ApiResponse edit(Integer id, String name);

    ApiResponse delete(Integer id);
}

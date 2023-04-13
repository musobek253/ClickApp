package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.ChecklistItemDto;
import com.musobek.clickapp.dto.ItemUserDto;

public interface ChecklistItemService {
    ApiResponse getByChecklist(Integer checklistId);

    ApiResponse create(ChecklistItemDto dto);

    ApiResponse assign(ItemUserDto dto);

    ApiResponse removeUser(ItemUserDto dto);

    ApiResponse edit(Integer id, String name);

    ApiResponse resolve(Integer id);

    ApiResponse delete(Integer id);
}

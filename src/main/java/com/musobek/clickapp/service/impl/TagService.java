package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.TagEditDto;

public interface TagService {
    ApiResponse get(Long workspaceId);

    ApiResponse editTag(Integer tagId, TagEditDto dto);

    ApiResponse deleteTag(Integer tagId);
}

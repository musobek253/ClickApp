package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.AttachMemberPermissionDto;
import com.musobek.clickapp.dto.ProjectDto;

public interface ProjectService {
    ApiResponse create(ProjectDto dto);

    ApiResponse attachMember(Long id, AttachMemberPermissionDto dto);

    ApiResponse deleteMember(Long id, Long memberId);


}

package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.*;
import com.musobek.clickapp.entity.User;



public interface SpaceService {
    ApiResponse get(Long id);

    ApiResponse getWorspaceId(Long workspaceId);

    ApiResponse addSpace(SpaceDto dto, User user);

    ApiResponse editSpace(SpaceDto dto, Long id);

    ApiResponse attachMembers(AttachMemberDto dto, Long id);

    ApiResponse attachViews(AttachViewsDto dto, Long id);

    ApiResponse attachClickApp(AttachClickAppDto dto, Long id);

    ApiResponse detachMembers(AttachMemberDto dto, Long id);

    ApiResponse detachViews(AttachViewsDto dto, Long id);

    ApiResponse detachClickApp(AttachClickAppDto dto, Long id);

    ApiResponse delete(Long id);

    ApiResponse getViewBySpace(Long id);
}

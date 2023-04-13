package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.CommentDto;

public interface CommentService {
    ApiResponse get(Integer id);

    ApiResponse getByTask(Long taskId);

    ApiResponse addComment(CommentDto dto);

    ApiResponse editComment(Integer id, String text);

    ApiResponse delete(Integer id);
}

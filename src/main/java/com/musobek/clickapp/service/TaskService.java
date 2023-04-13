package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface TaskService {
    ApiResponse get(Long id);

    ApiResponse getByCategory(Long categoryId);

    ApiResponse create(TaskDto dto);

    ApiResponse changeStatus(Long id, Long statusId);

    ApiResponse changeDescription(Long id, String description);

    ApiResponse attachFile(Long id, MultipartFile file);

    ApiResponse detachFile(Long id, String fileName);

    ApiResponse createSubtask(SubtaskDto dto);

    ApiResponse dueDate(Long id, DueDateDto dto);

    ApiResponse changePriority(Long id, Long priorityId);

    ApiResponse changeEstimate(Long id, Long estimate);

    ApiResponse addTag(TaskTagDto dto);

    ApiResponse removeTag(Integer tagId, Long taskId);

    ApiResponse assignUser(Long taskId, Long userId);

    ApiResponse removeUser(Long taskId, Long userId);
}

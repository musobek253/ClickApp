package com.musobek.clickapp.controller;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.AttachMemberPermissionDto;
import com.musobek.clickapp.dto.ProjectDto;
import com.musobek.clickapp.service.impl.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> create( @RequestBody ProjectDto dto) {
        ApiResponse apiResponse = projectService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/attach/member")
    public ResponseEntity<?> attachMember(@PathVariable Long id,  @RequestBody AttachMemberPermissionDto dto) {
        ApiResponse apiResponse = projectService.attachMember(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/delete/member")
    public ResponseEntity<?> deleteMember(@PathVariable Long id, @RequestParam Long memberId) {
        ApiResponse apiResponse = projectService.deleteMember(id,memberId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

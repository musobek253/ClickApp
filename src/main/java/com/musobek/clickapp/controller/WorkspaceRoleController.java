package com.musobek.clickapp.controller;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.EditWorkspacePermissionDto;
import com.musobek.clickapp.dto.WorkspaceRoleDto;
import com.musobek.clickapp.service.impl.WorkspaceRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspace/role")
@RequiredArgsConstructor
public class WorkspaceRoleController {
    private final WorkspaceRoleService workspaceRoleService;

    @PostMapping
    public ResponseEntity<?> addWorkspaceRole( @RequestBody WorkspaceRoleDto dto) {
        ApiResponse apiResponse = workspaceRoleService.addWorkspaceRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/add/permission")
    public HttpEntity<?> addPermissionToRole(@RequestBody EditWorkspacePermissionDto dto) {
        ApiResponse apiResponse = workspaceRoleService.addPermissionToRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/remove/permission")
    public HttpEntity<?> removePermissionToRole(@RequestBody EditWorkspacePermissionDto dto) {
        ApiResponse apiResponse = workspaceRoleService.removePermissionToRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}

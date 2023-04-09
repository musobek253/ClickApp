package com.musobek.clickapp.controller;

import com.musobek.clickapp.dto.*;
import com.musobek.clickapp.entity.User;
import com.musobek.clickapp.security.CurrentUser;
import com.musobek.clickapp.service.impl.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/space")
@RequiredArgsConstructor
public class SpaceController {
    private final SpaceService spaceService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        ApiResponse apiResponse = spaceService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/workspace/{id}")
    public ResponseEntity<?> getWorspaceId(@PathVariable(value = "id") Long workspaceId) {
        ApiResponse apiResponse = spaceService.get(workspaceId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> addSpace(@RequestBody SpaceDto dto, @CurrentUser User user) {
        ApiResponse apiResponse = spaceService.addSpace(dto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editSpace( @RequestBody SpaceDto dto, @PathVariable Long id) {
        ApiResponse apiResponse = spaceService.editSpace(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/attach/members")
    public ResponseEntity<?> attachMembers(@RequestBody AttachMemberDto dto, @PathVariable Long id) {
        ApiResponse apiResponse = spaceService.attachMembers(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/views/{id}")
    public ResponseEntity<?> getViewBySpace(@PathVariable Long id) {
        ApiResponse apiResponse = spaceService.getViewBySpace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/attach/views")
    public ResponseEntity<?> attachViews(@RequestBody AttachViewsDto dto, @PathVariable Long id) {
        ApiResponse apiResponse = spaceService.attachViews(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/attach/click/apps")
    public ResponseEntity<?> attachClickApp(@RequestBody AttachClickAppDto dto, @PathVariable Long id) {
        ApiResponse apiResponse = spaceService.attachClickApp(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/detach/members")
    public ResponseEntity<?> detachMembers( @RequestBody AttachMemberDto dto, @PathVariable Long id) {
        ApiResponse apiResponse = spaceService.detachMembers(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/detach/views")
    public ResponseEntity<?> detachViews( @RequestBody AttachViewsDto dto, @PathVariable Long id) {
        ApiResponse apiResponse = spaceService.detachViews(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}/detach/click/apps")
    public ResponseEntity<?> detachClickApp( @RequestBody AttachClickAppDto dto, @PathVariable Long id) {
        ApiResponse apiResponse = spaceService.detachClickApp(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = spaceService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
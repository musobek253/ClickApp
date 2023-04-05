package com.musobek.clickapp.controller;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.MemberDTO;
import com.musobek.clickapp.dto.WorkspaceDTO;
import com.musobek.clickapp.entity.User;
import com.musobek.clickapp.security.CurrentUser;
import com.musobek.clickapp.service.ResourceNotFoundException;
import com.musobek.clickapp.service.impl.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class WorkspaceController {
    private final WorkspaceService workspaceService;

        @GetMapping("/{id}/members")
        public HttpEntity<?> getWorkspaceMembers(@PathVariable Long id) {
            ApiResponse apiResponse = workspaceService.getWorkspaceMembers(id);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        }

        @GetMapping("/{id}/guests")
        public HttpEntity<?> getWorkspaceGuests(@PathVariable Long id) {
            ApiResponse apiResponse = workspaceService.getWorkspaceGuests(id);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        }

        @GetMapping("/user/{id}")
        public HttpEntity<?> getUserWorkspaces(@PathVariable(value = "id") Long userId) {
            ApiResponse apiResponse = workspaceService.getUserWorkspaces(userId);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        }

        @PostMapping
        public HttpEntity<?> addWorkspace( @RequestBody WorkspaceDTO dto, @CurrentUser User user) {
            ApiResponse apiResponse = workspaceService.addWorkspace(dto, user);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        }

        /**
         * NAME, COLOR, AVATAR O'ZGARAISHI MUMKIN
         *
         * @param dto
         * @return
         */
        @PutMapping("/{id}")
        public HttpEntity<?> editWorkspace(@PathVariable Long id, @RequestBody WorkspaceDTO dto) throws ResourceNotFoundException {
            ApiResponse apiResponse = workspaceService.editWorkspace(id, dto);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        }

        /**
         * @param id
         * @param ownerId
         * @return
         */
        @PutMapping("/changeOwner/{id}")
        public HttpEntity<?> changeOwnerWorkspace(@PathVariable Long id,
                                                  @RequestParam Long ownerId) {
            ApiResponse apiResponse = workspaceService.changeOwnerWorkspace(id, ownerId);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        }


        /**
         * ISHXONANI O'CHIRISH
         *
         * @param id
         * @return
         */
        @DeleteMapping("/{id}")
        public HttpEntity<?> deleteWorkspace(@PathVariable Long id) {
            ApiResponse apiResponse = workspaceService.deleteWorkspace(id);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        }

        @PostMapping("/addOrEditOrRemove/{id}")
        public HttpEntity<?> addOrEditOrRemoveWorkspace(@PathVariable Long id,
                                                        @RequestBody MemberDTO dto) throws ResourceNotFoundException {
            ApiResponse apiResponse = workspaceService.addOrEditOrRemoveWorkspace(id, dto);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        }

        @PutMapping("/join")
        public HttpEntity<?> joinToWorkspace(@RequestParam Long id,
                                             @CurrentUser User user) {
            ApiResponse apiResponse = workspaceService.joinToWorkspace(id, user);
            return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
        }

    }


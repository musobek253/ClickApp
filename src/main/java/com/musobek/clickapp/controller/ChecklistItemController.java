package com.musobek.clickapp.controller;
import com.musobek.clickapp.service.impl.ChecklistItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.musobek.clickapp.dto.*;


@RestController
@RequestMapping("/api/checklist/item")
@RequiredArgsConstructor
public class ChecklistItemController {
    private final ChecklistItemService checklistItemService;

    @GetMapping("/checklist/{id}")
    public ResponseEntity<?> getByChecklist(@PathVariable(value = "id") Integer checklistId) {
        ApiResponse apiResponse = checklistItemService.getByChecklist(checklistId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<?> create( @RequestBody ChecklistItemDto dto) {
        ApiResponse apiResponse = checklistItemService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/assign/user")
    public ResponseEntity<?> assign( @RequestBody ItemUserDto dto) {
        ApiResponse apiResponse = checklistItemService.assign(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/remove/user")
    public ResponseEntity<?> removeUser( @RequestBody ItemUserDto dto) {
        ApiResponse apiResponse = checklistItemService.removeUser(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @RequestParam String name) {
        ApiResponse apiResponse = checklistItemService.edit(id, name);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/resolve/{id}")
    public ResponseEntity<?> resolve(@PathVariable Integer id) {
        ApiResponse apiResponse = checklistItemService.resolve(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = checklistItemService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }



}

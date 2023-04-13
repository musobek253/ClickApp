package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.ChecklistItemDto;
import com.musobek.clickapp.dto.ChecklistItemUser;
import com.musobek.clickapp.dto.ItemUserDto;
import com.musobek.clickapp.entity.CheckList;
import com.musobek.clickapp.entity.CheckListItem;
import com.musobek.clickapp.entity.User;
import com.musobek.clickapp.mapper.MapstructMapper;
import com.musobek.clickapp.repo.CheckListItemRepository;
import com.musobek.clickapp.repo.CheckListItemUserRepository;
import com.musobek.clickapp.repo.CheckListRepository;
import com.musobek.clickapp.repo.UserRepository;
import com.musobek.clickapp.service.impl.ChecklistItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckListItemServiceImpl implements ChecklistItemService {

    private  final CheckListItemRepository checkListItemRepository;
    private final MapstructMapper mapper;
    private final CheckListRepository checkListRepository;
    private  final UserRepository userRepository;
    private  final CheckListItemUserRepository checkListItemUserRepository;

    @Override
    public ApiResponse getByChecklist(Integer checklistId) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(checklistId);
        if (optionalCheckList.isEmpty()){
            return new ApiResponse("Checklist not found",false);
        }
        List<CheckListItem> checkListItems= checkListItemRepository.findAllByCheckListIdId(checklistId);
        return new ApiResponse("get by checklist", true ,mapper.toCheckListItemDto(checkListItems));
    }

    @Override
    public ApiResponse create(ChecklistItemDto dto) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(dto.getCheckListId());
        if (!optionalCheckList.isEmpty()) {
            return new ApiResponse("Checklist not found", false);
        }
        CheckList checkList = optionalCheckList.get();
        CheckListItem item = new CheckListItem(dto.getName(), checkList, false);
        checkListItemRepository.save(item);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse assign(ItemUserDto dto) {
        Optional<CheckListItem> optionalCheckListItem = checkListItemRepository.findById(dto.getItemId());
        if (!optionalCheckListItem.isEmpty()) {
            return new ApiResponse("Item not found", false);
        }
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (!optionalUser.isEmpty()) {
            return new ApiResponse("User not found", false);
        }
        CheckListItem item = optionalCheckListItem.get();
        User user = optionalUser.get();
        checkListItemUserRepository.save(new ChecklistItemUser(item,user));
        return new ApiResponse("Assigned", true);
    }

    @Override
    public ApiResponse removeUser(ItemUserDto dto) {
        if (!checkListItemUserRepository.existsByCheckListItemIdAndUserId(dto.getItemId(),dto.getUserId())){
            return new ApiResponse("Not found", false);
        }
        checkListItemUserRepository.deleteByCheckListItemIdAndUserId(dto.getItemId(),dto.getUserId());
        return new ApiResponse("Removed", true);
    }

    @Override
    public ApiResponse edit(Integer id, String name) {
        Optional<CheckListItem> optionalCheckListItem = checkListItemRepository.findById(id);
        if (!optionalCheckListItem.isEmpty()) {
            return new ApiResponse("Item not found", false);
        }
        CheckListItem item = optionalCheckListItem.get();
        item.setName(name);
        checkListItemRepository.save(item);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse resolve(Integer id) {
        Optional<CheckListItem> optionalCheckListItem = checkListItemRepository.findById(id);
        if (!optionalCheckListItem.isEmpty()) {
            return new ApiResponse("Item not found", false);
        }
        CheckListItem item = optionalCheckListItem.get();
        item.setResolved(true);
        checkListItemRepository.save(item);
        return new ApiResponse("Resolved", true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        return null;
    }
}

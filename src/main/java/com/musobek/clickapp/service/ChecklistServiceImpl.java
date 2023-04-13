package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.ChecklistDto;
import com.musobek.clickapp.entity.CheckList;
import com.musobek.clickapp.entity.Task;
import com.musobek.clickapp.mapper.MapstructMapper;
import com.musobek.clickapp.repo.CheckListRepository;
import com.musobek.clickapp.repo.TaskRepository;
import com.musobek.clickapp.service.impl.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {
    private final CheckListRepository checkListRepository;
    private final TaskRepository taskRepository;
    private final MapstructMapper mapper;




    @Override
    public ApiResponse getChecklistByTask(Long taskId) {
        if (taskRepository.findById(taskId).isEmpty()){
            return new ApiResponse("task not found", false);
        }
      return new ApiResponse(" get by task", true, mapper.toChkDto(checkListRepository.findAllByTaskIdId(taskId)));
    }

    @Override
    public ApiResponse create(ChecklistDto dto) {
        Optional<Task> taskOptional = taskRepository.findById(dto.getTaskId());
        if (taskOptional.isEmpty()){
            return new ApiResponse("task not found", false);
        }
        Task task = taskOptional.get();
        CheckList checkList = new CheckList(dto.getName(),task);
        checkListRepository.save(checkList);
        return new ApiResponse("Created successfully", true);
    }

    @Override
    public ApiResponse edit(Integer id, String name) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        if (optionalCheckList.isEmpty()){
            return  new ApiResponse("Checklist not found",false);
        }
        CheckList checkList = optionalCheckList.get();
        checkList.setName(name);
        checkListRepository.save(checkList);
        return new ApiResponse("Update successfully",true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        if (optionalCheckList.isEmpty()){
            return  new ApiResponse("Checklist not found",false);
        }
        checkListRepository.deleteById(id);
        return new ApiResponse("Deleted successfuly",true);
    }
}

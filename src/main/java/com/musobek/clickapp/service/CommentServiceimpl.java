package com.musobek.clickapp.service;

import com.musobek.clickapp.dto.ApiResponse;
import com.musobek.clickapp.dto.CommentDto;
import com.musobek.clickapp.entity.Comment;
import com.musobek.clickapp.entity.Task;
import com.musobek.clickapp.mapper.MapstructMapper;
import com.musobek.clickapp.repo.CommentRepository;
import com.musobek.clickapp.repo.TaskRepository;
import com.musobek.clickapp.service.impl.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceimpl implements CommentService {
    private final CommentRepository commentRepo;

    private final TaskRepository taskRepo;

    private final MapstructMapper mapper;


    @Override
    public ApiResponse get(Integer id) {
        Optional<Comment> optionalComment = commentRepo.findById(id);
        return optionalComment.map(comment -> new ApiResponse("Ok", true, mapper.toCommentDto(comment))).orElseGet(() -> new ApiResponse("Comment not found", false));

    }

    @Override
    public ApiResponse getByTask(Long taskId) {
        if (!taskRepo.existsById(taskId)){
            return new ApiResponse("Task not found", false);
        }
        return new ApiResponse("OK", true, mapper.toCommentDto(commentRepo.findAllByTaskIdId(taskId)));
    }

    @Override
    public ApiResponse addComment(CommentDto dto) {
        Optional<Task> optionalTask = taskRepo.findById(dto.getTaskId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        Comment comment = new Comment(dto.getText(), task);
        commentRepo.save(comment);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse editComment(Integer id, String text) {
        Optional<Comment> optionalComment = commentRepo.findById(id);
        if (!optionalComment.isPresent()) {
            return new ApiResponse("Comment not found", false);
        }
        Comment comment = optionalComment.get();
        comment.setName(text);
        commentRepo.save(comment);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse delete(Integer id) {
        if (!commentRepo.existsById(id)) {
            return new ApiResponse("Comment not found", false);
        }
        commentRepo.deleteById(id);
        return new ApiResponse("Deleted", true);
    }
}

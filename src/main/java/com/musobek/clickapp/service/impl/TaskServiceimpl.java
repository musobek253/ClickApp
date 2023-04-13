package com.musobek.clickapp.service.impl;

import com.musobek.clickapp.dto.*;
import com.musobek.clickapp.entity.*;
import com.musobek.clickapp.entity.enam.AddType;
import com.musobek.clickapp.mapper.MapstructMapper;
import com.musobek.clickapp.repo.*;
import com.musobek.clickapp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceimpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private  final StatusRepository statusRepository;
    private  final PriorityRepository priorityRepo;
    private  final TaskUserRepository taskUserRepository;
    private  final AttachmentRepository attachmentRepository;
    private  final TaskAttachmentRepository taskAttachmentRepository;
    private  final TaskHistoryRepository taskHistoryRepository;
    private  final WorkspaceRepository workspaceRepository;
    private  final TagRepository tagRepository;
    private  final TaskTagRepository taskTagRepository;
    private  final UserRepository userRepository;

    private final Path root = Paths.get("/Users/musobek/spring_project");



    private final MapstructMapper mapper;
    @Override
    public ApiResponse get(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(task -> new ApiResponse("Ok", true, mapper.toTaskDto(task))).orElseGet(() -> new ApiResponse("Task not found", false));
    }

    @Override
    public ApiResponse getByCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            return new ApiResponse("Category not found", false);
        }
        return new ApiResponse("OK", true, mapper.toTaskDto(taskRepository.findAllByCategoryIdId(categoryId)));
    }

    @Override
    public ApiResponse create(TaskDto dto) {
        Optional<Status> optionalStatus = statusRepository.findById(dto.getStatusId());
        if (optionalStatus.isEmpty()) {
            return new ApiResponse("Status not found", false);
        }
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new ApiResponse("Category not found", false);
        }
        Optional<Priority> optionalPriority = priorityRepo.findById(Math.toIntExact(dto.getPriorityId()));
        if (optionalPriority.isEmpty()) {
            return new ApiResponse("Priority not found", false);
        }
        Status status = optionalStatus.get();
        Category category = optionalCategory.get();
        Priority priority = optionalPriority.get();
        Task task = new Task(dto.getName(), status, category, priority, dto.getStartedDate(), dto.isStartTimeHas(), dto.isDueTimeHas(), dto.getDueDate());
        Task savedTask = taskRepository.save(task);

        createTaskHistory(savedTask, null, null, null,
                User.getCurrentUser().getUsername() + " created task");

        TaskUser taskUser = new TaskUser( User.getCurrentUser(),savedTask);
        taskUserRepository.save(taskUser);
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse changeStatus(Long id, Long statusId) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            return new ApiResponse("Task not found", false);
        }
        Optional<Status> optionalStatus = statusRepository.findById(statusId);
        if (optionalStatus.isEmpty()) {
            return new ApiResponse("Status not found", false);
        }
        Status status = optionalStatus.get();
        Task task = optionalTask.get();
        createTaskHistory(task, "status", task.getStatus().getName(), status.getName(),
                User.getCurrentUser().getUsername() + " changed status from " + task.getStatus().getName() + " to " +
                        status.getName());
        task.setStatus(status);
        taskRepository.save(task);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse changeDescription(Long id, String description) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isEmpty()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        createTaskHistory(task, "description", task.getDescription(), description,
                User.getCurrentUser().getUsername() + " changed description");
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse attachFile(Long id, MultipartFile file) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isEmpty()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        String uniqueName = UUID.randomUUID().toString();

        try {
            Files.copy(file.getInputStream(), root.resolve(uniqueName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Attachment attachment = new Attachment();
        attachment.setName(uniqueName);
        attachment.setOriginalName(file.getOriginalFilename());
        attachment.setPath(root.toString());
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());

        Attachment savedAttachment = attachmentRepository.save(attachment);
        TaskAttachment taskAttachment = new TaskAttachment(task, savedAttachment);
        taskAttachmentRepository.save(taskAttachment);
        createTaskHistory(task, "File", null, null,
                User.getCurrentUser().getUsername() + " attached file");

        return new ApiResponse("Attached", true);
    }

    @Override
    public ApiResponse detachFile(Long id, String fileName) {
        Attachment attachment = attachmentRepository.findByName(fileName);
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isEmpty()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        taskAttachmentRepository.deleteByAttachmentIdIdAndTaskIdId(attachment.getId(), task.getId());
        createTaskHistory(task, "File", null, null,
                User.getCurrentUser().getUsername() + " detached file");

        return new ApiResponse("Detached", true);
    }

    @Override
    public ApiResponse createSubtask(SubtaskDto dto) {
        Optional<Task> optionalTask = taskRepository.findById(dto.getParentId());
        if (!optionalTask.isEmpty()) {
            return new ApiResponse("Parent task not found", false);
        }
        Optional<Priority> optionalPriority = priorityRepo.findById(Math.toIntExact(dto.getPriorityId()));
        if (!optionalPriority.isEmpty()) {
            return new ApiResponse("Priority not found", false);
        }
        Task task = optionalTask.get();
        Priority priority = optionalPriority.get();
        Task subtask = new Task(dto.getName(), priority, task, dto.getStartedDate(), dto.isStartTimeHas(), dto.isDueTimeHas(), dto.getDueDate(), dto.getEstimateTime(), dto.getActivateDate());
        taskRepository.save(subtask);
        createTaskHistory(task, "Subtask", null, null,
                User.getCurrentUser().getUsername() + " created subtask");
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse dueDate(Long id, DueDateDto dto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Parent task not found", false);
        }
        Task task = optionalTask.get();

        if (dto.getAddType().equals(AddType.REMOVE)) {
            task.setDueDate(null);
            task.setDueTimeHas(false);
            task.setStartedDate(null);
            task.setStartTimeHas(false);
        } else {
            task.setDueDate(dto.getDueDate());
            task.setDueTimeHas(dto.isDueTimeHas());
            task.setStartedDate(dto.getStartedDate());
            task.setStartTimeHas(dto.isStartTimeHas());
        }
        taskRepository.save(task);
        createTaskHistory(task, "Due date", null, null,
                User.getCurrentUser().getUsername() + " changed due date");
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse changePriority(Long id, Long priorityId) {
        Optional<Priority> optionalPriority = priorityRepo.findById(Math.toIntExact(priorityId));
        if (!optionalPriority.isPresent()) {
            return new ApiResponse("Priority not found", false);
        }
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Priority priority = optionalPriority.get();
        Task task = optionalTask.get();
        createTaskHistory(task, "Priority", task.getPriority().getName(), priority.getName(),
                User.getCurrentUser().getUsername() + " changed priority");
        task.setPriority(priority);
        taskRepository.save(task);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse changeEstimate(Long id, Long estimate) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        createTaskHistory(task, "Estimate", String.valueOf(task.getEstimateTime()), String.valueOf(estimate),
                User.getCurrentUser().getUsername() + " changed estimate time");
        task.setEstimateTime(estimate);
        taskRepository.save(task);
        return new ApiResponse("Updated", true);
    }

    @Override
    public ApiResponse addTag(TaskTagDto dto) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (!optionalWorkspace.isPresent()) {
            return new ApiResponse("Workspace not found", false);
        }
        Optional<Task> optionalTask = taskRepository.findById(dto.getTaskId());
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        Workspace workspace = optionalWorkspace.get();
        Task task = optionalTask.get();
        Tag tag = new Tag(dto.getName(), dto.getColor(), workspace);
        Tag savedTag = tagRepository.save(tag);
        TaskTag taskTag = new TaskTag(task, savedTag);
        taskTagRepository.save(taskTag);
        createTaskHistory(task, "Tag", null, tag.getName(), "Tag added");
        return new ApiResponse("Created", true);
    }

    @Override
    public ApiResponse removeTag(Integer tagId, Long taskId) {
        if (taskTagRepository.existsByTagIdIdAndTaskIdId(tagId, taskId)) {
            taskTagRepository.deleteByTagIdIdAndTaskIdId(tagId, taskId);
            createTaskHistory(taskRepository.getById(taskId), "Tag", tagRepository.getById(tagId).getName(), null, "Tag removed");
            return new ApiResponse("Removed", true);
        }
        return new ApiResponse("Not found", false);
    }

    @Override
    public ApiResponse assignUser(Long taskId, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            return new ApiResponse("Task not found", false);
        }
        TaskUser taskUser = new TaskUser( optionalUser.get(),optionalTask.get());
        taskUserRepository.save(taskUser);
        createTaskHistory(optionalTask.get(), "User", null, optionalUser.get().getUsername(),
                "User assigned");
        return new ApiResponse("Assigned", true);
    }

    @Override
    public ApiResponse removeUser(Long taskId, Long userId) {
        if (taskUserRepository.existsByUserIdIdAndTaskIdId(userId, taskId)) {
            taskUserRepository.deleteByUserIdIdAndTaskIdId(userId, taskId);
            createTaskHistory(taskRepository.getById(taskId), "User", userRepository.getById(userId).getUsername(),
                    null,"User removed");
            return new ApiResponse("Removed", true);
        }
        return new ApiResponse("Not found", false);
    }
    private void createTaskHistory(Task task, String changeField, String before, String after, String data) {
        TaskHistory taskHistory = new TaskHistory(task, changeField, before, after, data);
        taskHistoryRepository.save(taskHistory);
    }
}

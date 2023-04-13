package com.musobek.clickapp.mapper;

import com.musobek.clickapp.dto.res.*;
import com.musobek.clickapp.entity.*;
import com.musobek.clickapp.entity.enam.AccessType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, componentModel = "spring")
@Component
public interface MapstructMapper {
    /**
     * User
     */

    UserRespDto toUserDto(User user);

    List<UserRespDto> toUserDto(List<User> user);

    /**
     * Workspace
     */
    @Mapping(source = "owner.email", target = "owner")
    WorkspaceRespDto toWorkspaceDto(Workspace workspace);

    List<WorkspaceRespDto> toWorkspaceDto(List<Workspace> workspaces);

    /**
     * Space
     */
    @Mapping(source = "workspace.id", target = "workspace")
    @Mapping(source = "icon.id", target = "icon")
    @Mapping(source = "avatar.id", target = "avatar")
    @Mapping(source = "owner.id", target = "owner")

    SpaceRespDto toSpaceDto(Space space);

    List<SpaceRespDto> toSpaceDto(List<Space> space);

    /**
     * Category
     */

    @Mapping(source = "project.id", target = "project")
    CategoryRespDto toCategoryDto(Category category);

    List<CategoryRespDto> toCategoryDto(List<Category> categories);
    /**
     * view
     */

    @Mapping(source = "iconId.id", target = "icon")

    ViewRespDto toViewDto(View view);
    List<ViewRespDto> toViewDto(List<View> views);
    /**
     * Status
     */

    @Mapping(source = "space.id",target = "spaceId")
    @Mapping(source = "project.id",target = "projectId")
    @Mapping(source = "category.id",target = "categoryId")
    StatusRespDto toStatusDto(Status status);
    List<StatusRespDto> toStatusDto(List<Status> statuses);

    /**
     * task
     */
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "parent.id", target = "parentId")
    @Mapping(source = "priority.id", target = "priorityId")
    TaskResDto toTaskDto(Task task);

    List<TaskResDto> toTaskDto(List<Task> tasks);

    /**
     * tag
     */
    @Mapping(source = "workspaceId.id",target = "workspaceId")

    TagResDto toTagDto(Tag tag);
    List<Tag> toTagDto(List<Tag> tags);
    /**
     * Comment
     */

    @Mapping(source = "taskId.id",target = "taskId")
   CommentResDto toCommentDto(Comment comment);
    List<CommentResDto>  toCommentDto(List<Comment> comments);
}

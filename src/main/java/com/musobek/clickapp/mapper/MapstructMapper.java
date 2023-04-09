package com.musobek.clickapp.mapper;

import com.musobek.clickapp.dto.res.SpaceRespDto;
import com.musobek.clickapp.dto.res.UserRespDto;
import com.musobek.clickapp.dto.res.ViewRespDto;
import com.musobek.clickapp.dto.res.WorkspaceRespDto;
import com.musobek.clickapp.entity.Space;
import com.musobek.clickapp.entity.User;
import com.musobek.clickapp.entity.View;
import com.musobek.clickapp.entity.Workspace;
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
     * View
     */
//    @Mapping(source = "iconId.id", target = "icon")
//
//    ViewRespDto toViewDto(View view);
//    List<ViewRespDto> toViewDto(List<View> views);
}

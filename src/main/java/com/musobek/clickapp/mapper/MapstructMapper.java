package com.musobek.clickapp.mapper;

import com.musobek.clickapp.dto.res.UserRespDto;
import com.musobek.clickapp.dto.res.WorkspaceRespDto;
import com.musobek.clickapp.entity.User;
import com.musobek.clickapp.entity.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
}

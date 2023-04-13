package com.musobek.clickapp.dto.res;

import com.musobek.clickapp.entity.Workspace;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResDto {
    private String color;
    private String name;

    private Long workspaceId;
}

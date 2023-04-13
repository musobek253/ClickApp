package com.musobek.clickapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskTagDto {

    private String name;

    private String color;

    private Long workspaceId;

    private Long taskId;
}

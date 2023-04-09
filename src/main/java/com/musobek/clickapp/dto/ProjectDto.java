package com.musobek.clickapp.dto;

import com.musobek.clickapp.entity.enam.AccessType;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {

    private String name;

    private String color;


    private Long spaceId;


    private AccessType accessType;


    private List<String> lists;
}

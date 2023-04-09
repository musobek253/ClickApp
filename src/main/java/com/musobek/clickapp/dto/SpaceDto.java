package com.musobek.clickapp.dto;

import com.musobek.clickapp.entity.enam.AccessType;
import lombok.Data;

import java.util.List;
@Data
public class SpaceDto {
    private String name;

    private String color;


    private Long workspaceId;

    private Long avatarId;

    private Long iconId;

    private AccessType accessType; // public, private

    private List<Long> members;
}

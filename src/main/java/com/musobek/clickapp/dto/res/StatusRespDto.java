package com.musobek.clickapp.dto.res;

import com.musobek.clickapp.entity.enam.Types;
import lombok.Data;

@Data
public class StatusRespDto {
    private String name;

    private String color;

    private Long spaceId;

    private Long projectId;

    private Long categoryId;

    private Types types;
}
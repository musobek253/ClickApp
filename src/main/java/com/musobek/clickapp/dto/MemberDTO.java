package com.musobek.clickapp.dto;

import com.musobek.clickapp.entity.enam.AddType;
import lombok.Data;

@Data
public class MemberDTO {
    private Long id;

    private Long roleId;

    private AddType addType;
}

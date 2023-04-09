package com.musobek.clickapp.dto;

import com.musobek.clickapp.entity.enam.Permission;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AttachMemberPermissionDto {
    private Map<Long, List<Permission>> memberPermission;

}

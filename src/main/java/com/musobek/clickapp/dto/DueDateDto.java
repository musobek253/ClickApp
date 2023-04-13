package com.musobek.clickapp.dto;

import com.musobek.clickapp.entity.enam.AddType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DueDateDto {
    private Timestamp startedDate;

    private boolean startTimeHas = false;

    private boolean dueTimeHas = false;

    private Timestamp dueDate;

    private AddType addType;
}

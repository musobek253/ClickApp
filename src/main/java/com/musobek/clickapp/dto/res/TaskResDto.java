package com.musobek.clickapp.dto.res;

import com.musobek.clickapp.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class TaskResDto {
    private String name;

    private String description;

    private Status status;

    private Long categoryId;

    private Long priorityId;

    private Long parentId;

    private Timestamp startedDate;

    private boolean startTimeHas;

    private boolean dueTimeHas;

    private Timestamp dueDate;

    private Long estimateTime;

    private Timestamp activateDate;
}

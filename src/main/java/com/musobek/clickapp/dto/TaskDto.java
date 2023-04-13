package com.musobek.clickapp.dto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class TaskDto {
    private String name;

    private Long statusId;


    private Long categoryId;

    private Long priorityId;

    private Timestamp startedDate;

    private boolean startTimeHas;

    private Timestamp dueDate;

    private boolean dueTimeHas;
}

package com.musobek.clickapp.entity;

import com.musobek.clickapp.entity.template.AbsLongEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data

@NoArgsConstructor
@Entity
public class TaskHistory extends AbsLongEntity {

    @ManyToOne
    private Task task;

    @Column
    private String changeFieldName;

    @Column
    private String before;

    @Column
    private String after;

    @Column
    private String data;

    public TaskHistory(Task task, String changeField, String before, String after, String data) {
        this.task = task;
        this.after = after;
        this.before = before;
        this.changeFieldName = changeField;
        this.data = data;
    }
}

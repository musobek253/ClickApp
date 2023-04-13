package com.musobek.clickapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;





@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TaskAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean pinCoverImage;

    @ManyToOne
    private Task taskId;

    @ManyToOne
    private Attachment attachmentId;

    public TaskAttachment(Task task, Attachment savedAttachment) {
        this.attachmentId = savedAttachment;
        this.taskId = task;
    }
}


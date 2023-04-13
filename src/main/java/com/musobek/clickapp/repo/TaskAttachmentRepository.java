package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Attachment;
import com.musobek.clickapp.entity.Task;
import com.musobek.clickapp.entity.TaskAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, Integer> {
    void deleteByAttachmentIdIdAndTaskIdId(Long attachmentId_id, Long taskId_id);
}
package com.musobek.clickapp.repo;

import com.musobek.clickapp.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findByName(String fileName);

}
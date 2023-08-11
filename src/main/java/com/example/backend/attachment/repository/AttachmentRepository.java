package com.example.backend.attachment.repository;

import com.example.backend.attachment.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    public Attachment findByItemId(Long itemId);

}

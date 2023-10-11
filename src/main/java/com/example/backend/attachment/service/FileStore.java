package com.example.backend.attachment.service;

import com.example.backend.attachment.entity.Attachment;
import com.example.backend.attachment.repository.AttachmentRepository;
import com.example.backend.item.entity.Item;
import com.example.backend.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStore {
    private final AttachmentRepository attachmentRepository;
    private final ItemRepository itemRepository;

    @Value("${file.dir}")
    private String fileDir;

    public void storeFiles(List<MultipartFile> multipartFiles, Item item) throws IOException {
        List<Attachment> attachments = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFiles.isEmpty()) {
                Attachment attachment = storeFile(multipartFile, item);
                if (attachment != null) {
                    attachments.add(attachment);
                    Attachment saved = attachmentRepository.save(attachment);
                    log.info("이미지파일 ID ={}", saved.getId());
                }
            }
        }
    }

    public void update(MultipartFile image, Long itemId) throws IOException {
        Attachment byItemId = attachmentRepository.findByItemId(itemId);
        log.info("이미지 id={}", byItemId.getId());
        byItemId.setNotUse();
        Attachment attachment = storeFile(image, itemRepository.findById(itemId).orElseThrow());
        save(attachment);
    }

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }
    public Attachment storeFile(MultipartFile multipartFile, Item item) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new Attachment(originalFilename, storeFileName, item);
    }

    public void save(Attachment attachment) {
        Attachment saved = attachmentRepository.save(attachment);
    }

    private String createFileName(String originalFilename) {
        String uu = UUID.randomUUID().toString();
        String extracted = extract(originalFilename);

        String storeFileName = uu + "." + extracted;
        return storeFileName;
    }

    private String extract(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return ext;
    }
}

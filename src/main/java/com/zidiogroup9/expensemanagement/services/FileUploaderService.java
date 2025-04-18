package com.zidiogroup9.expensemanagement.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploaderService {
    String uploadFile(MultipartFile file, String folder);
    String preSignedUrl(String fileName);
    void deleteFile(String fileName);
}

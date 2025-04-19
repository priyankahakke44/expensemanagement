package com.zidiogroup9.expensemanagement.services.Impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.zidiogroup9.expensemanagement.exceptions.FilesUploadException;
import com.zidiogroup9.expensemanagement.services.FileUploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3FileUploader implements FileUploaderService {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    private final AmazonS3 client;

    @Override
    public String uploadFile(MultipartFile file, String folder, String email) {
        if (file == null) {
            throw new FilesUploadException("File is null");
        }
        String actualFilename = file.getOriginalFilename();
        String fileName = folder + "/" + email + actualFilename.substring(actualFilename.lastIndexOf("."));
        log.info(fileName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        try {
            client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
            log.info(preSignedUrl(fileName));
            return this.preSignedUrl(fileName);
        } catch (IOException e) {
            throw new FilesUploadException("error in uploading file " + e.getMessage());
        }
    }

    @Override
    public String preSignedUrl(String fileName) {
        ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
        responseHeaders.setContentDisposition("inline");
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        generatePresignedUrlRequest.withMethod(HttpMethod.GET);
        generatePresignedUrlRequest.withResponseHeaders(responseHeaders);
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            if (client.doesObjectExist(bucketName, fileName)) {
                client.deleteObject(bucketName, fileName);
            } else {
                throw new FilesUploadException("File not found in S3 bucket: " + fileName);
            }
        } catch (Exception e) {
            throw new FilesUploadException("Error deleting file from S3: " + e.getMessage());
        }
    }

}

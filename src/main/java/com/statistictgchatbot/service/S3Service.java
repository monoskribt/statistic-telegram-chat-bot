package com.statistictgchatbot.service;

import com.statistictgchatbot.props.S3Props;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Props s3Props;

    public void uploadFileToS3(String fileName, String localPath) throws IOException {
        File file = new File(localPath + fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3Props.bucketName())
                .key("chat-files/" + fileName + UUID.randomUUID())
                .build();

        try (InputStream inputStream = new FileInputStream(file)) {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.length()));
        }
    }
}

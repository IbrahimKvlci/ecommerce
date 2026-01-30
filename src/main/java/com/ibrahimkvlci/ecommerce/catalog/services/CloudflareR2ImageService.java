package com.ibrahimkvlci.ecommerce.catalog.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

@Service
@RequiredArgsConstructor
public class CloudflareR2ImageService implements ImageCloudService {

    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket}")
    private String bucketName;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (extension == null)
            extension = "jpg";

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(UUID.randomUUID().toString() + "." + extension)
                .contentType(file.getContentType())
                .cacheControl("max-age=31536000")
                .contentDisposition("inline")
                .build();

        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return putObjectRequest.key();
    }

    @Override
    public void deleteImage(String publicId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteImage'");
    }
}

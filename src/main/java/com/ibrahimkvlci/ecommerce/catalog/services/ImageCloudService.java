package com.ibrahimkvlci.ecommerce.catalog.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageCloudService {

    String uploadImage(MultipartFile file) throws IOException;

    void deleteImage(String publicId);
}

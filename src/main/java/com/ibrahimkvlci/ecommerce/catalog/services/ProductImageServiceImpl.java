package com.ibrahimkvlci.ecommerce.catalog.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ibrahimkvlci.ecommerce.catalog.exceptions.ImageUploadException;
import com.ibrahimkvlci.ecommerce.catalog.models.Product;
import com.ibrahimkvlci.ecommerce.catalog.models.ProductImage;
import com.ibrahimkvlci.ecommerce.catalog.repositories.ProductImageRepository;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.DataResult;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.Result;
import com.ibrahimkvlci.ecommerce.catalog.utilities.results.SuccessDataResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ImageCloudService imageCloudService;

    @Override
    public DataResult<String> uploadProductImage(MultipartFile file, Product product) {

        String result;
        try {
            result = imageCloudService.uploadImage(file);
        } catch (IOException e) {
            throw new ImageUploadException(e.getMessage());
        }
        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setImageUrl(result);
        productImage.setPrimary(false);
        productImageRepository.save(productImage);
        return new SuccessDataResult<>("Image uploaded successfully", result);

    }

    @Override
    public Result deleteProductImage(Long imageId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProductImage'");
    }

    @Override
    public Result setPrimaryImage(Long imageId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPrimaryImage'");
    }
}

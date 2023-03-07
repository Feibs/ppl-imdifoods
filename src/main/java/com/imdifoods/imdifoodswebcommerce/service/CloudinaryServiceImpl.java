package com.imdifoods.imdifoodswebcommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public String uploadImage(MultipartFile imageFile) {
        try {
            Uploader uploader = cloudinary.uploader();
            Map<String, Object> uploadResult = uploader.upload(imageFile.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("public_id").toString();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getImageUrl(String imageId) {
        try {
            return cloudinary.url().secure(true).format("jpg")
                    .publicId(imageId)
                    .generate();
        } catch (Exception e) {
            return null;
        }
    }
}

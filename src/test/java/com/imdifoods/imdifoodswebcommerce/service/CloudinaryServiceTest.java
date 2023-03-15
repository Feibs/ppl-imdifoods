package com.imdifoods.imdifoodswebcommerce.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.Url;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class CloudinaryServiceTest {
    @Autowired
    private CloudinaryService cloudinaryService;
    @MockBean
    private Cloudinary cloudinary;

    @Test
    void uploadImageShouldReturnPublicId() throws IOException {
        String mockImageId = "imageId";
        MultipartFile imageFile = mock(MultipartFile.class);
        Uploader uploader = mock(Uploader.class);
        Map map = mock(Map.class);
        Object object = mock(Object.class);
        when(imageFile.getBytes()).thenReturn(new byte[1]);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(byte[].class), anyMap())).thenReturn(map);
        when(map.get(anyString())).thenReturn(object);
        when(object.toString()).thenReturn(mockImageId);
        String imageId = cloudinaryService.uploadImage(imageFile);
        assertEquals(mockImageId, imageId);
    }

    @Test
    void uploadInvalidImageShouldReturnNull() {
        MultipartFile imageFile = null;
        String imageId = cloudinaryService.uploadImage(imageFile);
        assertNull(imageId);
    }

    @Test
    void getInvalidImageUrlShouldThrowException() {
        String imageId = null;
        assertNull(cloudinaryService.getImageUrl(imageId));
    }

    @Test
    void getValidImageUrlShouldReturnNotNull() {
        String imageId = "imageId";
        Url mockUrl = mock(Url.class);
        when(cloudinary.url()).thenReturn(mockUrl);
        when(mockUrl.secure(anyBoolean())).thenReturn(mockUrl);
        when(mockUrl.format(anyString())).thenReturn(mockUrl);
        when(mockUrl.publicId(anyString())).thenReturn(mockUrl);
        when(mockUrl.generate()).thenReturn(imageId);
        String url = cloudinaryService.getImageUrl(imageId);
        assertNotNull(url);
    }

    @Test
    void deleteValidImage() throws IOException {
        String imageId = "imageId";
        Uploader uploader = mock(Uploader.class);
        when(cloudinary.uploader()).thenReturn(uploader);
        cloudinaryService.deleteImage(imageId);
        verify(uploader).destroy(anyString(), anyMap());
    }

    @Test
    void deleteImageException() {
        String imageId = "imageId";
        assertNull(cloudinaryService.deleteImage(imageId));
    }
}
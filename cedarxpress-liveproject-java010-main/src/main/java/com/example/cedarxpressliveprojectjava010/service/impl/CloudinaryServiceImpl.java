package com.example.cedarxpressliveprojectjava010.service.impl;

import com.cloudinary.Cloudinary;
import com.example.cedarxpressliveprojectjava010.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinaryInstance;
    @Override
    public String uploadFile(MultipartFile image) {
        try {
            File uploadedFile = convertMultiPartToFile(image);
            Map uploadResult = cloudinaryInstance.uploader().upload(uploadedFile, com.cloudinary.utils.ObjectUtils.emptyMap());
            boolean isDeleted = uploadedFile.delete();
            if (isDeleted){

            }else
                System.out.println("File doesn't exist");
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private File convertMultiPartToFile(MultipartFile image) throws IOException {
        String file =  image.getOriginalFilename();
        if (file == null) throw new AssertionError();
        File convFile = new File(file);
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(image.getBytes());
        fos.close();
        return convFile;
    }
}
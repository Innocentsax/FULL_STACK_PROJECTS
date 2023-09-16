package com.decagon.chompapp.services.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.decagon.chompapp.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinaryConfiguration;

    @Autowired
    public CloudinaryServiceImpl(Cloudinary cloudinaryConfiguration) {
        this.cloudinaryConfiguration = cloudinaryConfiguration;
    }

    @Override
    public String uploadFile(MultipartFile image) {
        try {
            File uploadedFile = convertMultipartToFile(image);
            Map uploadedFileResult = cloudinaryConfiguration.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            boolean isDeleted = uploadedFile.delete();
            if (isDeleted) {
                System.out.println("File successfully deleted");
            } else
                System.out.println("File doesn't exist");
            return uploadedFileResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultipartToFile(MultipartFile image) throws IOException {
        String file = image.getOriginalFilename();
        assert file != null;
        File convertFile = new File(file);
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(image.getBytes());
        fileOutputStream.close();
        return convertFile;
    }
}


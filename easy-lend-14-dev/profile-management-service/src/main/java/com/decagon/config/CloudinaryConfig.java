package com.decagon.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CloudinaryConfig {

    @Value("${cloudinary.cloud_name}")
    private String CLOUD_NAME;

    @Value("${cloudinary.api_key}")
    private String CLOUD_KEY;

    @Value("${cloudinary.api_secret}")
    private String CLOUD_SECRET;


    private Cloudinary configCloudinary(){
        return new Cloudinary(ObjectUtils.asMap("cloud_name",CLOUD_NAME,"api_key",CLOUD_KEY,"api_secret",CLOUD_SECRET));
    }

    public String imageLink(MultipartFile file, String id) {
        String url = "";
        CloudinaryConfig cloudinaryConfig = new CloudinaryConfig();
        Cloudinary cloudinary = cloudinaryConfig.configCloudinary();
        try {
            cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("public_id", "image_id" + id));
            url = cloudinary.url().transformation(new Transformation().width(200).height(250).crop("fill")).generate("image_id" + id);
            System.out.println(url);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return url;
    }
}

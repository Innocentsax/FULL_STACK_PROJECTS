package com.decagon.eventhubbe.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class CloudinaryConfig {
//    @Value("${cloud-api-name}")
    private static final String CLOUD_NAME="dknryxg72";
//    @Value("${cloud-api-secret}")
    private static final String CLOUD_SECRET="boQo3txlmXUV2VpjvmJr1dc_FKc";
//    @Value("${cloud-api-key}")
    private static final String CLOUD_KEY="828638228684336";
    public String imageLink(MultipartFile file,String id){

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", CLOUD_KEY);
        config.put("api_secret", CLOUD_SECRET);
        Cloudinary cloudinary = new Cloudinary(config);

        try {
            cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("public_id", "image_id"+id));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        String url = cloudinary.url().transformation(new Transformation().width(200).height(250).crop("fill")).generate("image_id"+id);
        System.out.println(url);
        return url;
    }
}

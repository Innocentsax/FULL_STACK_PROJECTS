package com.example.decapay.configurations.cloudinary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter

public class CloudinaryConfig
{

    @Value("${CLOUDINARY_URL}")
    private  String cloudinaryUrl;

    @Value("${FOLDER_NAME}")
    private  String cloudinaryFolderName;

    @Value("${DEFAULT_AVATAR}")
    private  String avatarImagePath;

    @Value("${SECRETE_KEY}")
    private  String secreteKey;




}

package com.example.SMFashions.Util;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dbitcrbfm",
            "api_key", "977898415313795",
            "api_secret", "1PNljjrKry_ONjHTxJWkFlxyihY",
            "secure", true
        ));
    }
}
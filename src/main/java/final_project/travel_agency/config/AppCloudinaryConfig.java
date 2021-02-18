package final_project.travel_agency.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppCloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        return  new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "web-tour",
                "api_key", "776774897771848",
                "api_secret", "CLDjlianvqRy_kJYprxnVaGL7Nc"));
    }

}
